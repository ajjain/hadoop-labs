package in.ajjain.ci.dao;

import in.ajjain.ci.common.hbase.HBaseTableCFNames;
import in.ajjain.ci.common.hbase.HBaseTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * The Interface UserPageViewsDAO.
 */
public class UserPageViewsDAO extends AbstractDAO{

	/**
	 * Instantiates a new page view  dao.
	 *
	 * @param template the template
	 */
	public UserPageViewsDAO(HBaseTemplate template) {
		super(template);
		this.setTablename(HBaseTableCFNames.TAB_USERS_BY_PAGE);
	}

	/**
	 * Gets the user stats for hour or day.
	 *
	 * @param key the key
	 * @return the page views for hour or day
	 */
	public Map<String, String> getUserStatsForKey(String url, String datetime){
		String key = datetime+":"+url;
		Map<byte[], byte[]> result = this.getTemplate().find(this.getTablename(), key, HBaseTableCFNames.CF_USER_STATS);
		Map<String, String> txResult = new HashMap<>();
		for(Entry<byte[], byte[]> entry : result.entrySet()){
			txResult.put(Bytes.toString(entry.getKey()), Bytes.toString(entry.getValue()) );
		}
		return txResult;
	}

	/**
	 * Gets the user stats from to hours.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the page views from to hours
	 */
	public List<Map<String, String>> getUserStatsFromToHours(String from, String to){
		List<Map<String, String>> txResultList = new LinkedList<>();
		List<Map<byte[], byte[]>> resultList = this.getTemplate().find(this.getTablename(), HBaseTableCFNames.CF_USER_STATS, from, to);
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
