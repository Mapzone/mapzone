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

import java.util.List;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.util.EntityUtils;

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
 */
@SuppressWarnings("deprecation")
public class HttpResponseForwarder
        extends HttpForwarder 
        implements AutoCloseable {

    private static Log log = LogFactory.getLog( HttpResponseForwarder.class );
    
    private HttpRequestForwarder        requestForwarder;


    public HttpResponseForwarder( HttpRequestForwarder requestForwarder ) {
        this.requestForwarder = requestForwarder;
    }


    @Override
    public void close() {
    }


    protected void service( HttpServletRequest servletRequest, HttpServletResponse servletResponse )
            throws ServletException, IOException {
        HttpResponse proxyResponse = requestForwarder.proxyResponse;
        HttpRequest proxyRequest = requestForwarder.proxyRequest;
        try {
            // Process the response
            int statusCode = proxyResponse.getStatusLine().getStatusCode();
            log.info( "RESPONSE: " + statusCode );

            // copying response headers to make sure SESSIONID or other Cookie which
            // comes from remote server
            // will be saved in client when the proxied url was redirected to another
            // one.
            // see issue [#51](https://github.com/mitre/HTTP-Proxy-Servlet/issues/51)
            copyResponseHeaders( proxyResponse, servletRequest, servletResponse );

            if (doResponseRedirectOrNotModifiedLogic( servletRequest, servletResponse, proxyResponse, statusCode )) {
                // the response is already "committed" now without any body to send
                return;
            }

            // Pass the response code. This method with the "reason phrase" is
            // deprecated but it's the only way to pass the
            // reason along too.
            // noinspection deprecation
            servletResponse.setStatus( statusCode, proxyResponse.getStatusLine().getReasonPhrase() );

            // Send the content to the client
            copyResponseEntity( proxyResponse, servletResponse );

        }
        catch (Exception e) {
            // abort request, according to best practice with HttpClient
            if (proxyRequest instanceof AbortableHttpRequest) {
                AbortableHttpRequest abortableHttpRequest = (AbortableHttpRequest)proxyRequest;
                abortableHttpRequest.abort();
            }
            if (e instanceof RuntimeException)
                throw (RuntimeException)e;
            if (e instanceof ServletException)
                throw (ServletException)e;
            // noinspection ConstantConditions
            if (e instanceof IOException)
                throw (IOException)e;
            throw new RuntimeException( e );

        }
        finally {
            // make sure the entire entity was consumed, so the connection is
            // released
            if (proxyResponse != null) {
                consumeQuietly( proxyResponse.getEntity() );
            }
            // Note: Don't need to close servlet outputStream:
            // http://stackoverflow.com/questions/1159168/should-one-call-close-on-httpservletresponse-getoutputstream-getwriter
        }
    }


    protected boolean doResponseRedirectOrNotModifiedLogic( HttpServletRequest servletRequest,
            HttpServletResponse servletResponse, HttpResponse proxyResponse, int statusCode ) throws ServletException,
            IOException {
        // Check if the proxy response is a redirect
        // The following code is adapted from
        // org.tigris.noodle.filters.CheckForRedirect
        if (statusCode >= HttpServletResponse.SC_MULTIPLE_CHOICES /* 300 */
                && statusCode < HttpServletResponse.SC_NOT_MODIFIED /* 304 */) {
            Header locationHeader = proxyResponse.getLastHeader( HttpHeaders.LOCATION );
            if (locationHeader == null) {
                throw new ServletException( "Received status code: " + statusCode + " but no " + HttpHeaders.LOCATION
                        + " header was found in the response" );
            }
            // Modify the redirect to go to this proxy servlet rather that the
            // proxied host
            String locStr = rewriteUrlFromResponse( servletRequest, locationHeader.getValue() );

            servletResponse.sendRedirect( locStr );
            return true;
        }
        // 304 needs special handling. See:
        // http://www.ics.uci.edu/pub/ietf/http/rfc1945.html#Code304
        // We get a 304 whenever passed an 'If-Modified-Since'
        // header and the data on disk has not changed; server
        // responds w/ a 304 saying I'm not going to send the
        // body because the file has not changed.
        if (statusCode == HttpServletResponse.SC_NOT_MODIFIED) {
            servletResponse.setIntHeader( HttpHeaders.CONTENT_LENGTH, 0 );
            servletResponse.setStatus( HttpServletResponse.SC_NOT_MODIFIED );
            return true;
        }
        return false;
    }


    /**
     * HttpClient v4.1 doesn't have the
     * {@link org.apache.http.util.EntityUtils#consumeQuietly(org.apache.http.HttpEntity)}
     * method.
     */
    protected void consumeQuietly( HttpEntity entity ) {
        try {
            EntityUtils.consume( entity );
        }
        catch (IOException e) {
            log.warn( e.getMessage(), e );
        }
    }


    /** 
     * Copy proxied response headers back to the servlet client. 
     */
    protected void copyResponseHeaders( HttpResponse proxyResponse, HttpServletRequest servletRequest,
            HttpServletResponse servletResponse ) {
        for (Header header : proxyResponse.getAllHeaders()) {
            if (hopByHopHeaders.containsHeader( header.getName() ))
                continue;
            if (header.getName().equalsIgnoreCase( org.apache.http.cookie.SM.SET_COOKIE )
                    || header.getName().equalsIgnoreCase( org.apache.http.cookie.SM.SET_COOKIE2 )) {
                copyProxyCookie( servletRequest, servletResponse, header );
            }
            else {
                servletResponse.addHeader( header.getName(), header.getValue() );
            }
        }
    }


    /**
     * Copy cookie from the proxy to the servlet client. Replaces cookie path to
     * local path and renames cookie to avoid collisions.
     */
    protected void copyProxyCookie( HttpServletRequest servletRequest, HttpServletResponse servletResponse,
            Header header ) {
        List<HttpCookie> cookies = HttpCookie.parse( header.getValue() );
        String path = servletRequest.getContextPath(); // path starts with / or is empty string
        path += servletRequest.getServletPath(); // servlet path starts with / or is empty string

        for (HttpCookie cookie : cookies) {
            // set cookie name prefixed w/ a proxy value so it won't collide w/ other cookies
            String proxyCookieName = requestForwarder.cookieNamePrefix.get() + cookie.getName();
            Cookie servletCookie = new Cookie( proxyCookieName, cookie.getValue() );
            servletCookie.setComment( cookie.getComment() );
            servletCookie.setMaxAge( (int)cookie.getMaxAge() );
            servletCookie.setPath( path ); // set to the path of the proxy servlet
            // don't set cookie domain
            servletCookie.setSecure( cookie.getSecure() );
            servletCookie.setVersion( cookie.getVersion() );
            servletResponse.addCookie( servletCookie );
        }
    }


    /** Copy response body data (the entity) from the proxy to the servlet client. */
    protected void copyResponseEntity( HttpResponse proxyResponse, HttpServletResponse servletResponse )
            throws IOException {
        HttpEntity entity = proxyResponse.getEntity();
        if (entity != null) {
            OutputStream servletOutputStream = servletResponse.getOutputStream();
            entity.writeTo( servletOutputStream );
        }
    }


    /**
     * For a redirect response from the target server, this translates {@code theUrl}
     * to redirect to and translates it to one the original client can use.
     */
    protected String rewriteUrlFromResponse( HttpServletRequest servletRequest, String theUrl ) {
        // TODO document example paths
        final String targetUri = requestForwarder.targetUri.get();
        if (theUrl.startsWith( targetUri )) {
            String curUrl = servletRequest.getRequestURL().toString();// no query
            String pathInfo = servletRequest.getPathInfo();
            if (pathInfo != null) {
                assert curUrl.endsWith( pathInfo );
                curUrl = curUrl.substring( 0, curUrl.length() - pathInfo.length() );// take
                                                                                    // pathInfo
                                                                                    // off
            }
            theUrl = curUrl + theUrl.substring( targetUri.length() );
        }
        return theUrl;
    }

}
