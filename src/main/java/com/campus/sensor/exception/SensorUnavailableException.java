/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.exception;

/**
 *
 * @author kafin
 */

public class SensorUnavailableException extends RuntimeException {
    private final String sensorId;
    private final String currentStatus;

    public SensorUnavailableException(String sensorId, String currentStatus) {
        super(String.format(
            "Sensor '%s' is currently in '%s' status and cannot accept new readings. " +
            "Please bring the sensor back online before posting data.",
            sensorId, currentStatus));
        this.sensorId = sensorId;
        this.currentStatus = currentStatus;
    }
    public String getSensorId() { return sensorId; }
    public String getCurrentStatus() { return currentStatus; }
}