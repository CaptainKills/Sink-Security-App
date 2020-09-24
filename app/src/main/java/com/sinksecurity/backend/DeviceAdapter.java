package com.sinksecurity.backend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinksecurity.R;
import com.sinksecurity.devices.DeviceManager;
import com.sinksecurity.devices.SinkSecurityDevice;

import java.util.LinkedHashMap;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    public interface onItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setItemClickListener(onItemClickListener listener){
        this.clickListener = listener;
    }

    private LinkedHashMap<String, SinkSecurityDevice> deviceList;
    private onItemClickListener clickListener;


    public DeviceAdapter(LinkedHashMap<String, SinkSecurityDevice> deviceList){
        this.deviceList = deviceList;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        DeviceViewHolder cvh = new DeviceViewHolder(v, clickListener);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        String deviceKey = (String) deviceList.keySet().toArray()[position];
        SinkSecurityDevice currentDevice = deviceList.get(deviceKey);

        holder.deviceImageView.setImageResource(currentDevice.getDeviceImage());
        holder.deviceNameView.setText(currentDevice.getName());
        holder.deviceDescriptionView.setText(currentDevice.getIp().toString());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}
