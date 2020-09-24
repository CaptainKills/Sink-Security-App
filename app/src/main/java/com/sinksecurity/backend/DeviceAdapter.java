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

    /**
     * Interface of the onItemClickListener. This interface holds all the methods that should be
     * implemented when a new onClickListener is implemented.
     */
    public interface onItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

     /**
     * Sets the onItemClickListener of the DeviceAdapter.
     * @param listener
     * The onItemCLickListener.
     */
    public void setItemClickListener(onItemClickListener listener){
        this.clickListener = listener;
    }

    private LinkedHashMap<String, SinkSecurityDevice> deviceList;
    private onItemClickListener clickListener;

    /**
     * Constructor of the DeviceAdapter. This will initialise the list of devices that will be used
     * to make the list and display it to the user.
     * @param deviceList
     * The corresponding list of devices that will be displayed.
     */
    public DeviceAdapter(LinkedHashMap<String, SinkSecurityDevice> deviceList){
        this.deviceList = deviceList;
    }

    /**
     * This method inflates every item we parse in the deviceList.
     * @param parent
     * Parent object of the View. In our case the RecyclerView.
     * @param viewType
     * ViewType
     * @return Returns a DeviceViewHolder object corresponding to the itemView.
     */
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        DeviceViewHolder dvh = new DeviceViewHolder(v, clickListener);

        return dvh;
    }

    /**
     * This method binds the DeviceViewHolder to an actual device in the list. It links the views
     * of the itemView to the corresponding SinkSecurityDevice paramters (i.e. name, ip, etc.)
     * @param holder
     * The DeviceViewHolder that inflates the current SSDevice into a cardView.
     * @param position
     * Position in the list.
     */
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
