
# Livi Demo Health Application

What this app does.
-------

This app will ping every Url stored in the database Asynchronously, every 10 seconds. 
Persisting an OK or FAIL status for that service, if that external service is up or down.


This application also acts as an API to create, update and delete external Services. 

Setup
-------
I've set up a Docker environment for this application to run along with a **MySql** database.

This application has a frontend - https://github.com/fraserc12/livi-frontend
Please see instructions for that applcation in the respective ReadMe. 

Please add service via the **Swagger UI** so there is data to display on the frontend. 

This frontend app only displays health status - it does not have create, update, delete capabilities. 

## Docker

`docker-compose up`

Sets up a MySQL Database - which persists data. 

Once docker is running you can call the API at `http://localhost:8182/`.


### Creating, Updating, Deleting Services API Spec:

Please access Swagger for API docs at http://localhost:8082/swagger-ui.html


#### URL validation

I added Apache's UrlValidator to validate URLs. 
