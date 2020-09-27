package com.sinksecurity.devices;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SinkSecurityDevice implements Parcelable {

    private int deviceImage;
    private String name;
    private InetAddress ip;

    /**
     * Class that represents an instance of SinkSecurityDevice. Keeps tracks of Device variables.
     *
     * @param name
     * Name of the device.
     * @param ip
     * Internet IP address of the devices. Used to connect to the Arduino Internet Module.
     */
    public SinkSecurityDevice(int deviceImage, String name, InetAddress ip){
        this.deviceImage = deviceImage;
        this.name = name;
        this.ip = ip;
    }

    protected SinkSecurityDevice(Parcel in) {
        deviceImage = in.readInt();
        name = in.readString();
        //ip = extractIp(in.readString());
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
        dest.writeString(name);
        //dest.writeString(ip.toString());
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
        return name;
    }

    /**
     * Sets the Device Name.
     * @param name
     * Name of the Device
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the Device IP Address
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * Sets the Device IP Address.
     * @param ip
     * IP Address of the Device as an InetAddress
     */
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    /**
     * Extract IP Address from a String
     * @param ip
     * IP Address of the Device as a String
     */
    public InetAddress extractIp(String ip) {
        InetAddress ip_address = null;
        try{
            ip_address = InetAddress.getByName(ip);
        } catch (UnknownHostException e){
            e.printStackTrace();
        }

        return ip_address;

    }
}
