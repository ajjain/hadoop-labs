package in.ajjain.ci.common.hbase;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

/**
 * The Class HBaseConfigManager.
 */
public class HBaseManager {

	/** The config. */
	private Configuration config;

	/**
	 * Instantiates a new h base config manager.
	 */
	public HBaseManager(){
		try {
			config  = HBaseConfiguration.create();
			InputStream hbasesite = getClass().getResourceAsStream("/hbase-site.xml");
			InputStream coresite = getClass().getResourceAsStream("/core-site.xml");
			config.addResource(hbasesite);
			config.addResource(coresite);
		}
		catch(Exception e){
			throw new RuntimeException("unable to instantiate HBASE configuration manager", e);
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Connection getConnection() throws IOException {
		return ConnectionFactory.createConnection(getConfiguration());
	}
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public Configuration getConfiguration(){
		return config;
	}
}
