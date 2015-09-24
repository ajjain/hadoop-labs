package in.ajjain.ci.dao;

import in.ajjain.ci.common.hbase.HBaseTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * The Interface EventSummaryDAO.
 */
public class EventSummaryDAO extends AbstractDAO{
	
	/** The Constant CF_PAGE_VIEW. */
	private static final String CF_PAGE_VIEW = "page_view";
	
	/**
	 * Instantiates a new event summary dao.
	 *
	 * @param template the template
	 */
	public EventSummaryDAO(HBaseTemplate template) {
		super(template);
		this.setTablename("event_summary_table");
	}

	/**
	 * Gets the page views for hour or day.
	 *
	 * @param key the key
	 * @return the page views for hour or day
	 */
	public Map<String, Integer> getPageViewsForKey(String key){
		Map<byte[], byte[]> result = this.getTemplate().find(this.getTablename(), key, CF_PAGE_VIEW);
		Map<String, Integer> txResult = new HashMap<>();
		for(Entry<byte[], byte[]> entry : result.entrySet()){
			txResult.put(Bytes.toString(entry.getKey()), Bytes.toInt(entry.getValue()));
		}
		return txResult;
	}
	
	/**
	 * Gets the page views from to hours.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the page views from to hours
	 */
	List<Map<String, Integer>> getPageViewsFromToHours(String from, String to){
		List<Map<byte[], byte[]>> result = this.getTemplate().find(this.getTablename(), CF_PAGE_VIEW, from, to);
		return null;
	}
}
