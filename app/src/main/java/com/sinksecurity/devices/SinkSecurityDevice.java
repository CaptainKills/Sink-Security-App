package com.sinksecurity.devices;

import java.net.Inet4Address;
import java.net.InetAddress;

public class SinkSecurityDevice {

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
     * IP Address of the Device
     */
    public void setIp(InetAddress ip) {
        this.ip = ip;
    }
}
