package in.ajjain.ci.view.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import in.ajjain.ci.common.hbase.HBaseManager;
import in.ajjain.ci.common.hbase.HBaseTemplate;
import in.ajjain.ci.common.utils.CIUtils;
import in.ajjain.ci.dao.EventSummaryDAO;
import in.ajjain.ci.dao.UserPageViewsDAO;

/**
 * The Class ClickInsightsResource.
 */
@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
public class ClickInsightsResource {
	
	private EventSummaryDAO eventSummaryDao;
	private UserPageViewsDAO userPageViewsDao;
	
	
	public ClickInsightsResource(){
		HBaseManager manager = new HBaseManager();
		HBaseTemplate template = new HBaseTemplate(manager);
		this.eventSummaryDao = new EventSummaryDAO(template);
		this.userPageViewsDao = new UserPageViewsDAO(template);
	}
	
	@GET
    @Timed
    @Path("most_visited_pages")
    public Map<String, Long> most_visited_pages(
    		@QueryParam("date") String date, 
    		@QueryParam("hour") String hour,
    		@QueryParam("to_hour") String fromHour,
    		@QueryParam("from_hour") String toHour) {
		if(hour != null)
			return eventSummaryDao.getPageViewsForKey(hour);
		else if (date != null)
			return eventSummaryDao.getPageViewsForKey(date);
		else
			throw new IllegalArgumentException("Illegal arguments to most_visited_pages REST API");
    }
	
	@GET
    @Timed
    @Path("most_visited_pages_distribution")
    public List<Map<String, String>> most_visited_pages_distribution(
    		@QueryParam("from_hour") String fromHour,
    		@QueryParam("to_hour") String toHour) {
		if (toHour != null && fromHour != null)
			return eventSummaryDao.getPageViewsFromToHours(fromHour, toHour);
		else
			throw new IllegalArgumentException("Illegal arguments to most_visited_pages_distribution REST API");
    }
	
	@GET
    @Timed
    @Path("user_page_views_distribution")
    public List<Map<String, String>> user_page_views_distribution(
    		@QueryParam("from_hour") String fromHour,
    		@QueryParam("to_hour") String toHour) {
		if (toHour != null && fromHour != null)
			return userPageViewsDao.getUserStatsFromToHours(fromHour, toHour);
		else
			throw new IllegalArgumentException("Illegal arguments to user_page_views_distribution REST API");
    }
	
	@GET
    @Timed
    @Path("user_page_views")
    public Map<String, String> user_page_views(
    		@QueryParam("hour") String hour,
    		@QueryParam("url") String url) {
		if(hour != null && url != null){
			
			return CIUtils.sortByValue(userPageViewsDao.getUserStatsForKey(url, hour));
		}
		else
			throw new IllegalArgumentException("Illegal arguments to user_page_views REST API");
    }
	
	@GET
    @Timed
    @Path("geo_logins")
    public Map<String, String> geo_logins(
    		@QueryParam("hour") String hour) {
		if(hour != null){
			
			return CIUtils.sortByValue(eventSummaryDao.getGeoLoginsForKey(hour));
		}
		else
			throw new IllegalArgumentException("Illegal arguments to user_page_views REST API");
    }
}
