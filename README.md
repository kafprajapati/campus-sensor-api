# Campus Sensor API

A RESTful API for managing campus rooms and IoT sensors, built with JAX-RS (Jersey) and an embedded Grizzly HTTP server. All data is stored using HashMap and ArrayList.

---

# API Overview

- **Base URL:** `http://localhost:8080/`
- **Technology:** JAX-RS (Jersey 2.41), Grizzly HTTP Server
- **Storage:** In-memory HashMap and ArrayList - no database

###Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET |  / | Discovery endpoint |
| GET | /rooms | Lis all rooms |
| POST | /rooms | Create a room |
| GET | /rooms/{id} | Get a specific room |
| DELETE | /rooms/{id} | Delete a room |
| GET | /sensors | List all sensors (supports ?type= filter) |
| POST | /sensors | Register a new sensor |
| GET | /sensors/{id} | Get a specific sensor |
| GET | /sensors/{id}/readings | Get all readings for a sensor |
| POST | /sensors/{id}/readings | Post a new reading |

---

## How to Build and Run

### Prerequisites
- Apache Netbeans 18 (Used this)
- Maven 3.6 or higher

### How I built this project?

I created this project manually in Netbeans by following these steps:

1. Opened Netbeans -> File -> New Project -> Java with Maven -> Java Application
2. Set up all required project details:
   - Project Name: campus-sensor-api
   - Artifact ID: campus-sensor-api
   - Package: com.campus.sensor
3. Created the following packages manually by right-clicking Source Packages -> New -> Java Package:
   - com.campus.sensor
   - com.campus.sensor.model
   - com.campus.sensor.resource
   - com.campus.sensor.exception
   - com.campus.sensor.filter
4. Replaced the pom.xml with the Jersey and Grizzly dependencies
5. Create each Java class manually inside the correct package by right clicking -> New -> Java Class
6. Started to code manually by parts (Part 1, 2, 3, 4 and 5)
7. Right-clicked the project → Clean and Build to download all dependencies
8. Right-clicked the project → Run to start the server

While doing the code, I kept committing to GitHub after completing each part so the progress is visible.

Console ouput when running:
```
===============================================
  Campus Sensor API is running!
  URL: http://localhost:8080/
  Press ENTER to stop the server...
===============================================
```

---
## Sample curl Commands

**1. Discovery**
```bash
curl -X GET http://localhost:8080/
```

**2. Create a room**
```bash
curl -X POST http://localhost:8080/rooms \
   -H "Content-Type: application/json" \
   -d '{"name":"Test Room","location":"Bldg1","floor":1}'
```

**3. Get all rooms**
```bash
curl -X GET http://localhost:8080/rooms
```

**4. Get room by ID**
```bash
curl -X GET http://localhost:8080/rooms/{roomId}
```

**5. Create a sensor**
```bash
curl -X POST http://localhost:8080/sensors \
   -H "Content-Type: application/json" \
   -d '{"roomID": {roomId}, "type": "TEMPERATURE", "status":"ACTIVE"}'
```

**6. Filtor sensors by type**
```bash
curl -X GET "http://localhost:8080/sensors?type=TEMPERATURE"
```

## Sample API Requests

**1. Discovery**
GET http://localhost:8080/

**2. Create a room**
POST http://localhost:8080/rooms
Body:
{
    "name": "Test Room",
    "location": "Bldg1",
    "floor": 1
}

**3. Get all rooms**
GET http://localhost:8080/rooms

**4. Get room by ID**
GET http://localhost:8080/rooms/{roomId}

**5. Create a sensor**
POST http://localhost:8080/sensors
Body:
{
    "roomId": "your-room-id",
    "type": "TEMPERATURE",
    "status": "ACTIVE"
}

**6. Create sensor with invalid roomId - triggers 422**
POST http://localhost:8080/sensors
Body:
{
    "roomId": "INVALID-ID",
    "type": "TEMP",
    "status": "ACTIVE"
}

---
## Report - Answers to Questions

Part 1 - Q1: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures to prevent data loss or race conditions.

Q2: Why is the provision of Hypermedia (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

Part 2 - Q1: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

Q2: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

Part 3 - Q1: We explicitly use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

Q2: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

Part 4 - Q1: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path in one massive controller class?

Part 5 - Q1: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

Q2: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

Q3: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?
