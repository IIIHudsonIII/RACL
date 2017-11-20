package com.example.hudso.racl.outro;

import android.util.Log;
import android.util.Pair;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.utils.NetworkUtils;
import com.example.hudso.racl.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class DeviceServices extends Services {

    public DeviceServices() {
        this.direction = DEVICE;
    }

    public DeviceBean findById(DeviceBean deviceBean) {
        return findById(deviceBean.getId());
    }

    protected DeviceBean findById(String id) {
        String url = getEvaluatedURL(new Pair<>("id", id));
        String response = NetworkUtils.getJSONFromAPI(url, NetworkUtils.GET);
        if (response != null && !"".equals(response.trim())) {
            try {
                JSONObject jsonDevice = new JSONObject(response);
                return jsonToDeviceBean(jsonDevice);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private DeviceBean jsonToDeviceBean(JSONObject jsonDevice) throws JSONException {
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setId(jsonDevice.getString("id"));
        deviceBean.setName(jsonDevice.getString("name"));
        deviceBean.setInformation(jsonDevice.getString("information"));
        deviceBean.setLast_latitude(jsonDevice.getDouble("last_latitude"));
        deviceBean.setLast_longitude(jsonDevice.getDouble("last_longitude"));
        return deviceBean;
    }


    public DeviceBean update(DeviceBean deviceBean) {
        InputStream inputStream;
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make update request to the given URL
            HttpPost httpPost = new HttpPost(getEvaluatedURL());

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", deviceBean.getId());
            jsonObject.accumulate("name", deviceBean.getName());
            jsonObject.accumulate("information", deviceBean.getInformation());
            jsonObject.accumulate("last_latitude", deviceBean.getLast_latitude());
            jsonObject.accumulate("last_longitude", deviceBean.getLast_longitude());
            deviceBean = null;

            // 4. convert JSONObject to JSON to String
            String json = jsonObject.toString();

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

            // 8. Execute update request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {
                deviceBean = jsonToDeviceBean( new JSONObject(Utils.parseInputStreamToString(inputStream)));
            }

            System.out.println("Hudson - resultado? - " + deviceBean);

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return deviceBean;


        /*
        // Outra forma - Não funcionou
        String jsonAsString = "";

        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            URL apiEnd = new URL(url);

            connection = (HttpURLConnection) apiEnd.openConnection();
            connection.setRequestMethod("update");
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
    }

/*
// Outra forma de update: Testar
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("update");
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

        String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        String request        = "http://example.com/index.php";
        URL    url            = new URL( request );
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "update" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try(
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write( postData );
        }
 */
}
