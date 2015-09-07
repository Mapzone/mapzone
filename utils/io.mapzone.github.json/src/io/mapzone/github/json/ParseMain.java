/*
 * polymap.org 
 * Copyright (C) 2015 individual contributors as indicated by the @authors tag. 
 * All rights reserved.
 * 
 * This is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package io.mapzone.github.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

/**
 * @author Joerg Reichert <joerg@mapzone.io>
 *
 */
public class ParseMain {

    static class SearchResult
            implements Comparable<SearchResult> {

        String user;

        String repository;

        String file;

        String lastIndexed;


        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((file == null) ? 0 : file.hashCode());
            result = prime * result + ((lastIndexed == null) ? 0 : lastIndexed.hashCode());
            result = prime * result + ((repository == null) ? 0 : repository.hashCode());
            result = prime * result + ((user == null) ? 0 : user.hashCode());
            return result;
        }


        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals( Object obj ) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SearchResult other = (SearchResult)obj;
            if (file == null) {
                if (other.file != null)
                    return false;
            }
            else if (!file.equals( other.file ))
                return false;
            if (lastIndexed == null) {
                if (other.lastIndexed != null)
                    return false;
            }
            else if (!lastIndexed.equals( other.lastIndexed ))
                return false;
            if (repository == null) {
                if (other.repository != null)
                    return false;
            }
            else if (!repository.equals( other.repository ))
                return false;
            if (user == null) {
                if (other.user != null)
                    return false;
            }
            else if (!user.equals( other.user ))
                return false;
            return true;
        }


        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "{\n\"user\": \"" + user + "\",\n\"repository\": \"" + repository + "\",\n\"file\": \"" + file
                    + "\",\n\"lastIndexed\": \"" + lastIndexed + "\"\n}";
        }


        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo( SearchResult o ) {
            return o.toString().compareTo( this.toString() );
        }
    }


    static class GoogleResults {

        private ResponseData responseData;


        public ResponseData getResponseData() {
            return responseData;
        }


        public void setResponseData( ResponseData responseData ) {
            this.responseData = responseData;
        }


        public String toString() {
            return "ResponseData[" + responseData + "]";
        }


        public static class ResponseData {

            private List<Result> results;


            public List<Result> getResults() {
                return results;
            }


            public void setResults( List<Result> results ) {
                this.results = results;
            }


            public String toString() {
                return "Results[" + results + "]";
            }
        }


        public static class Result {

            private String url;

            private String title;


            public String getUrl() {
                return url;
            }


            public String getTitle() {
                return title;
            }


            public void setUrl( String url ) {
                this.url = url;
            }


            public void setTitle( String title ) {
                this.title = title;
            }


            public String toString() {
                return "Result[url:" + url + ",title:" + title + "]";
            }
        }
    }


    public static void main( String[] args ) throws Exception {
        // aggregateJSONData();
        searchGoogle();
    }


    /**
     * 
     */
    private static void searchGoogle() throws Exception {
        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
        String search = "geometry site:github.com filetype:geojson";
        String charset = "UTF-8";

        URL url = new URL( google + URLEncoder.encode( search, charset ) );
        Reader reader = new InputStreamReader( url.openStream(), charset );
        FileWriter writer = new FileWriter(new File("google.json"));
        List<String> lines = IOUtils.readLines( reader );
        lines.stream().forEach( line -> {try { writer.write( line ); } catch(Exception e) {}} );
        writer.flush();
        writer.close();
        
        GoogleResults results = new Gson().fromJson( reader, GoogleResults.class );
        System.out.println(results);
    }


    private static void aggregateJSONData() throws IOException {
        // queryHTMLSearchResults();
        Set<String> users = new TreeSet<String>();
        Set<String> repositories = new TreeSet<String>();
        Set<SearchResult> results = new HashSet<SearchResult>();
        aggregateJSONResults( "indexed_recently", users, repositories, results );
        aggregateJSONResults( "index_leastrecent", users, repositories, results );
        aggregateJSONResults( "bestmatch", users, repositories, results );
        File csvFile = new File( "all.csv" );
        FileWriter fileWriter = new FileWriter( csvFile );
        fileWriter.write( "user;repository;file;lastIndexed;\n" );
        for (SearchResult result : results) {
            results.add( result );
            fileWriter.write( result.user + ";" + result.repository + ";" + result.file + ";" + result.lastIndexed
                    + ";\n" );
        }
        fileWriter.flush();
        fileWriter.close();
        users.stream().forEach( user -> System.out.println( user ) );
        System.out.println( "-----" );
        repositories.stream().forEach( user -> System.out.println( user ) );
    }


    private static void aggregateJSONResults( String folderName, Set<String> users, Set<String> repositories,
            Set<SearchResult> results ) throws IOException {
        Gson gson = new Gson();
        File folder = new File( folderName );
        for (File file : folder.listFiles()) {
            if (file.toString().endsWith( ".json" )) {
                String content = FileUtils.readFileToString( file );

                content = content.replace( "user:", "\"user:\"" );
                content = content.replace( "repository:", "\"repository:\"" );
                content = content.replace( "file:", "\"file:\"" );
                content = content.replace( "lastIndexed:", "\"lastIndexed:\"" );
                content = content.replace( "\"user:\"", "\"user\":" );
                content = content.replace( "\"repository:\"", "\"repository\":" );
                content = content.replace( "\"file:\"", "\"file\":" );
                content = content.replace( "\"lastIndexed:\"", "\"lastIndexed\":" );
                content = content.replace( ",", "\"," );
                content = content.replace( "\"\"", "" );
                content = content.replace( "{{", "[{" );
                content = content.replace( "}}", "}]" );
                content = content.replace( "}\",", "}," );
                content = content.replace( ",", "\"," );
                content = content.replace( "\"\",", "\"," );
                content = content.replace( "}\",", "}," );

                FileUtils.write( file, content );

                JsonParser parser = new JsonParser();
                JsonElement root = parser.parse( content );
                if (root.isJsonArray()) {
                    SearchResult[] resultArray = gson.fromJson( root, SearchResult[].class );
                    for (SearchResult result : resultArray) {
                        results.add( result );
                        users.add( result.user );
                        repositories.add( result.repository );
                    }
                }
            }
        }
    }


    public static void queryHTMLSearchResults() throws Exception {
        int page = 1;
        StatusLine statusLine = null;
        List<SearchResult> results;
        do {
            results = new ArrayList<SearchResult>();
            if (page % 9 == 0) {
                Thread.sleep( 1000 * 65 );
            }
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet( "https://github.com/search?o=asc&p=" + page
                    + "&q=geometry+extension%3Ageojson&ref=searchresults&s=indexed&type=Code&utf8=%E2%9C%93" );
            // get.addHeader( BasicScheme.authenticate( new
            // UsernamePasswordCredentials( "de.abg.reichert.joerg@googlemail.com",
            // "b" ), "UTF-8",
            // false ) );
            HttpResponse response = client.execute( get );
            statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                try {
                    Reader reader = new InputStreamReader( content );
                    byte[] bytes = IOUtils.toByteArray( reader );
                    String fileContent = new String( bytes );
                    while ((fileContent = parseItem( fileContent, results )) != null) {

                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    content.close();
                }
            }
            else {
                System.out.println( statusLine.getStatusCode() + ": " + statusLine.getReasonPhrase() );
                break;
            }
            FileWriter fw = new FileWriter( new File( "result_" + page + ".json" ) );
            JsonWriter jsonWriter = new JsonWriter( fw );
            jsonWriter.beginArray();
            results.forEach( result -> {
                try {
                    jsonWriter.name( "user" ).value( result.user );
                    jsonWriter.name( "repository" ).value( result.repository );
                    jsonWriter.name( "file" ).value( result.file );
                    jsonWriter.name( "lastIndexed" ).value( result.lastIndexed );
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            } );
            jsonWriter.endArray();
            jsonWriter.close();
            System.out.println( "Written " + page + ".json" );
            page++;
        }
        while (!results.isEmpty());
    }


    private static String parseItem( String fileContent, List<SearchResult> results ) {
        int start = fileContent.indexOf( "<div class=\"code-list-item code-list-item-public \">" );
        if (start >= 0) {
            fileContent = fileContent.substring( start );
            SearchResult result = new SearchResult();
            fileContent = parseUser( fileContent, result );
            fileContent = parseRepository( fileContent, result );
            fileContent = parseFile( fileContent, result );
            fileContent = parseLastIndexed( fileContent, result );
            results.add( result );
            return fileContent;
        }
        return null;
    }


    private static String parseUser( String para, SearchResult result ) {
        String searchStr = "<a href=\"/";
        int start = para.indexOf( searchStr ) + searchStr.length();
        para = para.substring( start );
        int end = para.indexOf( "\"" );
        result.user = "https://github.com/" + para.substring( 0, end );
        return para.substring( end );
    }


    private static String parseRepository( String para, SearchResult result ) {
        String searchStr = "<a href=\"/";
        int start = para.indexOf( searchStr ) + searchStr.length();
        para = para.substring( start );
        int end = para.indexOf( ">" );
        result.repository = "https://github.com/" + para.substring( 0, end );
        return para.substring( end );
    }


    private static String parseLastIndexed( String para, SearchResult result ) {
        String searchStr = "<span class=\"updated-at\">Last indexed <time datetime=\"";
        int start = para.indexOf( searchStr ) + searchStr.length();
        para = para.substring( start );
        int end = para.indexOf( "\" is=\"relative-time\"" );
        result.lastIndexed = para.substring( 0, end );
        return para.substring( end );
    }


    private static String parseFile( String para, SearchResult result ) {
        String searchStr = "<a href=\"/";
        int start = para.indexOf( searchStr ) + searchStr.length();
        para = para.substring( start );
        int end = para.indexOf( "\"" );
        result.file = "https://github.com/" + para.substring( 0, end );
        return para.substring( end );
    }


    public static void queryViaGitHubRESTAPI() throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost( "https://api.github.com//search/code?q=geometry+extension:geojson" );
        HttpResponse response = client.execute( post );
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            try {
                Reader reader = new InputStreamReader( content );
                FileWriter writer = new FileWriter( new File( "output.json" ) );
                char[] cbuf = new char[1024];
                while (reader.read( cbuf ) != -1) {
                    writer.write( cbuf );
                }
                reader.close();
                writer.close();
                content.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
