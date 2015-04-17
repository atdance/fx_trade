# fx_trade
FX Market Trade Processor

INSTALLATION REQUIREMENTS
Tomcat 8 or equivalent Servlet container.
Java JVM 7 .

LIMITATIONS
Only two currencies are allowed : EUR, GBP.
	
ENDPOINTS   
http://<host>:8080/restapi/rest/trade/gettest?tradeid=1
http://<host>:/restapi/fixed.html

MESSAGE CONSUMPTION
- Consumed messages are received to a REST framework and written in RAM data structure.
- Rate is implemented with a Servlet Filter that limits the rate of incoming requests at the Application level.
It may return 429 Too Many Requests response.

MESSAGE PROCESSOR
-  Currency volume of messages from one currency pair market (EUR/GBP) is calculated and saved.
- Messages are sent through a realtime framework which pushes transformed data to a Socket.io 
frontend.

MESSAGE FRONTEND
An html and javascript page renders graphing currency volume of messages from the (EUR/GBP) currency 
pair market.
