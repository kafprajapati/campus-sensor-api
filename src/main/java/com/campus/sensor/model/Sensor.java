/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.model;

/**
 *
 * @author kafin
 */

public class Sensor {
    private String sensorId;
    private String roomId;
    private String type;
    private String status;
    private double currentValue;

    public Sensor() {}

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }
}