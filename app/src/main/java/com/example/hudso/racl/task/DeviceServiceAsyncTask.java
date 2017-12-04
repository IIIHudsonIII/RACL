package com.example.hudso.racl.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.example.hudso.racl.bean.DeviceBean;
import com.example.hudso.racl.outro.DeviceServices;
import com.example.hudso.racl.singleton.SingletonDevice;

/**
 * @author hudso
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

    public void find(String id) {
        find(new DeviceBean(id));
    }

    public void find(DeviceBean deviceBean) {
        execute(ActionEnum.FIND, deviceBean);
    }

    public void update(DeviceBean deviceBean) {
        execute(ActionEnum.UPDATE, deviceBean);
    }

    protected void execute(@NonNull DeviceServiceAsyncTask.ActionEnum action, DeviceBean deviceBean) {
        if (deviceBean != null) {
            System.out.println("Hudson - ID do dispositivo: " + deviceBean.getId());
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
        System.out.println("Hudson.DeviceServiceAsyncTask - Dispositivo autenticado: " + deviceBean);
        SingletonDevice.getInstance().setDeviceBean(deviceBean);

        if (deviceBean != null) {
            behaviour.success();
        } else {
            behaviour.failed();
        }
    }
}
