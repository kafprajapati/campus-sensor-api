/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.exception;

/**
 *
 * @author kafin
 */

import com.campus.sensor.model.ErrorResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse(409, "Conflict", ex.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}

@Provider
class LinkedResourceNotFoundExceptionMapper
        implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException ex) {
        return Response.status(422)
                .entity(new ErrorResponse(422, "Unprocessable Entity", ex.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}

@Provider
class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    @Override
    public Response toResponse(SensorUnavailableException ex) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new ErrorResponse(403, "Forbidden", ex.getMessage()))
                .type(MediaType.APPLICATION_JSON).build();
    }
}

@Provider
class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final java.util.logging.Logger LOGGER =
            java.util.logging.Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable ex) {
        LOGGER.severe("Unhandled exception: " + ex.getMessage());
        java.io.StringWriter sw = new java.io.StringWriter();
        ex.printStackTrace(new java.io.PrintWriter(sw));
        LOGGER.severe(sw.toString());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(500, "Internal Server Error",
                        "An unexpected error occurred. Please contact the API administrator."))
                .type(MediaType.APPLICATION_JSON).build();
    }
}