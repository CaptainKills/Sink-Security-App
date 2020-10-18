package com.sinksecurity;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.sinksecurity.devices.DeviceManager;

public class SinkSecurityApp extends Application {

    public static final String CHANNEL_DEVICE_INFO_ID = "channel_device_info";

    @Override
    public void onCreate() {
        super.onCreate();

        DeviceManager.setMainContext(this);
        DeviceManager.loadData();
        DeviceManager.startDeviceStatusChecks();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_DEVICE_INFO_ID,
                    "Device Information",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("This channel is used to notify the user of any warnings received by one of the connected SinkSecurity devices.");
            channel.setLightColor(R.color.colorPrimary);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
