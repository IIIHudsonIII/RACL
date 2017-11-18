package com.example.hudso.racl.outro;

import android.support.test.espresso.core.deps.guava.net.HttpHeaders;
import android.support.test.espresso.core.deps.guava.net.MediaType;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.hudso.racl.utils.NetworkUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DeviceServices extends Services {

    public DeviceServices() {
        this.direction = DEVICE;
    }

    public JSONObject findById(String id) {
        String url = getEvaluatedURL(new Pair("id", id));
        String response = NetworkUtils.getJSONFromAPI(url, NetworkUtils.GET);
        if (response != null && !"".equals(response.trim())) {
            try {
                return new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static int id = 10;

    public String POST(String url) {
        /*String jsonAsString = "";

        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL apiEnd = new URL(url);

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            String json = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", id++);
                jsonObject.accumulate("name", "name " + id);
                jsonObject.accumulate("information", "information " + id);

                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringEntity se = new StringEntity(json);
            connection.set

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
*/

        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(getEvaluatedURL());

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", id++);
            jsonObject.accumulate("name", "name " + id);
            jsonObject.accumulate("information", "information " + id);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                result = parseInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }

            System.out.println("Hudson - resultado? - " + result);

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

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
