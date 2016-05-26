#Forex Market Trade Processor

## INSTALLATION REQUIREMENTS
Tomcat 8 or equivalent Servlet container 3.0 ready .
Java JVM 7 .

## ARCHITECTURE

![Alt text](restapi.jpg "architecture")


## Demo with random data

[Forex live demo](http://www.bondsbiz.com/trade/random.html)


## Frameworks used
-Backend
- - JSR 356: Java API for WebSocket, Tyrus-spi
- - Java EE: JAX-RS, Servlet API, Servlet filters
- - Server Sent events in Jersey
- - Light broadcast with Tyrus
- - Java SE 8
- - Apache Commons Lang
- - Slf4j
- - Jackson for XML
- - Maven & Git
- - Junit

-Frontend
- - Rickshaw Javascript real time charting
	
### TO DO
- Is it possible to substitute SSE with Java EE 7 ?
- JSR 354: Java API for working with Money and Currencies,
- JSR 349 with JBoss Hybernate validator
- SonarQube and PMD
- Dropwizard command and exception mapper
- Shade maven plugin
- Jetty embedded server
- Lombok


## ENDPOINTS   
- For testing that the service is up and running: http://<host>:8080/restapi/rest/trade/gettest?tradeid=1
- For posting data: http://<host>:8080/restapi/rest/trade/add
 Use set Content-Type to application/json and a payload like this:
{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 
"900", "amountBuy": "450", "rate": "0.7471", "timePlaced" : "24-JAN-15 10:27:4", "originatingCountry" : "FR"}
- Frontend for rendering of data through websockets http://<host>:/restapi/index.html

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

## LIMITATIONS
Only two currencies are allowed : EUR, GBP.
Only these origin countries are allowed: FRA, IRL, UK, USA .