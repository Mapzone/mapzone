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

import java.util.BitSet;
import java.util.Formatter;

import java.net.URI;

import org.apache.http.message.BasicHeader;
import org.apache.http.message.HeaderGroup;

import org.polymap.core.runtime.config.Configurable;

/**
 * 
 * <p/>
 * Inspiration: http://httpd.apache.org/docs/2.0/mod/mod_proxy.html
 *
 * @see <a href="https://github.com/mitre/HTTP-Proxy-Servlet/blob/master/src/main/java/org/mitre/dsmiley/httpproxy/ProxyServlet.java">Origin</a>
 * @author David Smiley dsmiley@mitre.org
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public abstract class HttpForwarder
        extends Configurable {

    /**
     * These are the "hop-by-hop" headers that should not be copied.
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html I use an HttpClient
     * HeaderGroup class instead of Set<String> because this approach does case
     * insensitive lookup faster.
     */
    protected static final HeaderGroup hopByHopHeaders;

    static {
        hopByHopHeaders = new HeaderGroup();
        String[] headers = new String[] { "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
            "TE", "Trailers", "Transfer-Encoding", "Upgrade" };
        for (String header : headers) {
            hopByHopHeaders.addHeader( new BasicHeader( header, null ) );
        }
    }


    /**
     * Encodes characters in the query or fragment part of the URI.
     *
     * <p>
     * Unfortunately, an incoming URI sometimes has characters disallowed by the
     * spec. HttpClient insists that the outgoing proxied request has a valid URI
     * because it uses Java's {@link URI}. To be more forgiving, we must escape the
     * problematic characters. See the URI class for the spec.
     *
     * @param in example: name=value&foo=bar#fragment
     */
    protected static CharSequence encodeUriQuery( CharSequence in ) {
        // Note that I can't simply use URI.java to encode because it will escape
        // pre-existing escaped things.
        StringBuilder outBuf = null;
        Formatter formatter = null;
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt( i );
            boolean escape = true;
            if (c < 128) {
                if (asciiQueryChars.get( (int)c )) {
                    escape = false;
                }
            }
            else if (!Character.isISOControl( c ) && !Character.isSpaceChar( c )) {// not-ascii
                escape = false;
            }
            if (!escape) {
                if (outBuf != null)
                    outBuf.append( c );
            }
            else {
                // escape
                if (outBuf == null) {
                    outBuf = new StringBuilder( in.length() + 5 * 3 );
                    outBuf.append( in, 0, i );
                    formatter = new Formatter( outBuf );
                }
                // leading %, 0 padded, width 2, capital hex
                formatter.format( "%%%02X", (int)c );// TODO
            }
        }
        return outBuf != null ? outBuf : in;
    }

    protected static final BitSet asciiQueryChars;
    
    static {
        char[] c_unreserved = "_-!.~'()*".toCharArray();// plus alphanum
        char[] c_punct = ",;:$&+=".toCharArray();
        char[] c_reserved = "?/[]@".toCharArray();// plus punct

        asciiQueryChars = new BitSet( 128 );
        for (char c = 'a'; c <= 'z'; c++)
            asciiQueryChars.set( (int)c );
        for (char c = 'A'; c <= 'Z'; c++)
            asciiQueryChars.set( (int)c );
        for (char c = '0'; c <= '9'; c++)
            asciiQueryChars.set( (int)c );
        for (char c : c_unreserved)
            asciiQueryChars.set( (int)c );
        for (char c : c_punct)
            asciiQueryChars.set( (int)c );
        for (char c : c_reserved)
            asciiQueryChars.set( (int)c );

        asciiQueryChars.set( (int)'%' );// leave existing percent escapes in place
    }

}
