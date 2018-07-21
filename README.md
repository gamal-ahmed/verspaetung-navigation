## Problem

In the fictional city of Verspaetung, public transport is notoriously unreliable. To tackle the problem, the city council has decided to make the public transport timetable and delay information public, opening up opportunities for innovative use cases.

You are given the task of writing a web API to expose the Verspaetung public transport information.

As a side note, the city of Verspaetung has been built on a strict grid - all location information can be assumed to be from a cartesian coordinate system.

# Technologies and concepts

Small Microservice based on :
  * JDK 1.8
  * Spring Boot 
  * JPA
  * h2database
  * Tomcat 8 embedded (No Tomcat  installation is necessary)
  * Maven 3.x
  * Swagger 2 API docs

Concepts 
  * Writing a RESTful service using annotation supports both XML and JSON request / response;
  * Demonstrates MockMVC test framework with associated libraries
  * Build different layers to seperate the application into parts bussines logic , the data base reproistories and endpoint
  
## How to Run 

This application is packaged as a war which has Tomcat 8 embedded.

* Clone this repository 
* Run ```mvn clean package```
* Run one of these  two commands:
```
        java -jar -Dspring.profiles.active=test target/Verspaetung-nvaigation-1.0.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or log.log file to make sure no exceptions are thrown


## Data

The Verspaetung public transport information is comprised of 4 CSV files:

- `resources/data/line.csv` - the public transport line.
- `resources/data/stop.csv` - the stop along each line.
- `resources/data/time.csv` - the time vehicles arrive & depart at each stop. The timetimestamp is in the format of `HH:MM:SS`.
- `resources/data/delays.csv` - the delays for each line. This data is static and assumed to be valid for any time of day.

DataBase Intializers ```DelayInitializer ,LinesInitializer,StopsInitializer,TimesInitializer``` will load data from csv files to the database 
during the application startup 

### Returns the vehicles for a given time and coordinates [Paginated List]

```
http://localhost:8081/city-navigation/v1/lines/?x=1&y=1&date=10:00:00

{
    "content": [
        {
            "id": 31,
            "lineId": 0,
            "lineName": "M4"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageSize": 100,
        "pageNumber": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 100,
    "number": 0,
    "first": true,
    "numberOfElements": 1,
    "sort": {
        "sorted": false,
        "unsorted": true
    }
}
Response: HTTP 200
Content: paginated list 
```


### Returns boolean indicating if given line is currently delayed

http://localhost:8081/city-navigation/v1/lines/check-delay?lineName=200

```true```