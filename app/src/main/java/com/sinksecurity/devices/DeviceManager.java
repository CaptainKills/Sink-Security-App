package com.sinksecurity.devices;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinksecurity.R;
import com.sinksecurity.backend.DeviceAdapter;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeviceManager {

    private static LinkedHashMap<String, SinkSecurityDevice> deviceList;
    private static DeviceAdapter deviceAdapter;

    public static LinkedHashMap<String, SinkSecurityDevice> getDeviceList(){
        return deviceList;
    }

    public static void addDevice(SinkSecurityDevice device){
        deviceList.put(device.getName(), device);
        deviceAdapter.notifyItemInserted(getDevicePosition(device));
    }

    public static void removeDevice(SinkSecurityDevice device){
        deviceList.remove(device.getName());
        deviceAdapter.notifyItemRemoved(getDevicePosition(device));
    }

    public static void removeDevice(int position){
        deviceList.remove(getDevice(position).getName());
        deviceAdapter.notifyItemRemoved(position);
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

    public static int getDevicePosition(SinkSecurityDevice device){
        int position = 0;
        for(Map.Entry<String, SinkSecurityDevice> entry : deviceList.entrySet()){
            if(device.getName().equals(entry.getValue().getName())){
                System.out.println("Positon of Device: " + position);
                break;
            }
            position++;
        }
        return position;
    }

    public static SinkSecurityDevice getDevice(int position){
        SinkSecurityDevice device = null;
        int i = 0;
        for(Map.Entry<String, SinkSecurityDevice> entry : deviceList.entrySet()){
            if(i == position){
                device =  entry.getValue();
                System.out.println("Device Found!");
                break;
            }
            i++;
        }

        return device;
    }

    public static int getDeviceListSize(){
        return deviceList.size();
    }

    public static void setDeviceAdapter(DeviceAdapter adapter){
        deviceAdapter = adapter;
    }
}
