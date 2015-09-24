package in.ajjain.ci.common.hbase;

import in.ajjain.ci.dao.exception.CIDAOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * The Class HBaseTemplate.
 */
public class HBaseTemplate {

	/** The manager. */
	private HBaseManager manager;

	/**
	 * Instantiates a new h base template.
	 *
	 * @param manager the manager
	 */
	public HBaseTemplate(HBaseManager manager) {
		super();
		this.manager = manager;
	}

	/**
	 * Find.
	 *
	 * @param tableName the table name
	 * @param rowkey the rowkey
	 * @param columnFamily the column family
	 * @return the map
	 */
	public Map<byte[], byte[]> find(String tableName, String rowkey, String columnFamily){
		Connection conn = null;
		Table table = null;
		try {
			conn = manager.getConnection();
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowkey));
			get.addFamily(Bytes.toBytes(columnFamily));
			Result result = table.get(get);
			NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(columnFamily));
			return familyMap;
		} catch (IOException e) {
			throw new CIDAOException(e);
		}
		finally{
			if(table != null)
				try {
					table.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Find.
	 *
	 * @param tableName the table name
	 * @param rowkey the rowkey
	 * @return the map
	 */
	public Map<byte[], NavigableMap<byte[], byte[]>> find(String tableName, String rowkey){
		Connection conn = null;
		Table table = null;
		try {
			conn = manager.getConnection();
			table = conn.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowkey));
			Result result = table.get(get);
			Map<byte[],NavigableMap<byte[],byte[]>> familyMap = result.getNoVersionMap();
			return familyMap;
		} catch (IOException e) {
			throw new CIDAOException(e);
		}
		finally{
			if(table != null)
				try {
					table.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Find.
	 *
	 * @param tableName the table name
	 * @param columnFamily the column family
	 * @param fromrowkey the fromrowkey
	 * @param torowkey the torowkey
	 * @return the list
	 */
	public List<Map<byte[], byte[]>> find(String tableName, String columnFamily, String fromrowkey, String torowkey){
		Connection conn = null;
		Table table = null;
		List<Map<byte[], byte[]>> resultList = new ArrayList<>();
		try {
			conn = manager.getConnection();
			table = conn.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan(Bytes.toBytes(fromrowkey), Bytes.toBytes(torowkey));
			scan.addFamily(Bytes.toBytes(columnFamily));
			ResultScanner scanner = table.getScanner(scan);
			for (Result result = scanner.next(); result != null; result = scanner.next()){
				NavigableMap<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(columnFamily));
				resultList.add(familyMap);
			}
			return resultList;
		} catch (IOException e) {
			throw new CIDAOException(e);
		}
		finally{
			if(table != null)
				try {
					table.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
