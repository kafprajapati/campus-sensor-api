/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.model;

/**
 *
 * @author kafin
 */
public class Room {
    private String roomId;
    private String name;
    private String location;
    private int floor;

    public Room() {}

    public Room(String roomId, String name, String location, int floor) {
        this.roomId = roomId;
        this.name = name;
        this.location = location;
        this.floor = floor;
    }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
}
