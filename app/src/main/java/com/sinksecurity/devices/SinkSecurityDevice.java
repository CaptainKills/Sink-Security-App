package com.sinksecurity.devices;

import java.net.Inet4Address;
import java.net.InetAddress;

public class SinkSecurityDevice {

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
    public SinkSecurityDevice(String name, InetAddress ip){
        this.name = name;
        this.ip = ip;
    }

    /**
     * Returns the Device Name
     * @return String
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
     * Returns the Device IP Address
     * @return Integer
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
