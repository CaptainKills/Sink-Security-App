package com.sinksecurity.backend;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sinksecurity.R;
import com.sinksecurity.SinkSecurityApp;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

public class DeviceStatusCheckService extends JobService {

    private static final String TAG = "StatusCheckService";
    private RequestQueue requestQueue;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Started!");
        checkDeviceStatus(params);

        return true;
    }

    private void checkDeviceStatus(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String jsonDevice = params.getExtras().getString("SinkSecurityDevice");
                final SinkSecurityDevice device = gson.fromJson(jsonDevice, SinkSecurityDevice.class);

                requestQueue = Volley.newRequestQueue(DeviceStatusCheckService.this);
                String url = "http://" + device.getIp() + ":80/";

                Log.d(TAG, "Send Status Request to " + device.getName() + " @URL: " + url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Request: " + response);
                        switch (response){
                            case "LEVEL_0":
                                //sendNotification(getResources().getString(R.string.status_level_0), device);
                                break;
                            case "LEVEL_1":
                                sendNotification(getResources().getString(R.string.status_level_1), device);
                                break;
                            case "LEVEL_2":
                                sendNotification(getResources().getString(R.string.status_level_2), device);
                                break;
                            case "LEVEL_3":
                                sendNotification(getResources().getString(R.string.status_level_3), device);
                                break;
                            default:
                                sendNotification(getResources().getString(R.string.device_status_error_text), device);
                                break;
                        }
                        Log.d(TAG, "Job Finished! - Device: " + device.getName());
                        jobFinished(params, true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Request error for " + device.getName() + ": " + error.toString());
                        Log.d(TAG, "Job Finished! - Device: " + device.getName());
                        jobFinished(params, true);
                    }
                });

                requestQueue.add(stringRequest);
            }
        }).start();
    }

    private void sendNotification(String text, SinkSecurityDevice device){
        Notification notification = new NotificationCompat.Builder(this, SinkSecurityApp.CHANNEL_DEVICE_INFO_ID)
                .setSmallIcon(device.getDeviceImage())
                .setContentTitle(device.getName() + " status update:")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(DeviceManager.getDevicePosition(device), notification);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job Cancelled!");
        requestQueue.stop();
        return false;
    }
}
