## Problem

In the fictional city of Verspaetung, public transport is notoriously unreliable. To tackle the problem, the city council has decided to make the public transport timetable and delay information public, opening up opportunities for innovative use cases.

You are given the task of writing a web API to expose the Verspaetung public transport information.

As a side note, the city of Verspaetung has been built on a strict grid - all location information can be assumed to be from a cartesian coordinate system.

# Technologies and concepts

Small Microservice based on :
  * JDK 1.8
  * Spring Boot 2.0 
  * JPA
  * h2database
  * Tomcat 8 embedded (No Tomcat  installation is necessary)
  * Maven 3.x
  * Swagger 2 API docs

Concepts 
  * Writing a RESTful service using annotation supports both XML and JSON request / response;
  * Demonstrates MockMVC test framework with associated libraries
  * Build different layers to separate the application into parts bussines logic , the data base reproistories and endpoint
## Archtiture overview
![](https://raw.githubusercontent.com/gamal-ahmed/verspaetung-navigation/master/Archititure%20Diagram.png)
## How to Run 

This application is packaged as a war which has Tomcat 8 embedded.

* Clone this repository 
* Run ```mvn clean package```
* Run one of these  two commands:
```
        java -jar -Dspring.profiles.active=test target/verspaetung-nvaigation-1.0.0.jar
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or log.log file to make sure no exceptions are thrown

## Run Using Docker

I added Sptoify maven plugin for building and pushing Docker images. 

``` 
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <repository>${docker.image.prefix}/${project.artifactId}</repository>
                    <contextDirectory>${project.basedir}</contextDirectory>
                    <tag>${project.version}</tag>
                    <buildArgs>
                        <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
                    </buildArgs>
                </configuration>
                <executions>
                    <execution>
                        <id>default</id>
                        <phase>install</phase>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
  ```
  
Steps to Build and run the docker image: 

- Run inside the project directory `mvn install dockerfile:build`
- Start the docker container `docker run -p 8081:8081 -t mobimeo/verspaetung-nvaigation:1.0.0`
- Check that the container is up and runding using `docker ps` 
- Use the restfull apis
 
## Data

The Verspaetung public transport information is comprised of 4 CSV files:

- `resources/data/line.csv` - the public transport line.
- `resources/data/stop.csv` - the stop along each line.
- `resources/data/time.csv` - the time vehicles arrive & depart at each stop. The timetimestamp is in the format of `HH:MM:SS`.
- `resources/data/delays.csv` - the delays for each line. This data is static and assumed to be valid for any time of day.

DataBase Intializers ```DelayInitializer ,LinesInitializer,StopsInitializer,TimesInitializer``` will load data from csv files to the database 
during the application startup As shown below from logs :

```
2018-07-22 21:36:30.740  INFO 1 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2018-07-22 21:36:31.272  INFO 1 --- [           main] o.h.h.i.QueryTranslatorFactoryInitiator  : HHH000397: Using ASTQueryTranslatorFactory
2018-07-22 21:36:31.483  INFO 1 --- [           main] c.m.c.dao.intializers.LinesInitializer   : Importing 3 Lines into DataBase…
2018-07-22 21:36:31.555  INFO 1 --- [           main] c.m.c.dao.intializers.LinesInitializer   : Successfully imported 3 Lines.
2018-07-22 21:36:31.581  INFO 1 --- [           main] c.m.c.dao.intializers.TimesInitializer   : Importing 15 Times into DataBase…
2018-07-22 21:36:31.599  INFO 1 --- [           main] c.m.c.dao.intializers.TimesInitializer   : Successfully imported 15 Times.
2018-07-22 21:36:31.741  INFO 1 --- [           main] c.m.c.dao.intializers.DelayInitializer   : Importing 3 Delays into DataBase…
2018-07-22 21:36:31.747  INFO 1 --- [           main] c.m.c.dao.intializers.DelayInitializer   : Successfully imported 3 Delays.
2018-07-22 21:36:31.942  INFO 1 --- [           main] c.m.c.dao.intializers.StopsInitializer   : Importing 12 Stops into DataBase…
2018-07-22 21:36:31.953  INFO 1 --- [           main] c.m.c.dao.intializers.StopsInitializer   : Successfully imported 12 Stops.
```
### Secuirty 
default authentication and  authorization are disabled for HTTP methods ```POST ```, ```GET ``` to make it easir for testing .

Note : it is simple and easy to define a user name and password with some roles in the application.proparties file or in the ```configure() ``` function below .

```
  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
            private Pattern allowedMethods = Pattern.compile("^(GET|POST)$");
            private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/v[0-9]*/.*", null);

            @Override
            public boolean matches(HttpServletRequest request) {
                // No CSRF due to allowedMethod
                if (allowedMethods.matcher(request.getMethod()).matches())
                    return false;

                // No CSRF due to api call
                if (apiMatcher.matches(request))
                    return false;

                // CSRF for everything else that is not an API call or an allowedMethod
                return true;
            }
        });
        http.authorizeRequests().antMatchers("/").permitAll();
```

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
