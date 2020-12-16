# OC : Parcours Java / Project 8 ( TourGuide )
##### Author : **_MorganCpn_**

## Configurations
### External API (Microservices)
* /ExternalAPI/GPSUtil
    - Java 8
	- Gradle 6.6.1
	- Spring Boot 2.1.6

* /ExternalAPI/Reward Central
    - Java 8
    - Gradle 6.6.1
    - Spring Boot 2.1.6
    
* /ExternalAPI/Trip Pricer
    - Java 8
	- Gradle 6.6.1
	- Spring Boot 2.1.6

### Main App	
* TourGuide
    - Java 8
	- Gradle 6.6.1
	- Spring Boot 2.1.6

	
## Install Project

1. Start your Docker app
2. Install docker environment : cd "External API"; docker-compose up -d`, 
Three docker's containers are created :
* GPSUtil : localhost:8081
* TripPricer : localhost:8082
* RewardCentral : localhost:8083
3. Import in POSTMAN the import file : /TourGuide.postman_collection.json
4. Run app : `cd TourGuide; ./gradlew bootrun`

## Running App

`cd TourGuide; ./gradlew bootrun

## Testing

`cd TourGuide; ./gradlew clean; ./gradlew test`

## Logs File

`/TourGuide/logs/TourGuide.log`

## Project URL and endpoints
### GPSUtil (Microservice)
    * INFO       : http://localhost:8081/actuator/info
    * HEALTH     : http://localhost:8081/actuator/health
    * BEANS      : http://localhost:8081/actuator/beans
    * METRICS    : http://localhost:8081/actuator/metrics
    * ENV        : http://localhost:8081/actuator/env
    * HTTP TRACE : http://localhost:8081/actuator/httptrace
    
    * GET : http://localhost:8081/getAttractions
		Return list of all attractions
		
	* GET : http://localhost:8081/getUserLocation?userId=
	    URL Params : userId
    	Return user location 
		
		
### TripPricer (Microservice)
    * INFO       : http://localhost:8082/actuator/info
    * HEALTH     : http://localhost:8082/actuator/health
    * BEANS      : http://localhost:8082/actuator/beans
    * METRICS    : http://localhost:8082/actuator/metrics
    * ENV        : http://localhost:8082/actuator/env
    * HTTP TRACE : http://localhost:8082/actuator/httptrace
    
    * POST : http://localhost:8082/getPrice
        Body params (form-data): 
            apiKey : String
            attractionId : String
            adults : Integer
            children : Integer
            nightsStay : Integer
            rewardsPoints : Integer
        
### RewardCentral (Microservice)
    * INFO       : http://localhost:8083/actuator/info
    * HEALTH     : http://localhost:8083/actuator/health
    * BEANS      : http://localhost:8083/actuator/beans
    * METRICS    : http://localhost:8083/actuator/metrics
    * ENV        : http://localhost:8083/actuator/env
    * HTTP TRACE : http://localhost:8083/actuator/httptrace
    
    * GET : http://localhost:8083/getAttractionRewardPoints?userId=&attractionId=
        URL Params : userId, attractionId
    	Return user reward corresponding to attraction
    
### TourGuide (Main App)
    * INFO       : http://localhost:8080/actuator/info
    * HEALTH     : http://localhost:8080/actuator/health
    * BEANS      : http://localhost:8080/actuator/beans
    * METRICS    : http://localhost:8080/actuator/metrics
    * ENV        : http://localhost:8080/actuator/env
    * HTTP TRACE : http://localhost:8080/actuator/httptrace
	
		
	* GET : http://localhost:8080/getAllCurrentLocations
		Return all users locations
		
	* GET : http://localhost:8080/getTripDeals?userName=
	    URL Params : userName
		Return list of 5 trip deals
		
	* GET : http://localhost:8080/getLocation?userName=
	    URL Params : userName
		Return user location

	* GET : http://localhost:8080/getNearbyAttractions?userName=
	    URL Params : userName
		Return 5 nearest attraction of the user	
	
	* GET : http://localhost:8080/getRewards?userName=
	    URL Params : userName
		Return user reward balance

## Class Diagram
![alt text](https://github.com/MrgnCpn/OC-Java-Project-8-TourGuide/blob/master/ArchitectureTourGuide.png)