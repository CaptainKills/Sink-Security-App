package com.sinksecurity.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.sinksecurity.R;
import com.sinksecurity.device.SinkSecurityDevice;

import java.net.Inet4Address;

public class AddDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_add_device);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void addDevice(View view){
        TextInputEditText nameInput = view.findViewById(R.id.name_input);
        String deviceName = nameInput.getText().toString();

        SinkSecurityDevice device = new SinkSecurityDevice(deviceName, 192);

    }

    public void cancelDevice(View view){
        NavUtils.navigateUpFromSameTask(this);
    }
}