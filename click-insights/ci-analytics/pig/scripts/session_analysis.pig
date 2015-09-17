--------------------------------------------------------------------
-- perform the analysis of session and generate the summary results
--------------------------------------------------------------------

-- load all the events
events = load '/user/ajjain/events/*' using PigStorage(',') AS (event_time:chararray, url:chararray, method:chararray, browser:chararray, email:chararray, fname:chararray, lname:chararray, geoname:chararray, latitude:FLOAT, longitude:FLOAT, sessionid:chararray);

group_events_by_sessionid = GROUP events BY (sessionid, email, fname, lname, geoname, latitude, longitude);

session_dimensions = FOREACH group_events_by_sessionid {
	ordered_events = ORDER events BY event_time;
	items_added_to_cart = FILTER ordered_events BY method == 'POST' AND url matches '.*rest.*';
	items_removed_from_cart = FILTER ordered_events BY method == 'DELETE' AND url matches '.*rest.*';
	total_items_in_cart = COUNT(items_added_to_cart) - COUNT(items_removed_from_cart);
	items_viewed = FILTER ordered_events BY method == 'GET' AND url matches '.*rest.*';
	page_viewed = FILTER ordered_events BY method == 'GET' AND url matches '.*view.*';
	
	GENERATE 
		group.sessionid, group.email, group.fname, group.lname, group.geoname, group.latitude, group.longitude, 
		total_items_in_cart, 
		COUNT(items_viewed), 
		COUNT(page_viewed); 
};

session_dimensions_limit = LIMIT session_dimensions 10;

DUMP session_dimensions_limit;
