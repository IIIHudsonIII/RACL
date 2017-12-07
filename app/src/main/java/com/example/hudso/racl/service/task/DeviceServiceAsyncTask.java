package com.example.hudso.racl.service.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.service.DeviceServices;
import com.example.hudso.racl.singleton.SingletonDevice;

/**
 * Class to execute route services.
 * Use services defined on class <code>{@link DeviceServices}</code>.
 *
 * @author Hudson Henrique Lopes
 * @since 03/12/2017
 */
public final class DeviceServiceAsyncTask extends AsyncTask<Pair<DeviceServiceAsyncTask.ActionEnum, DeviceBean>, Void, DeviceBean> {

    public enum ActionEnum {
        FIND, UPDATE
    }

    private Behaviour behaviour;

    public DeviceServiceAsyncTask(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    public interface Behaviour {
        void success();

        void failed();
    }

    /**
     * Search device by ID.
     *
     * @param id
     */
    public void find(String id) {
        find(new DeviceBean(id));
    }

    /**
     * Search device.
     *
     * @param deviceBean
     */
    public void find(DeviceBean deviceBean) {
        execute(ActionEnum.FIND, deviceBean);
    }

    /**
     * Update device location.
     *
     * @param deviceBean
     */
    public void update(DeviceBean deviceBean) {
        execute(ActionEnum.UPDATE, deviceBean);
    }

    protected void execute(@NonNull DeviceServiceAsyncTask.ActionEnum action, DeviceBean deviceBean) {
        if (deviceBean != null) {
            System.out.println("RACL.LOG - (" + action.name() + ") Device ID : " + deviceBean.getId());
            super.execute(new Pair<>(action, deviceBean));
        } else {
            this.behaviour.failed();
        }
    }

    @Override
    protected DeviceBean doInBackground(Pair<DeviceServiceAsyncTask.ActionEnum, DeviceBean>... params) {
        DeviceServices ds = new DeviceServices();
        switch (params[0].first) {
            case FIND:
                return ds.findById(params[0].second);
            case UPDATE:
                return ds.update(params[0].second);
            default:
        }
        return null;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onPostExecute(DeviceBean deviceBean) {
        System.out.println("RACL.LOG - Found device on services: " + deviceBean);
        SingletonDevice.getInstance().setDeviceBean(deviceBean);

        if (deviceBean != null) {
            behaviour.success();
        } else {
            behaviour.failed();
        }
    }
}
