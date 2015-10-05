var svg = d3.select('#geoview svg');

var projection = d3.geo.albersUsa()
					.scale(700)
					.translate([500, 200]);

var path = d3.geo.path()
			.projection(projection);

var states = svg.append("svg:g")
				.attr("id", "states");

var circles = svg.append("svg:g")
.attr("id", "circles");

d3.json("./ref/us_states.json", function(collection) {
	states.selectAll("path")
	.data(collection.features)
	.enter().append("svg:path")
	.attr("d", path);
});

circles.selectAll("circle")
.data([{"geoname":'New York City',"lat":40.7142691,"long":-74.0059738, "value":100}])
.enter().append("svg:circle")
.attr("r", function(d, i) { return Math.sqrt(d.value); })
.attr("transform", function(d) {return "translate(" + projection([d.long,d.lat]) + ")";});


//d3.csv("flights-airport.csv", function(flights) {
//var linksByOrigin = {},
//countByAirport = {},
//locationByAirport = {},
//positions = [];

//var arc = d3.geo.greatArc()
//.source(function(d) { return locationByAirport[d.source]; })
//.target(function(d) { return locationByAirport[d.target]; });

//flights.forEach(function(flight) {
//var origin = flight.origin,
//destination = flight.destination,
//links = linksByOrigin[origin] || (linksByOrigin[origin] = []);
//links.push({source: origin, target: destination});
//countByAirport[origin] = (countByAirport[origin] || 0) + 1;
//countByAirport[destination] = (countByAirport[destination] || 0) + 1;
//});

//d3.csv("airports.csv", function(airports) {

//// Only consider airports with at least one flight.
//airports = airports.filter(function(airport) {
//if (countByAirport[airport.iata]) {
//var location = [+airport.longitude, +airport.latitude];
//locationByAirport[airport.iata] = location;
//positions.push(projection(location));
//return true;
//}
//});

//// Compute the Voronoi diagram of airports' projected positions.
//var polygons = d3.geom.voronoi(positions);

//var g = cells.selectAll("g")
//.data(airports)
//.enter().append("svg:g");

//g.append("svg:path")
//.attr("class", "cell")
//.attr("d", function(d, i) { return "M" + polygons[i].join("L") + "Z"; })
//.on("mouseover", function(d, i) { d3.select("h2 span").text(d.name); });

//g.selectAll("path.arc")
//.data(function(d) { return linksByOrigin[d.iata] || []; })
//.enter().append("svg:path")
//.attr("class", "arc")
//.attr("d", function(d) { return path(arc(d)); });

//circles.selectAll("circle")
//.data(airports)
//.enter().append("svg:circle")
//.attr("cx", function(d, i) { return positions[i][0]; })
//.attr("cy", function(d, i) { return positions[i][1]; })
//.attr("r", function(d, i) { return Math.sqrt(countByAirport[d.iata]); })
//.sort(function(a, b) { return countByAirport[b.iata] - countByAirport[a.iata]; });
//});
//});