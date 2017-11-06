package com.example.hudso.racl.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Hudson Henrique Lopes on 03/11/2017.
 */
public class NetworkUtils {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    @StringDef({GET, POST, PUT, DELETE})
    public @interface RequestMethod {
    }

    /**
     * Access <b>API <code>url</code></b> and returns <b>JSON as <code>String</code></b>.
     *
     * @param url    a pointer to a "resource" on the World Wide Web
     * @param method method for the URL_DEFAULT request
     * @return
     */
    public static String getJSONFromAPI(@NonNull String url, @RequestMethod String method, Pair... parameters) {
        return getJSONFromAPI(url, method, 15000, 15000, parameters);
    }

    /**
     * Access <b>API <code>url</code></b> and returns <b>JSON as <code>String</code></b>.
     *
     * @param url            a pointer to a "resource" on the World Wide Web
     * @param method         method for the URL_DEFAULT request
     * @param timeOutRead    specified timeout value, in milliseconds, when reading from Input stream when a connection is established to a resource
     * @param timeOutConnect specified timeout value, in milliseconds, to be used when opening a communications link
     * @param parameters
     * @return
     */
    public static String getJSONFromAPI(@NonNull String url, @RequestMethod String method, int timeOutRead, int timeOutConnect, Pair... parameters) {
        String jsonAsString = "";

        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL apiEnd = new URL(url);

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod(method);
            connection.setReadTimeout(timeOutRead);
            connection.setConnectTimeout(timeOutConnect);
            //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();

/*
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        String input = "{ \"snippet\": {\"playlistId\": \"WL\",\"resourceId\": {\"videoId\": \""+videoId+"\",\"kind\": \"youtube#video\"},\"position\": 0}}";

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : "
                + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
* */
/*
        String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        String request        = "http://example.com/index.php";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try(
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }
 */

            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            jsonAsString = parseInputStreamToString(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return jsonAsString;
    }

    /**
     * Returns <b><code>InputStream</code></b> value as <b><code>String</code></b>.
     *
     * @param is
     * @return
     */
    private static String parseInputStreamToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader bufferedReader;
            String line;

            bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }
}
