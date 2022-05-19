# FOREX MARKET REST API

Usage of JAX-RS with CDI 2, JSON-B, javax validation, Grizzly and Jetty.
Tests with partitions. Note the important workflow IT test.
And the AppUncaughtExceptionHandler.

## ARCHITECTURE

![Alt text](restapi.jpg "architecture")

## ENDPOINTS   
- For testing that the service is up and running: http://0.0.0.0:9998/trade/get?tradeid=1
- For posting data: http://0.0.0.0:9998/trade/add
 Use set Content-Type to application/json and a payload like this:
{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 
"900", "amountBuy": "450", "rate": "0.7471", "timePlaced" : "24-JAN-22 16:55:4", "originatingCountry" : "FR"}

- Frontend for rendering of data through websockets http://0.0.0.0:9998/trade/index.html

## MAPPINGS

The mapping of the URI path space is presented in the following table:

URI path		| Resource class      	| HTTP methods	| Notes
----------------| --------------------- | ------------	| --------------------------------------------------------
/trade/			| ForexExchangeResource |	GET			|  Checks that the application is running
/trade/get{id}	| ForexExchangeResource |	GET			|  Returns a saved exchange trade
/trade/list		| ForexExchangeResource |	GET			|  Returns list of all saved exchange trades
/trade/volume	| ForexExchangeResource |	GET			|  Returns market volume.
/trade/add		| ForexExchangeResource	|	POST		|

## RUNNING THE APPLICATION

Run Grizzly as follows:

>     mvn clean compile exec:java

This deploys the example using [Grizzly](https://javaee.github.io/grizzly/) container.

-   <http://0.0.0.0:9998/helloworld/Some%20Name>

Run Jetty as follows:

>     mvn clean compile jetty:run

This deploys the example using [Jetty](https://www.eclipse.org/jetty/) container.

-   <http://0.0.0.0:9998/helloworld/Some%20Name>

<!--
## INSTALLATION REQUIREMENTS
Tomcat 8 or equivalent Servlet container 3.0 ready .
Java JVM 7 .
-->
## DEMO WITH RANDOM DATA

[Forex live demo](https://atdance.github.io/fx_trade/)


## FRAMEWORKS USED
- Backend
- - Grizzly and Jetty containers
- - javax validation
- - Dependency Injection
<!--
- - Server Sent events in Jersey
- - Light broadcast with Tyrus
- - Apache Commons Lang
-->
- - Java SE 8
- - Slf4j
- - JSON-B
- - Junit

- Frontend
- - Rickshaw Javascript real time charting

## TECHNICAL NOTES
- DIFFICULTIES FOUND
- - It was extremely time using the matching of the dependencies of all versions.
I do not think this is good for Jersey. 
- JETTY SUPPORT
- - beans.xml is only needed by Jetty. This deployment uses a Custom Application subclass.
You can read Jetty document paragraphs 4.7.1.1 Custom Application subclass
	and Table 4.1. "Servlet 3 Pluggability Overview" for
more details for when Application subclass and web.xml could be avoided. 
- - Packaging war was needed for Jersey. 									 
- - Jetty could be run without including the without including the jetty-maven-plugin.
- - info about the famous "WELD-000144: CDI API version mismatch. CDI 1.0 
	API detected on classpath" and json-b and MOXy 
	are included in the comments of the pom.xml file.	
- GRIZZLY SUPPORT
- - see AppGrizzly class
	
- OTHER
The projects version decides all dependencies of jax.rx: <version>2.35</version>
		
## LAST RELEASE
- JSR 349 validator
- JSON-B
	
### TO DO
- Review REST resources names.
- HATEOAS
- Latest SSE. With Java EE 8 ? 
- JSR 354: Java API for working with Money and Currencies,
- SonarQube and PMD
- Dropwizard command and exception mapper ?
- Shade maven plugin ?
- Lombok?

<!---
## MESSAGE CONSUMPTION
- Consumed messages are received to a REST framework and written in RAM data structure.
- Rate limiting is implemented with a Servlet Filter that limits the rate of incoming requests at the Application level.
It may return 429 Too Many Requests response.

## MESSAGE PROCESSOR
-  Currency volume of messages from one currency pair market (EUR/GBP) is calculated and saved.
- Messages are sent through a realtime framework which pushes transformed data to a websocket  
frontend.

## MESSAGE FRONTEND
Two html and javascript pages renders graphing currency volume of messages from the (EUR/GBP) currency 
pair market.
- index.html uses websockets
- fixed.html uses REST

## SECURITY
- The API is rate limited.
- Input data is validated.
- Logged input validation failures.
- Strong typing: incoming data is strongly typed as quickly as possible. 
-->
## LIMITATIONS
Only two currencies are allowed : EUR, GBP.
Only these origin countries are allowed: FRA, IRL, UK, USA .