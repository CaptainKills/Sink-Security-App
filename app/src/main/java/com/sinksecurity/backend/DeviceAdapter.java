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

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    public interface onItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setItemClickListener(onItemClickListener listener){
        this.clickListener = listener;
    }

    private LinkedHashMap<String, SinkSecurityDevice> deviceList;
    private onItemClickListener clickListener;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public ImageView deviceImageView;
        public TextView deviceNameView;
        public TextView deviceDescriptionView;
        public ImageView deviceDeleteImage;

        public DeviceViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);

            deviceImageView = itemView.findViewById(R.id.device_icon);
            deviceNameView = itemView.findViewById(R.id.device_name);
            deviceDescriptionView = itemView.findViewById(R.id.device_description);
            deviceDeleteImage = itemView.findViewById(R.id.device_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deviceDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

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
