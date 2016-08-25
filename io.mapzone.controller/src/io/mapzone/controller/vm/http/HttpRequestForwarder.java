/**
 * Copyright MITRE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mapzone.controller.vm.http;

import java.util.Enumeration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;

import org.polymap.core.runtime.config.Config2;
import org.polymap.core.runtime.config.DefaultBoolean;
import org.polymap.core.runtime.config.Mandatory;

/**
 * An HTTP reverse proxy/gateway servlet. It is designed to be extended for
 * customization if desired. Most of the work is handled by <a
 * href="http://hc.apache.org/httpcomponents-client-ga/">Apache HttpClient</a>.
 * <p/>
 * There are alternatives to a servlet based proxy such as Apache mod_proxy if that
 * is available to you. However this servlet is easily customizable by Java,
 * secure-able by your web application's security (e.g. spring-security), portable
 * across servlet engines, and is embeddable into another web application.
 * <p/>
 * Inspiration: http://httpd.apache.org/docs/2.0/mod/mod_proxy.html
 *
 * @see <a href="https://github.com/mitre/HTTP-Proxy-Servlet/blob/master/src/main/java/org/mitre/dsmiley/httpproxy/ProxyServlet.java">Origin</a>
 * @author David Smiley dsmiley@mitre.org
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
@SuppressWarnings("deprecation")
public class HttpRequestForwarder
        extends HttpForwarder {

    static Log log = LogFactory.getLog( HttpRequestForwarder.class );
    
    /** Set to {@link PoolingHttpClientConnectionManager#setDefaultMaxPerRoute(int)}. */
    public static final int             MAX_CONNECTIONS_PER_ROUTE = 6;
    
    /** Set to {@link PoolingHttpClientConnectionManager#setMaxTotal(int)}. */
    public static final int             MAX_CONNECTIONS = 100;
    
    protected static HttpClient         proxyClient;
    
    protected static ThreadLocal<HttpRequestForwarder>  active = new ThreadLocal();

    static {
        InterceptableHttpClientConnectionFactory factory = new InterceptableHttpClientConnectionFactory() {
            @Override
            protected void onRequestSubmitted( HttpRequest request ) {
                active.get().onRequestSubmitted( request );
            }
        };
        
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager( factory );
        manager.setDefaultMaxPerRoute( MAX_CONNECTIONS_PER_ROUTE );
        manager.setMaxTotal( MAX_CONNECTIONS );
        
        proxyClient = HttpClientBuilder.create()
                .disableCookieManagement()
                .setConnectionManagerShared( true )
                .setConnectionManager( manager ).build();
        
//        // as of HttpComponents v4.2, this class is better since it uses System Properties:
//      HttpParams hcParams = new BasicHttpParams();
//      hcParams.setParameter( ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES );
//      hcParams.setParameter( ClientPNames.HANDLE_REDIRECTS, Boolean.TRUE );
//      return new SystemDefaultHttpClient( hcParams );
        
    }
    
    // instance *******************************************
    
    @DefaultBoolean( false )
    public Config2<HttpRequestForwarder,Boolean> doForwardIP;

    /** User agents shouldn't send the url fragment but what if it does? */
    @DefaultBoolean( false )
    public Config2<HttpRequestForwarder,Boolean> doSendUrlFragment;

    @Mandatory
    public Config2<HttpRequestForwarder,String>  cookieNamePrefix;
    
    // These next 3 are cached here, and should only be referred to in initialization
    // logic. See the
    
    /**  */
    @Mandatory
    public Config2<HttpRequestForwarder,String>  targetUri;

    protected URI                 targetUriObj;  // new URI(targetUri)

    protected HttpHost            targetHost;    // URIUtils.extractHost(targetUriObj);

    protected HttpResponse        proxyResponse;

    protected HttpRequest         proxyRequest;

    
    /**
     * Executed when the request is send - before start waiting on response. See
     * {@link InterceptableHttpClientConnectionFactory} for detail.
     */
    protected void onRequestSubmitted( HttpRequest request ) {
    }

    
    public void service( HttpServletRequest servletRequest, HttpServletResponse servletResponse )
            throws ServletException, IOException, URISyntaxException {
        targetUriObj = new URI( targetUri.get() );
        targetHost = URIUtils.extractHost( targetUriObj );

        // Make the Request
        // note: we won't transfer the protocol version because I'm not sure it would
        // truly be compatible
        String method = servletRequest.getMethod();
        String proxyRequestUri = rewriteUrlFromRequest( servletRequest );
        
        // spec: RFC 2616, sec 4.3: either of these two headers signal that there is
        // a message body.
        if (servletRequest.getHeader( HttpHeaders.CONTENT_LENGTH ) != null
                || servletRequest.getHeader( HttpHeaders.TRANSFER_ENCODING ) != null) {
            HttpEntityEnclosingRequest eProxyRequest = new BasicHttpEntityEnclosingRequest( method, proxyRequestUri );
            // Add the input entity (streamed)
            // note: we don't bother ensuring we close the servletInputStream since
            // the container handles it
            eProxyRequest.setEntity( new InputStreamEntity( servletRequest.getInputStream(), servletRequest
                    .getContentLength() ) );
            proxyRequest = eProxyRequest;
        }
        else {
            proxyRequest = new BasicHttpRequest( method, proxyRequestUri );
        }

        copyRequestHeaders( servletRequest );

        setXForwardedForHeader( servletRequest );

        // Execute the request
        try {
            active.set( this );
            
            log.info( "REQUEST "
                    + "[" + StringUtils.right( Thread.currentThread().getName(), 2 ) + "] " 
                    + method + ": " + servletRequest.getRequestURI() + " -- " + proxyRequest.getRequestLine().getUri() );
            proxyResponse = proxyClient.execute( targetHost, proxyRequest );
        }
        catch (Exception e) {
            // abort request, according to best practice with HttpClient
            if (proxyRequest instanceof AbortableHttpRequest) {
                AbortableHttpRequest abortableHttpRequest = (AbortableHttpRequest)proxyRequest;
                abortableHttpRequest.abort();
            }
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            if (e instanceof ServletException) {
                throw (ServletException)e;
            }
            // noinspection ConstantConditions
            if (e instanceof IOException) {
                throw (IOException)e;
            }
            throw new RuntimeException( e );
        }
        finally {
            active.set( null );
        }
        // Note: Don't need to close servlet outputStream:
        // http://stackoverflow.com/questions/1159168/should-one-call-close-on-httpservletresponse-getoutputstream-getwriter
    }


    /** Copy request headers from the servlet client to the proxy request. */
    protected void copyRequestHeaders( HttpServletRequest servletRequest ) {
        Enumeration enumerationOfHeaderNames = servletRequest.getHeaderNames();
        while (enumerationOfHeaderNames.hasMoreElements()) {
            String headerName = (String)enumerationOfHeaderNames.nextElement();
            // Instead the content-length is effectively set via InputStreamEntity
            if (headerName.equalsIgnoreCase( HttpHeaders.CONTENT_LENGTH )) {
                continue;
            }
            if (hopByHopHeaders.containsHeader( headerName )) {
                log.debug( "    Header: " + headerName + " ... skipped." );
                continue;
            }

            Enumeration headers = servletRequest.getHeaders( headerName );
            while (headers.hasMoreElements()) {
                String headerValue = (String)headers.nextElement();
                // In case the proxy host is running multiple virtual servers,
                // rewrite the Host header to ensure that we get content from
                // the correct virtual server
                if (headerName.equalsIgnoreCase( HttpHeaders.HOST )) {
                    HttpHost host = targetHost;
                    headerValue = host.getHostName();
                    if (host.getPort() != -1) {
                        headerValue += ":" + host.getPort();
                    }
                }
                //
                else if (headerName.equalsIgnoreCase( org.apache.http.cookie.SM.COOKIE )) {
                    headerValue = getRealCookie( headerValue );
                }
                proxyRequest.addHeader( headerName, headerValue );
                log.debug( "    Header: " + headerName + " = " + headerValue );
            }
        }
    }


    /**
     * Take any client cookies that were originally from the proxy and prepare them
     * to send to the proxy. This relies on cookie headers being set correctly
     * according to RFC 6265 Sec 5.4. This also blocks any local cookies from being
     * sent to the proxy.
     */
    protected String getRealCookie( String cookieValue ) {
        StringBuilder escapedCookie = new StringBuilder();
        String cookies[] = cookieValue.split( "; " );
        for (String cookie : cookies) {
            String cookieSplit[] = cookie.split( "=" );
            if (cookieSplit.length == 2) {
                String cookieName = cookieSplit[0];
                if (cookieName.startsWith( cookieNamePrefix.get() )) {
                    cookieName = cookieName.substring( cookieNamePrefix.get().length() );
                    if (escapedCookie.length() > 0) {
                        escapedCookie.append( "; " );
                    }
                    escapedCookie.append( cookieName ).append( "=" ).append( cookieSplit[1] );
                }
            }

            cookieValue = escapedCookie.toString();
        }
        return cookieValue;
    }


    protected void setXForwardedForHeader( HttpServletRequest servletRequest ) {
        String headerName = "X-Forwarded-For";
        if (doForwardIP.get()) {
            String newHeader = servletRequest.getRemoteAddr();
            String existingHeader = servletRequest.getHeader( headerName );
            if (existingHeader != null) {
                newHeader = existingHeader + ", " + newHeader;
            }
            proxyRequest.setHeader( headerName, newHeader );
        }
    }


    /**
     * Reads the request URI from {@code servletRequest} and rewrites it, considering
     * targetUri. It's used to make the new request.
     */
    protected String rewriteUrlFromRequest( HttpServletRequest servletRequest ) {
        StringBuilder uri = new StringBuilder( 500 );
        uri.append( targetUriObj );
        // Handle the path given to the servlet
        if (servletRequest.getPathInfo() != null) {// ex: /my/path.html
            uri.append( encodeUriQuery( rewritePath( servletRequest.getPathInfo() ) ) );
        }
        // Handle the query string & fragment
        String queryString = servletRequest.getQueryString();// ex:(following '?'):
                                                             // name=value&foo=bar#fragment
        String fragment = null;
        // split off fragment from queryString, updating queryString if found
        if (queryString != null) {
            int fragIdx = queryString.indexOf( '#' );
            if (fragIdx >= 0) {
                fragment = queryString.substring( fragIdx + 1 );
                queryString = queryString.substring( 0, fragIdx );
            }
        }

        queryString = rewriteQueryString( queryString );
        if (queryString != null && queryString.length() > 0) {
            uri.append( '?' );
            uri.append( encodeUriQuery( queryString ) );
        }

        if (doSendUrlFragment.get() && fragment != null) {
            uri.append( '#' );
            uri.append( encodeUriQuery( fragment ) );
        }
        return uri.toString();
    }


    protected String rewritePath( String path ) {
        return path;
    }

    
    protected String rewriteQueryString( String queryString ) {
        return queryString;
    }
}
