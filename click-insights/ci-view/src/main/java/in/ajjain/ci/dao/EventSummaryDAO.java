package in.ajjain.ci.dao;

import in.ajjain.ci.common.hbase.HBaseTableCFNames;
import in.ajjain.ci.common.hbase.HBaseTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * The Interface EventSummaryDAO.
 */
public class EventSummaryDAO extends AbstractDAO{

	

	/**
	 * Instantiates a new event summary dao.
	 *
	 * @param template the template
	 */
	public EventSummaryDAO(HBaseTemplate template) {
		super(template);
		this.setTablename(HBaseTableCFNames.TAB_EVENT_SUMMARY);
	}

	/**
	 * Gets the page views for hour or day.
	 *
	 * @param key the key
	 * @return the page views for hour or day
	 */
	public Map<String, Long> getPageViewsForKey(String key){
		Map<byte[], byte[]> result = this.getTemplate().find(this.getTablename(), key, HBaseTableCFNames.CF_PAGE_VIEW);
		Map<String, Long> txResult = new HashMap<>();
		for(Entry<byte[], byte[]> entry : result.entrySet()){
			txResult.put(Bytes.toString(entry.getKey()), new Long(Bytes.toString(entry.getValue())) );
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
	public List<Map<String, String>> getPageViewsFromToHours(String from, String to){
		List<Map<String, String>> txResultList = new ArrayList<>();
		List<Map<byte[], byte[]>> resultList = this.getTemplate().find(this.getTablename(), HBaseTableCFNames.CF_PAGE_VIEW, from, to);
		for(Map<byte[], byte[]> result: resultList){
			Map<String, String> txResult = new HashMap<>();
			for(Entry<byte[], byte[]> entry : result.entrySet()){
				txResult.put(Bytes.toString(entry.getKey()), Bytes.toString(entry.getValue()) );
			}
			txResultList.add(txResult);
		}
		return txResultList;
	}
}
