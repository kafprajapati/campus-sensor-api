# Campus Sensor API

A RESTful API for managing campus rooms and IoT sensors, built with JAX-RS (Jersey) and an embedded Grizzly HTTP server. All data is stored using HashMap and ArrayList.

# API Overview

- **Base URL:** 'http://localhost:8080/'
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
| GET | /sensors | List all sensors (suppots ?type= filter) |
| POST | /sensors | Register a new sensor |
| GET | /sensors/{id} | Get a specific sensor |
| GET | /sensors/{id}/readings | Get all readings for a sensor |
| POST | /sensors/{id}/readings | Post a new reading |
