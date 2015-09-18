REGISTER ci-analytics-1.0.jar;

--------------------------------------------------------------------
-- perform the analysis of session and generate the summary results
--------------------------------------------------------------------

DEFINE CI_COUNT in.ajjain.ci.analytics.CICounter;

-- load all the events
events = load '/user/ajjain/events/*' using PigStorage(',') AS (event_time:chararray, url:chararray, method:chararray, browser:chararray, email:chararray, fname:chararray, lname:chararray, geoname:chararray, latitude:FLOAT, longitude:FLOAT, sessionid:chararray);

group_events_by_sessionid = GROUP events BY (sessionid, email, fname, lname, geoname, latitude, longitude);

session_dimensions = FOREACH group_events_by_sessionid {
	ordered_events = ORDER events BY event_time;
	items_in_cart = FILTER ordered_events BY method == 'POST' AND url matches '.*rest.*';
	items_viewed = FILTER ordered_events BY method == 'GET' AND url matches '.*rest.*';
	page_viewed = FILTER ordered_events BY method == 'GET' AND url matches '.*view.*';
	pay_events = FILTER ordered_events BY method == 'POST' AND url matches '.*pay.*';
	GENERATE 
		group.sessionid, group.email, group.fname, group.lname, group.geoname, group.latitude, group.longitude, 
		COUNT(items_in_cart) as cnt_items_in_cart, 
		COUNT(items_viewed) as cnt_items_viewed, 
		COUNT(page_viewed) as cnt_page_viewed,
		CI_COUNT(ordered_events, "INTERVAL") as session_interval,
		CI_COUNT(page_viewed, "REFRESH") as cnt_refresh,
		(COUNT(pay_events) > 0 ? 1 : 0) as paid; 
};

session_dimensions_limit = LIMIT session_dimensions 10;

DUMP session_dimensions_limit;
