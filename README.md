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

