package com.sinksecurity.devices;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinksecurity.R;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class DeviceManager {

    private static LinkedHashMap<String, SinkSecurityDevice> deviceList;

    public static LinkedHashMap<String, SinkSecurityDevice> getDeviceList(){
        return deviceList;
    }

    public static void addDevice(SinkSecurityDevice device){
        deviceList.put(device.getName(), device);
    }

    public static void removeDevice(SinkSecurityDevice device){
        deviceList.remove(device.getName());
    }

    public static void saveData(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.device_manager_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(deviceList);

        editor.putString(context.getString(R.string.device_list_file_key), json);
        editor.apply();
    }

    public static void loadData(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.device_manager_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = sharedPref.getString(context.getString(R.string.device_list_file_key), null);
        Type type = new TypeToken<LinkedHashMap<String, SinkSecurityDevice>>(){}.getType();

        deviceList = gson.fromJson(json, type);
        if(deviceList == null){
            deviceList = new LinkedHashMap<String, SinkSecurityDevice>();
        }
    }
}
