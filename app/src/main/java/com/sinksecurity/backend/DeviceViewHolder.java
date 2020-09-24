package com.sinksecurity.backend;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinksecurity.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder {
    public ImageView deviceImageView;
    public TextView deviceNameView;
    public TextView deviceDescriptionView;
    public ImageView deviceDeleteImage;

    public DeviceViewHolder(@NonNull View itemView, final DeviceAdapter.onItemClickListener listener) {
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