
# Livi Demo Health Application

Setup
-------
I've set up a Docker environment for this application along with **MySql** database.

This application has a frontend - https://github.com/fraserc12/livi-frontend
Please see instructions for that applcation in the respective ReadMe. 

This frontend app only displays health status - it does not have create, update, delete capabilities. 

## Docker

`docker-compose up`

Sets up a MySQL Database - which persists data. 

Once docker is running you can call the API at `http://localhost:8182/`.


### Creating, Updating, Deleting Services API Spec:

Please access Swagger for API docs at http://localhost:8082/swagger-ui.html
