/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.campus.sensor.exception;

/**
 *
 * @author kafin
 */

public class LinkedResourceNotFoundException extends RuntimeException {
    private final String resourceType;
    private final String resourceId;

    public LinkedResourceNotFoundException(String resourceType, String resourceId) {
        super(String.format(
            "The referenced %s with id '%s' does not exist. " +
            "Please create the %s before linking resources to it.",
            resourceType, resourceId, resourceType));
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
    public String getResourceType() { return resourceType; }
    public String getResourceId() { return resourceId; }
}