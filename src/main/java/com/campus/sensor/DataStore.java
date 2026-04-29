/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor;

/**
 *
 * @author kafin
 */

import com.campus.sensor.model.Room;
import com.campus.sensor.model.Sensor;
import com.campus.sensor.model.SensorReading;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    public static final Map<String, Room> rooms = new LinkedHashMap<>();
    public static final Map<String, Sensor> sensors = new LinkedHashMap<>();
    public static final Map<String, List<SensorReading>> readings = new LinkedHashMap<>();
    
    private DataStore() {}
}
