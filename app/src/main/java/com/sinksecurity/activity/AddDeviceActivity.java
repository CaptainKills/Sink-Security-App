package com.sinksecurity.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.sinksecurity.R;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AddDeviceActivity extends AppCompatActivity {

    private EditText nameInputView;
    private EditText ipInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_add_device);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nameInputView = (EditText) findViewById(R.id.name_input);
        ipInputView = (EditText) findViewById(R.id.ip_input);
    }

    public void addDevice(View view){
        String deviceName = nameInputView.getText().toString();
        String ipString = ipInputView.getText().toString();

        System.out.println("NAME: " + deviceName + ", IP: " + ipString);

        //Check if one of the values is empty (i.e. null)
        if(nameInputView.length() == 0 || ipInputView.length() == 0){
            Snackbar.make(view, "Please fill in all fields before adding a device.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        //Convert String to desired Type if necessary
        InetAddress deviceIP = null;
        try{
            deviceIP = Inet4Address.getByName(ipString);
        } catch (UnknownHostException e){
            e.printStackTrace();
        }


        //Create new Device from input, and add it to the DeviceList
        SinkSecurityDevice device = new SinkSecurityDevice(deviceName, deviceIP);
        DeviceManager.addDevice(device);
        Snackbar.make(view, "Device got successfully added!", Snackbar.LENGTH_SHORT).show();
    }

    public void cancelDevice(View view){
        NavUtils.navigateUpFromSameTask(this);
    }
}