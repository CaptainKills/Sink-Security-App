package com.sinksecurity.backend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinksecurity.R;
import com.sinksecurity.devices.SinkSecurityDevice;

import java.util.LinkedHashMap;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.DeviceViewHolder> {

    private LinkedHashMap<String, SinkSecurityDevice> deviceList;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public ImageView deviceImageView;
        public TextView deviceNameView;
        public TextView deviceDescriptionView;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceImageView = itemView.findViewById(R.id.device_icon);
            deviceNameView = itemView.findViewById(R.id.device_name);
            deviceDescriptionView = itemView.findViewById(R.id.device_description);
        }
    }

    public CustomAdapter(LinkedHashMap<String, SinkSecurityDevice> deviceList){
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        DeviceViewHolder cvh = new DeviceViewHolder(v);

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
