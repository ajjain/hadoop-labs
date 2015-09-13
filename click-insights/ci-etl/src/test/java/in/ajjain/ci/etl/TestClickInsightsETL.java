package in.ajjain.ci.etl;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import in.ajjain.ci.etl.ClickInsightsETL.CIMapper;

/**
 * The Class TestClickInsightsETL.
 */
public class TestClickInsightsETL {
	
	/** The map driver. */
	MapDriver<Object, Text, Text, Text> mapDriver;
	
	
	/**
	 * Sets the up.
	 */
	@Before
	  public void setUp() {
	    CIMapper mapper = new CIMapper();
	    mapDriver = MapDriver.newMapDriver(mapper);
	  }
	
	@Test
	  public void testMapper() throws Exception {
	    mapDriver.withInput(new LongWritable(), new Text(
	        "{\"timestamp\":\"10/11/2015 04:40:10:504\",\"url\":\"yass.com/view/pay\",\"method\":\"POST\",\"browser\":\"safari\",\"email\":\"roselle.estell@hotmail.com\",\"fname\":\"Roselle\",\"lname\":\"Estell\",\"geoname\":\"Newark\",\"latitude\":\"40.735657\",\"longitude\":\"40.735657\",\"sessionid\":\"686281781\"}"));
	    mapDriver.withOutput(new Text("10/11/2015 04"), new Text("10/11/2015 04:40:10:504,yass.com/view/pay,POST,safari,roselle.estell@hotmail.com,Roselle,Estell,Newark,40.735657,40.735657,686281781"));
	    mapDriver.runTest();
	  }
}
