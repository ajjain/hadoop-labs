
function TopUserItem(label, value){
	this.label = label;
	this.value = +value;
}

function drawTopUsers(data){
	nv.addGraph(function() {
		var chart = nv.models.discreteBarChart()
		.x(function(d) { return d.label })    
		.y(function(d) { return d.value });

		//chart.staggerLabels(true);
		chart.showValues(true);
		chart.xAxis.rotateLabels(-45);
		
		
		d3.select('#topusers svg')
		.datum(data)
		//.staggerLabels(true)    
		//.tooltips(false)        
		//.showValues(true)       
		//.transitionDuration(350)
		.transition().duration(500)
		.call(chart);

		nv.utils.windowResize(chart.update);

		return chart;
	});
}

function fetchdata() {
	d3.json(
			"/api/rest/user_page_views?hour=2015-10-30 01&url=yass.com/view/home", 
			function(error, json){
				if (error) return console.warn(error);

				var result = [];

				for (var propName in json) {
					var item = new TopUserItem(propName, json[propName]);
					result.push(item);
				}
				result = result.sort(function(a,b){return b.value - a.value;});
				result = result.slice(0,24);
				var resultDs = new Array();
				var finalObj = new Object();
				finalObj.key = "User Page Views";
				finalObj.values = result;
				resultDs.push(finalObj);

				drawTopUsers(resultDs);
			}
	);
}
fetchdata();