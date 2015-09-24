package in.ajjain.ci.view.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

/**
 * The Class ClickInsightsResource.
 */
@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
public class ClickInsightsResource {
	
	@GET
    @Timed
    @Path("most_visited_pages")
    public String most_visited_pages(
    		@QueryParam("date") String date, 
    		@QueryParam("hour") String hour,
    		@QueryParam("to_hour") String fromHour,
    		@QueryParam("from_hour") String toHour) {
		
        return "hello";
    }
}
