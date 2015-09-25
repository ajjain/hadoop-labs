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
import in.ajjain.ci.dao.EventSummaryDAO;

/**
 * The Class ClickInsightsResource.
 */
@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
public class ClickInsightsResource {
	
	private EventSummaryDAO eventSummaryDao;
	
	public ClickInsightsResource(){
		HBaseManager manager = new HBaseManager();
		HBaseTemplate template = new HBaseTemplate(manager);
		this.eventSummaryDao = new EventSummaryDAO(template);
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
    public List<Map<String, Long>> most_visited_pages_distribution(
    		@QueryParam("from_hour") String fromHour,
    		@QueryParam("to_hour") String toHour) {
		if (toHour != null && fromHour != null)
			return eventSummaryDao.getPageViewsFromToHours(fromHour, toHour);
		else
			throw new IllegalArgumentException("Illegal arguments to most_visited_pages_distribution REST API");
    }
}
