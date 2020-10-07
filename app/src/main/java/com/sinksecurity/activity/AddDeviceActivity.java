package com.sinksecurity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.android.material.snackbar.Snackbar;
import com.sinksecurity.R;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

public class AddDeviceActivity extends AppCompatActivity {

    private int selectedDeviceIconID = R.drawable.ic_bathroom_icon;
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

        buildLayout();
    }

    private void buildLayout(){
        RadioGroup iconRadioGroup = (RadioGroup) findViewById(R.id.icon_radioGroup);
        nameInputView = (EditText) findViewById(R.id.name_input);
        ipInputView = (EditText) findViewById(R.id.ip_input);

        iconRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_bathtub:
                        selectedDeviceIconID = R.drawable.ic_bathroom_icon;
                        break;
                    case R.id.radio_sink_normal:
                        selectedDeviceIconID = R.drawable.ic_regular_tap_icon;
                        break;
                    case R.id.radio_sink_outside:
                        selectedDeviceIconID = R.drawable.ic_outside_tap_icon;
                        break;
                    default:
                        selectedDeviceIconID = R.drawable.ic_android;
                        break;
                }
            }
        });
    }


    public void addDevice(View view){
        String deviceName = nameInputView.getText().toString();
        String deviceIp = ipInputView.getText().toString();

        //Check for errors in the input fields.
        if(nameInputView.length() == 0 || ipInputView.length() == 0){
            Snackbar.make(view, "Please fill in all fields before adding a device.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if(DeviceManager.getDeviceList().keySet().contains(deviceName)){
            Snackbar.make(view, "This Device name already exists.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        //Create new Device from input, and add it to the DeviceList
        SinkSecurityDevice device = new SinkSecurityDevice(selectedDeviceIconID, deviceName, deviceIp);
        DeviceManager.addDevice(device);
        DeviceManager.saveData(this);
        Snackbar.make(view, "Device got successfully added!", Snackbar.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(this);
    }

    public void cancelDevice(View view){
        NavUtils.navigateUpFromSameTask(this);
    }
}