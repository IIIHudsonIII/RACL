package com.example.hudso.racl.service;

import android.util.Log;
import android.util.Pair;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.util.NetworkUtils;
import com.example.hudso.racl.util.InputStreamUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Class to define device services.
 *
 * @author Hudson Henrique Lopes
 * @since 03/12/2017
 */
public class DeviceServices extends Services {

    public DeviceServices() {
        this.direction = DEVICE;
    }

    /**
     * Search device.
     *
     * @param deviceBean
     * @return
     */
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

    /**
     * Parse <code>{@link JSONObject}</code> informations to <code>{@link DeviceBean}</code>.
     *
     * @param jsonDevice
     * @return
     * @throws JSONException
     */
    private DeviceBean jsonToDeviceBean(JSONObject jsonDevice) throws JSONException {
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setId(jsonDevice.getString("id"));
        deviceBean.setName(jsonDevice.getString("name"));
        deviceBean.setInformation(jsonDevice.getString("information"));
        deviceBean.setLast_latitude(jsonDevice.getDouble("last_latitude"));
        deviceBean.setLast_longitude(jsonDevice.getDouble("last_longitude"));
        return deviceBean;
    }

    /**
     * Update device informations.
     *
     * @param deviceBean
     * @return
     */
    public DeviceBean update(DeviceBean deviceBean) {
        InputStream inputStream;
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(getEvaluatedURL());

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("id", deviceBean.getId());
            jsonObject.accumulate("name", deviceBean.getName());
            jsonObject.accumulate("information", deviceBean.getInformation());
            jsonObject.accumulate("last_latitude", deviceBean.getLast_latitude());
            jsonObject.accumulate("last_longitude", deviceBean.getLast_longitude());
            deviceBean = null;

            String json = jsonObject.toString();

            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                deviceBean = jsonToDeviceBean(new JSONObject(InputStreamUtils.parseInputStreamToString(inputStream)));
            }

            System.out.println("RACL.LOG - Updated device: " + deviceBean);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return deviceBean;
    }
}
