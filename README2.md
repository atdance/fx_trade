#FX Market Trade Processor

## INSTALLATION REQUIREMENTS
Tomcat 8 or equivalent Servlet container 3.0 ready .
Java JVM 7 .

## LIMITATIONS
Only two currencies are allowed : EUR, GBP.
Only these origin countries are allowed: FRA, IRL, UK, USA .

## ARCHITECTURE

![Alt text](restapi.jpg "architecture")

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


<link type="text/css" rel="stylesheet" href="http://code.shutterstock.com/rickshaw/src/css/graph.css">
<link type="text/css" rel="stylesheet" href="http://code.shutterstock.com/rickshaw//src/css/detail.css">
<link type="text/css" rel="stylesheet" href="http://code.shutterstock.com/rickshaw//src/css/legend.css">
<link type="text/css" rel="stylesheet" href="http://code.shutterstock.com/rickshaw/examples/css/extensions.css">

<script src="http://code.shutterstock.com/rickshaw/vendor/d3.v3.js"></script>
<script src="http://code.shutterstock.com/rickshaw/rickshaw.js"></script>

	
<div id="chart_container">
	<div id="chart"></div>
	<div id="legend_container">
		<div id="smoother" title="Smoothing"></div>
		<div id="legend"></div>
	</div>
	<div id="slider"></div>
</div>

<script>
function print_r(printthis, returnoutput) {
    var output = '';
    var i = 0;

    if($.isArray(printthis) || typeof(printthis) == 'object') {
        for(i in printthis) {
            output += i + ' : ' + print_r(printthis[i], true) + '\n';
            //output += 'size ' + printthis[i] + '\n';
        }
    }else {
        output += printthis;
    }
    //output += 'size ' + i + '\n';
    if(returnoutput && returnoutput == true) {
        return output;
    }else {
        alert(output);
    }
}		
	
var remote = "http://2.65.155.63:8080/restapi/rest/trade/volume";
var test =  "http://web.ict.kth.se/~michelec/fixed.php";
var local = "http://localhost:8080/restapi/rest/trade/volume";
var myURL = local;	
var tv = 500;

var globaldata;

// instantiate our graph!
var graph = new Rickshaw.Graph( {
	element: document.getElementById("chart"),
	width: 900,
	height: 500,
	renderer: 'line',
	series: new Rickshaw.Series.FixedDuration([{ name: 'eur' },{ name: 'gbp' }], undefined, {
		timeInterval: tv,
		maxDataPoints: 100,
		timeBase: new Date().getTime() / 1000
	}) 
} );

graph.render();

var hoverDetail = new Rickshaw.Graph.HoverDetail( {
	graph: graph
} );

var legend = new Rickshaw.Graph.Legend( {
	graph: graph,
	element: document.getElementById('legend')

} );

var shelving = new Rickshaw.Graph.Behavior.Series.Toggle( {
	graph: graph,
	legend: legend
} );

var axes = new Rickshaw.Graph.Axis.Time( {
	graph: graph
} );
axes.render();

// add some data every so often

var i = 0;

var iv = setInterval( function() {
	//graph.series.addData(globaldata);
	graph.series.addData(getRandomData());//globaldata);
	graph.render();
}, tv );


var	getRemoteData = function() {
	   $.ajax({
     	dataType: "json",
      url: myURL,
      success: function(data) {
         //print_r(data );
         globaldata = data;
      }
    });	
};

var getRandomData =  function(){
	var data = { eur: Math.floor(Math.random() * 40) + 120 };
	var randInt = Math.floor(Math.random()*100);
	data.gbp = (Math.sin(i++ / 40) + 4) * (randInt + 400);
	data.eur = randInt + 300;
	//globaldata = data;
	return data;		
};
</script>