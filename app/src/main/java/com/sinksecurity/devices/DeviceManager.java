package com.sinksecurity.devices;

import java.util.LinkedHashMap;

public class DeviceManager {

    private static LinkedHashMap<String, SinkSecurityDevice> deviceList = new LinkedHashMap<String, SinkSecurityDevice>();

    public static LinkedHashMap<String, SinkSecurityDevice> getDeviceList(){
        return deviceList;
    }

    public static void addDevice(SinkSecurityDevice device){
        deviceList.put(device.getName(), device);
    }

    public static void removeDevice(SinkSecurityDevice device){
        deviceList.remove(device.getName());
    }
}
