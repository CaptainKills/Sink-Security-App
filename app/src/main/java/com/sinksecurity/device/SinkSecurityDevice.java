package com.sinksecurity.device;

public class SinkSecurityDevice {

    private String name;
    private int ip;

    /**
     * Class that represents an instance of SinkSecurityDevice. Keeps tracks of Device variables.
     *
     * @param name
     * Name of the device.
     * @param ip
     * Internet IP address of the devices. Used to connect to the Arduino Internet Module.
     */
    public SinkSecurityDevice(String name, int ip){

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
     * Returns the Device Name
     * @return Inet4Address
     */
    public int getIp() {
        return ip;
    }

    /**
     * Sets the Device IP Address.
     * @param ip
     * IP Address of the Device
     */
    public void setIp(int ip) {
        this.ip = ip;
    }
}
