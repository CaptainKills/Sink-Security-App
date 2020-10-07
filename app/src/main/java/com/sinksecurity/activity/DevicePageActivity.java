package com.sinksecurity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinksecurity.R;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

public class DevicePageActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private SinkSecurityDevice clickedDevice;

    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_page);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_device_page);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        clickedDevice = getIntent().getParcelableExtra("SinkSecurityDevice");
        setPageContents();
        createRequestQueue();
    }

    /**
     * Configures all page contents with the SinkSecurityDevice information
     */
    private void setPageContents(){
        ImageView imageView = findViewById(R.id.page_device_icon);
        imageView.setImageResource(clickedDevice.getDeviceImage());

        TextView deviceNameView = findViewById(R.id.page_device_name);
        deviceNameView.setText(clickedDevice.getName());

        statusText = findViewById(R.id.page_device_status);
        statusText.setText(R.string.device_status_check_text);

        TextView deviceIpView = findViewById(R.id.page_device_ip);
        deviceIpView.setText(clickedDevice.getIp());
    }

    private void createRequestQueue(){
        requestQueue = Volley.newRequestQueue(DevicePageActivity.this);
        String url = "http://" + clickedDevice.getIp() + ":80/";
        System.out.println("Device URL: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Request: " + response);
                switch (response){
                    case "LEVEL_0":
                        statusText.setText(R.string.status_level_0);
                        break;
                    case "LEVEL_1":
                        statusText.setText(R.string.status_level_1);
                        break;
                    case "LEVEL_2":
                        statusText.setText(R.string.status_level_2);
                        break;
                    case "LEVEL_3":
                        statusText.setText(R.string.status_level_3);
                        break;
                    default:
                        statusText.setText(R.string.device_status_error_text);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statusText.setText(R.string.device_status_failed_text);
                System.out.println("Request error: " + error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    public void onDeviceDelete(View view){
        DeviceManager.removeDevice(clickedDevice);
        int position = DeviceManager.getDevicePosition(clickedDevice);
        DeviceManager.getDeviceAdapter().notifyItemRemoved(position);

        NavUtils.navigateUpFromSameTask(this);
    }

}