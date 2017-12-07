package com.example.hudso.racl.util;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Hudson Henrique Lopes
 * @since 03/11/2017
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

            if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            jsonAsString = InputStreamUtils.parseInputStreamToString(is);
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
}
