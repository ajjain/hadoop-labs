--------------------------------------------------------------------
-- perform the analysis of session and generate the summary results
--------------------------------------------------------------------

-- load all the events
events = load '$INPUT' using PigStorage(',') AS (event_time:TIMESTAMP, url:chararray, method:chararray, browser:chararray, email:chararray, fname:chararray, lname:chararray, geoname:chararray, latitude:FLOAT, longitude:FLOAT, sessionid:chararray);

group_events_by_sessionid = GROUP events BY sessionid, email, fname, lname, geoname, latitude, longitude;

session_dimensions = FOR EACH group_events_by_sessionid {
	ordered_events = ORDER events BY event_time;
	items_added_to_cart = FILTER ordered_events BY method == 'POST' AND url matches '.*rest.*'';
	items_removed_from_cart = FILTER ordered_events BY method == 'DELETE' AND url matches '.*rest.*';
	total_items_in_cart = COUNT(items_added_to_cart) - COUNT(items_removed_from_cart);
	items_viewed = FILTER ordered_events BY method == 'GET' AND url matches '.*rest.*';
	page_viewed = FILTER ordered_events BY method == 'GET' AND url matches '.*view.*';
	 = 
	GENERATE 
		sessionid, email, fname, lname, geoname, latitude, longitude, 
		total_items_in_cart, items_viewed, page_viewed, 
		
};








-- sessionid, session_interval, start_time, email, fname, lname, geoname, latitude, longitude, refresh, back, total_purchase_count


