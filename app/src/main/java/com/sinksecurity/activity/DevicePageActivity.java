package com.sinksecurity.activity;

import android.os.Bundle;
import android.view.View;
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

    private TextView text;

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
        text = findViewById(R.id.version_textView);
        text.setText("Checking status...");
    }

    private void createRequestQueue(){
        requestQueue = Volley.newRequestQueue(DevicePageActivity.this);
        String url = "http://" + clickedDevice.getIp() + ":80/";
        System.out.println("Device URL: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                text.setText(response);
                System.out.println("Request: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                text.setText(error.toString());
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
