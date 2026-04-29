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
import com.campus.sensor.exception.LinkedResourceNotFoundException;
import com.campus.sensor.model.Sensor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> result = new ArrayList<>(DataStore.sensors.values());
        if (type != null && !type.isEmpty()) {
            result = result.stream()
                    .filter(s -> type.equalsIgnoreCase(s.getType()))
                    .collect(Collectors.toList());
        }
        return Response.ok(result).build();
    }

    @POST
    public Response createSensor(Sensor sensor) {
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room", sensor.getRoomId());
        }
        if (sensor.getSensorId() == null || sensor.getSensorId().isEmpty()) {
            sensor.setSensorId(UUID.randomUUID().toString());
        }
        if (sensor.getStatus() == null || sensor.getStatus().isEmpty()) {
            sensor.setStatus("ACTIVE");
        }
        DataStore.readings.put(sensor.getSensorId(), new ArrayList<>());
        DataStore.sensors.put(sensor.getSensorId(), sensor);
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}")
    public Response getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Sensor not found: " + sensorId + "\"}")
                    .build();
        }
        return Response.ok(sensor).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}
