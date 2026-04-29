/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.exception;

/**
 *
 * @author kafin
 */

public class RoomNotEmptyException extends RuntimeException {
    private final String roomId;
    private final int sensorCount;

    public RoomNotEmptyException(String roomId, int sensorCount) {
        super(String.format(
            "Room '%s' cannot be deleted because it still has %d active sensor(s) assigned to it.",
            roomId, sensorCount));
        this.roomId = roomId;
        this.sensorCount = sensorCount;
    }
    public String getRoomId() { return roomId; }
    public int getSensorCount() { return sensorCount; }
}