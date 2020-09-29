package com.sinksecurity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.sinksecurity.R;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

public class DevicePageActivity extends AppCompatActivity {

    SinkSecurityDevice clickedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_page);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_device_page);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        clickedDevice = intent.getParcelableExtra("SinkSecurityDevice");
        setPageContents();
    }

    /**
     * Configures all page contents with the SinkSecurityDevice information
     */
    private void setPageContents(){

    }

    public void onDeviceDelete(View view){
        DeviceManager.removeDevice(clickedDevice);
        int position = DeviceManager.getDevicePosition(clickedDevice);
        DeviceManager.getDeviceAdapter().notifyItemRemoved(position);

        NavUtils.navigateUpFromSameTask(this);
    }

}
