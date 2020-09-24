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

/**
 * Class that will keep track of all the devices currently in the application. It will also take care
 * of saving and loading data from and to the storage.
 */
public class DeviceManager {

    private static LinkedHashMap<String, SinkSecurityDevice> deviceList;
    private static DeviceAdapter deviceAdapter;

    /**
     *
     * @return Returns the DeviceList that contains all the SSDevices.
     */
    public static LinkedHashMap<String, SinkSecurityDevice> getDeviceList(){
        return deviceList;
    }

    /**
     * Method to add a new SinkSecurityDevice to the deviceList. Also updates the UI List.
     * @param device
     * The SinkSecurityDevice to be added to the list.
     */
    public static void addDevice(SinkSecurityDevice device){
        deviceList.put(device.getName(), device);
        deviceAdapter.notifyItemInserted(getDevicePosition(device));
    }

    /**
     * Method to remove a SinkSecurityDevice from the deviceList. Also updates the UI List.
     * @param device
     * The SinkSecurityDevice to be removed from the list.
     */
    public static void removeDevice(SinkSecurityDevice device){
        deviceList.remove(device.getName());
        deviceAdapter.notifyItemRemoved(getDevicePosition(device));
    }

    /**
     * Method to remove a SinkSecurityDevice from the deviceList. Also updates the UI List.
     * @param position
     * The position of the to be removed SinkSecurityDevice.
     */
    public static void removeDevice(int position){
        deviceList.remove(getDevice(position).getName());
        deviceAdapter.notifyItemRemoved(position);
    }

    /**
     * Method that will save the current DeviceList to storage.
     * @param context
     * Context in which we can access the SharedPreferences. This is necessary to sava data.
     */
    public static void saveData(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.device_manager_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(deviceList);

        editor.putString(context.getString(R.string.device_list_file_key), json);
        editor.apply();
    }

    /**
     * Method that will load the current DeviceList from storage.
     * @param context
     * Context in which we can access the SharedPreferences. This is necessary to load data.
     */
    public static void loadData(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.device_manager_file_key), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString(context.getString(R.string.device_list_file_key), null);
        Type type = new TypeToken<LinkedHashMap<String, SinkSecurityDevice>>(){}.getType();

        deviceList = gson.fromJson(json, type);
        if(deviceList == null){
            deviceList = new LinkedHashMap<String, SinkSecurityDevice>();
        }
    }

    /**
     * Method to get the position in the deviceList of the SinkSecurityDevice.
     * @param device
     * The device that of which the position is to be determined.
     * @return Returns the position of the Device in the list.
     */
    public static int getDevicePosition(SinkSecurityDevice device){
        int position = 0;
        for(Map.Entry<String, SinkSecurityDevice> entry : deviceList.entrySet()){
            if(device.getName().equals(entry.getValue().getName())){
                System.out.println("Position of Device: " + position);
                break;
            }
            position++;
        }
        return position;
    }

    /**
     * Method to get the SinkSecurityDevice from the deviceList.
     * @param position
     * Position of the Device in the deviceList.
     * @return Return the SinkSecurityDevice at that position.
     */
    public static SinkSecurityDevice getDevice(int position){
        String deviceKey = (String) deviceList.keySet().toArray()[position];
        SinkSecurityDevice device = deviceList.get(deviceKey);

        return device;
    }

    /**
     *
     * @return Returns the size of the current deviceList.
     */
    public static int getDeviceListSize(){
        return deviceList.size();
    }

    /**
     * Method to set the DeviceAdapter that is used to access UI view changes.
     * @param adapter
     * DeviceAdapter that will adapt all UI elements.
     */
    public static void setDeviceAdapter(DeviceAdapter adapter){
        deviceAdapter = adapter;
    }
}
