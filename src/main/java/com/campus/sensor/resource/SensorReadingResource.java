/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.resource;

/**
 *
 * @author kafin
 */

import com.campus.sensor.DataStore;
import com.campus.sensor.model.ErrorResponse;
import com.campus.sensor.model.Sensor;
import com.campus.sensor.model.SensorReading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public Response getReadings() {
        if (!DataStore.sensors.containsKey(sensorId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Sensor not found: " + sensorId))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        List<SensorReading> history = DataStore.readings
                .getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(history).build();
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Sensor not found: " + sensorId))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse(403, "Forbidden",
                        "Sensor '" + sensorId + "' is currently in 'MAINTENANCE' status " +
                        "and cannot accept new readings. Please bring the sensor back online."))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (reading.getReadingId() == null || reading.getReadingId().isEmpty()) {
            reading.setReadingId(UUID.randomUUID().toString());
        }
        reading.setSensorId(sensorId);
        DataStore.readings.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);
        sensor.setCurrentValue(reading.getValue());
        DataStore.sensors.put(sensorId, sensor);
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}