package com.sinksecurity.devices;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinksecurity.R;
import com.sinksecurity.backend.DeviceAdapter;
import com.sinksecurity.backend.DeviceStatusCheckService;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class that will keep track of all the devices currently in the application. It will also take care
 * of saving and loading data from and to the storage.
 */
public class DeviceManager {

    private static final String TAG = "DeviceManager";

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
        Log.d(TAG, "Device Added: " + device.getName());
    }

    /**
     * Method to remove a SinkSecurityDevice from the deviceList. Also updates the UI List.
     * @param device
     * The SinkSecurityDevice to be removed from the list.
     */
    public static void removeDevice(SinkSecurityDevice device){
        deviceList.remove(device.getName());
        deviceAdapter.notifyItemRemoved(getDevicePosition(device));
        Log.d(TAG, "Device Removed: " + device.getName());
    }

    /**
     * Method to remove a SinkSecurityDevice from the deviceList. Also updates the UI List.
     * @param position
     * The position of the to be removed SinkSecurityDevice.
     */
    public static void removeDevice(int position){
        deviceList.remove(getDevice(position).getName());
        deviceAdapter.notifyItemRemoved(position);
        Log.d(TAG, "Device Removed: " + getDevice(position).getName());
    }

    /**
     * Method that will save the current DeviceList to storage.
     * @param context
     * Context in which we can access the SharedPreferences. This is necessary to sava data.
     */
    public static void saveData(Context context){
        Log.d(TAG, "Saving Device Data!");
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.device_manager_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(deviceList);

        editor.putString(context.getString(R.string.device_list_file_key), json);
        editor.apply();
        Log.d(TAG, "Data Saved Successfully!");
    }

    /**
     * Method that will load the current DeviceList from storage.
     * @param context
     * Context in which we can access the SharedPreferences. This is necessary to load data.
     */
    public static void loadData(Context context){
        Log.d(TAG, "Loading Device Data!");
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.device_manager_file_key), Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString(context.getString(R.string.device_list_file_key), null);
        Type type = new TypeToken<LinkedHashMap<String, SinkSecurityDevice>>(){}.getType();

        deviceList = gson.fromJson(json, type);
        if(deviceList == null){
            deviceList = new LinkedHashMap<String, SinkSecurityDevice>();
            Log.d(TAG, "No Saved Data present: Creating New DeviceList");
        } else{
            Log.d(TAG, "Checking Loaded DeviceList: " + deviceList.size());
            for(Map.Entry<String, SinkSecurityDevice> entry : deviceList.entrySet()){
                if(entry.getValue() == null){
                    deviceList.remove(entry.getKey());
                    Log.d(TAG, "Invalid DeviceList entry: " + entry.getKey());
                } else{
                    Log.d(TAG, "Valid DeviceList entry: " + entry.getKey());
                }
            }
            Log.d(TAG, "Checking Loaded DeviceList: " + deviceList.size());
        }
        Log.d(TAG, "Data Loaded Successfully!");
    }

    public static void startDeviceStatusChecks(Context context){
        int i = 0;
        Gson gson = new Gson();

        Log.d(TAG, "Scheduling device status checks.");
        for(SinkSecurityDevice device : deviceList.values()){
            PersistableBundle bundle = new PersistableBundle();
            bundle.putString("SinkSecurityDevice", gson.toJson(device));

            ComponentName componentName = new ComponentName(context, DeviceStatusCheckService.class);
            JobInfo info = new JobInfo.Builder(i, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setPeriodic(15 * 60 * 1000)
                    .setExtras(bundle)
                    .build();

            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int resultCode = jobScheduler.schedule(info);
            
            if(resultCode == JobScheduler.RESULT_SUCCESS){
                Log.d(TAG, "Job Scheduled: " + device.getName());
            } else{
                Log.d(TAG, "Job Schedule Failed: " + device.getName());
            }
            i++;
        }
    }

    public static void stopDeviceStatusChecks(Context context, int position){
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(position);
        Log.d(TAG, "Job Cancelled: " + getDevice(position).getName());
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

    /**
     *
     * @return Returns the DeviceAdapter that takes care of the item list.
     */
    public static DeviceAdapter getDeviceAdapter(){
        return deviceAdapter;
    }
}
