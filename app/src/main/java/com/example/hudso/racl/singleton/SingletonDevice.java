package com.example.hudso.racl.singleton;

import android.provider.Settings;

import com.example.hudso.racl.bean.DeviceBean;

public class SingletonDevice {

    private static SingletonDevice instance;

    private SingletonDevice() {
    }

    public static SingletonDevice getInstance() {
        if (instance == null) {
            instance = new SingletonDevice();
        }
        return instance;
    }

    private DeviceBean deviceBean;

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }
}
