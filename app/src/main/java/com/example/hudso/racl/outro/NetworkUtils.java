package com.example.hudso.racl.outro;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
     * @param method method for the URL request
     * @return
     */
    public static String getJSONFromAPI(@NonNull String url, @RequestMethod String method) {
        return getJSONFromAPI(url, method, 15000, 15000);
    }

    /**
     * Access <b>API <code>url</code></b> and returns <b>JSON as <code>String</code></b>.
     *
     * @param url            a pointer to a "resource" on the World Wide Web
     * @param method         method for the URL request
     * @param timeOutRead    specified timeout value, in milliseconds, when reading from Input stream when a connection is established to a resource
     * @param timeOutConnect specified timeout value, in milliseconds, to be used when opening a communications link
     * @return
     */
    public static String getJSONFromAPI(@NonNull String url, @RequestMethod String method, int timeOutRead, int timeOutConnect) {
        String jsonAsString = "";

        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL apiEnd = new URL(url);

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod(method);
            connection.setReadTimeout(timeOutRead);
            connection.setConnectTimeout(timeOutConnect);
            connection.connect();

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
