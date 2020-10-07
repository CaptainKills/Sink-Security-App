package com.sinksecurity.devices;

import android.os.Parcel;
import android.os.Parcelable;

public class SinkSecurityDevice implements Parcelable {

    private int deviceImage;
    private String deviceName;
    private String deviceIp;

    /**
     * Class that represents an instance of SinkSecurityDevice. Keeps tracks of Device variables.
     *
     * @param name
     * Name of the device.
     * @param ip
     * Internet IP address of the devices. Used to connect to the Arduino Internet Module.
     */
    public SinkSecurityDevice(int deviceImage, String name, String ip){
        this.deviceImage = deviceImage;
        this.deviceName = name;
        this.deviceIp = ip;
    }

    protected SinkSecurityDevice(Parcel in) {
        deviceImage = in.readInt();
        deviceName = in.readString();
        deviceIp = in.readString();
    }

    public static final Creator<SinkSecurityDevice> CREATOR = new Creator<SinkSecurityDevice>() {
        @Override
        public SinkSecurityDevice createFromParcel(Parcel in) {
            return new SinkSecurityDevice(in);
        }

        @Override
        public SinkSecurityDevice[] newArray(int size) {
            return new SinkSecurityDevice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deviceImage);
        dest.writeString(deviceName);
        dest.writeString(deviceIp);
    }

    /**
     * @return Returns the Device Image
     */
    public int getDeviceImage() {
        return deviceImage;
    }

    /**
     * Sets the Device Image.
     * @param deviceImage
     * Image (icon) of the Device
     */
    public void setDeviceImage(int deviceImage) {
        this.deviceImage = deviceImage;
    }

    /**
     * @return Returns the Device Name
     */
    public String getName() {
        return deviceName;
    }

    /**
     * Sets the Device Name.
     * @param name
     * Name of the Device
     */
    public void setName(String name) {
        this.deviceName = name;
    }

    /**
     * @return Returns the Device IP Address
     */
    public String getIp() {
        return deviceIp;
    }

    /**
     * Sets the Device IP Address.
     * @param ip
     * IP Address of the Device as an InetAddress
     */
    public void setIp(String ip) {
        this.deviceIp = ip;
    }
}
