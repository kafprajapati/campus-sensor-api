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

*Part 1* - **Q1: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures to prevent data loss or race conditions.**

For each incoming HTTP request, JAX-RS by default generates a new instance of each resource class. The default lifespan defined in the JAX-RS specification is known as per-request scope.

In my implementation, this meant I could not store data inside the resource classes themselves because that data would be lost after every request. To solve this I created a separate DataStore class which holds static final LinkedHashMap collections for rooms, sensors and readings. Being static, these maps belong to the class itself and stay alive for the entire lifetime of the JVM, shared across all requests.

However, there is a chance that race conditions will arise. A standard LinkedHashMap is not thread-safe and may corrupt its internal state if two requests arrive simultaneously and attempt to write to the same map. I would use ConcurrentHashMap or the synchronized keyword on write operations in a production system to fix this.

---

**Q2: Why is the provision of Hypermedia (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?**

Hypermedia as the Engine of Application State is referred to as HATEOAS. This means that without hardcoding any URLs, API answers contain connections to adjacent resources so that a client can browse the entire API by following those links, just like clicking links on a webpage.

In my implementation, the discovery endpoint at GET / returns links to /rooms and /sensors. Without the requirement for external documentation, a client can locate everything by starting at the root.

This, in my opinion, is an important indicator of advanced RESTful design since the client is totally independent of particular URL structures. The client continues to function by following the links it receives even if the API modifies its pathways. Additionally, the API starts to self-document at runtime, which is quite helpful for developers from outside the company. Because of this, HATEOAS is regarded as the most advanced RESTful design level at Level 3 of the Richardson Maturity Model.

---

*Part 2* - **Q1: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.**

Returning just IDs would reduce the size of the response and save bandwidth. The N+1 problem, however, would need the client to make N more questions in order to retrieve the information for each room. For instance, the client would require 101 search requests in total if there were 100 rooms, which would significantly increase latency and server load.

Because it is far more efficient for common use scenarios, such as presenting a list in a user interface, I decided to deliver whole room objects in a single response in my solution. Pagination is the best option for really huge collections. When entire objects are returned, the customer receives everything they require in a single round trip.

---

**Q2: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.**

In my implementation, DELETE is indeed idempotent. Idempotency is defined by the HTTP protocol as performing the same action more than once and obtaining the same server state.

The first DELETE on a valid room in my code eliminates it and returns 200 OK. Any further DELETE for the same roomId returns 404 Not Found since there isn't a room. After every call, the server state is the same-the room does not exist-despite the different status codes. Repeated DELETE calls do not produce or corrupt any data. The RFC 7231 concept of idempotency is completely satisfied by this.

---

*Part 3* - **Q1: We explicitly use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?**

I specified that my POST endpoints only accept requests with Content-Type: application/json by using the @Consumes(MediaType.APPLICATION_JSON) annotation. Before my method body ever runs, Jersey intercepts and rejects the request if a client delivers text/plain or application/xml instead, immediately returning HTTP 415 Unsupported Media Type.

This is crucial since it guarantees that the request body is always valid JSON before any of my business logic is executed. Additionally, it enforces a clear contract; every developer utilising the API is aware of the precise format that is needed. Jackson deserialisation issues could spread across the program in the absence of this annotation due to unexpected formats.

---

**Q2: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?**

Instead of putting the type in the URL path like /sensors/type/TEMPERATURE, I used @QueryParam for the type filter in my implementation, such as GET /sensors?type=TEMPERATURE.

Because query parameters modify what is returned without altering the resource being queried, they are semantically correct for filtering, which is why I picked them. Type/TEMPERATURE is not a distinct, identifiable resource, as a path-based approach would suggest. Additionally, query parameters are composable, so I could easily add more filters like?type=CO2&status=ACTIVE, and they are universally accepted by HTTP clients and cache layers as non-structural modifiers. Because they are optional by design, GET /sensors still functions flawlessly without any filters.

---

*Part 4* - **Q1: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path in one massive controller class?**

In my implementation, I handled the readings endpoints using the Sub-Resource Locator approach. I made a distinct SensorReadingResource class and assigned the /sensors/{id}/readings path to it via a locator method rather than declaring each nested path inside a single, large SensorResource class.

For a number of reasons, I thought such an approach was much better. Initially, it assigned a single, distinct task to each class: SensorResource is responsible for managing sensors, whereas SensorReadingResource is responsible for managing readings. Secondly, it makes the code easier to scale, I would just create a new class if I wanted to add /sensors/{id}/alerts later. Third, there is no need to re-validate anything because the sub-resource always runs in the right sensor setting because it automatically obtains the sensorId from the locator method. 

---

*Part 5* - **Q1: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?**

**Q2: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?**

**Q3: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?**
