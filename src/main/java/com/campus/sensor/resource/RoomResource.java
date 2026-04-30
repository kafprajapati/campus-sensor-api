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
import com.campus.sensor.model.Room;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAllRooms() {
        List<Room> allRooms = new ArrayList<>(DataStore.rooms.values());
        return Response.ok(allRooms).build();
    }

    @POST
    public Response createRoom(Room room) {
        if (room.getRoomId() == null || room.getRoomId().isEmpty()) {
            room.setRoomId(UUID.randomUUID().toString());
        }
        DataStore.rooms.put(room.getRoomId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Room not found: " + roomId))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(404, "Not Found", "Room not found: " + roomId))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        long sensorCount = DataStore.sensors.values().stream()
                .filter(s -> roomId.equals(s.getRoomId()))
                .count();

        if (sensorCount > 0) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(409, "Conflict",
                        "Room '" + roomId + "' cannot be deleted because it still has "
                        + sensorCount + " active sensor(s) assigned to it."))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        DataStore.rooms.remove(roomId);
        return Response.ok()
                .entity(new ErrorResponse(200, "OK",
                        "Room " + roomId + " deleted successfully."))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}