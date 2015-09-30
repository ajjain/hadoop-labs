package in.ajjain.ci.client;

import in.ajjain.ci.client.event.EventBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.mifmif.common.regex.Generex;

/**
 * The Class UserSessionGenerator.
 */
public class UserSessionGenerator {

	/** The page list. */
	private List<List<String>> pageList = new ArrayList<>();

	/** The catalog list. */
	private List<List<String>> catalogList = new ArrayList<>();
	/**
	 * Instantiates a new user session generator.
	 */
	private UserSessionGenerator(){
		InputStream input = getClass().getResourceAsStream("/page.csv");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				pageList.add(items);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		InputStream cinput = getClass().getResourceAsStream("/catalog.csv");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(cinput))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				catalogList.add(items);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** The singleton. */
	private static UserSessionGenerator singleton = new UserSessionGenerator();

	/**
	 * Gets the single instance of UserSessionGenerator.
	 *
	 * @return single instance of UserSessionGenerator
	 */
	public static UserSessionGenerator getInstance(){
		return singleton;
	}

	/** The Constant rand. */
	private static final Random rand = new Random();
	
	public List<EventBean> generate(String sessionid, String email, String fname,
			String lname, String latitude, String longitude, String geoname, String browser, Date fromTime, int sessionlength){
		List<EventBean> eventList = new ArrayList<>();
		String accessPattern = generex.random();
		String[] urlIndexStrArray = accessPattern.split("-");
		long timeL = fromTime.getTime();
		long increment = (sessionlength*1000)/urlIndexStrArray.length;
		for (String urlIndexStr : urlIndexStrArray) {
			int cIndex = (int)(catalogList.size() * rand.nextFloat());
			List<String> catalogEntry = catalogList.get(cIndex);
			List<String> pageEvent = pageList.get(Integer.parseInt(urlIndexStr)-1);
			timeL = timeL + increment;
			Date eventdt = new Date(timeL);
			String url = pageEvent.get(0);
			url = url.replace("{categoryid}", catalogEntry.get(0));
			url = url.replace("{productid}", catalogEntry.get(1));
			EventBean eb = new EventBean(sessionid, email, fname, lname, latitude, longitude, df.format(eventdt), geoname, browser, url, pageEvent.get(1));
			eventList.add(eb);
		}
		return eventList;
	}
	/** The Constant sessionRegEx. */
	private static final String sessionRegEx = 
			"1([-]2)+"
					+"("
					+ "(([-]3)|([-]3[-]4))|"
					+ "([-]6)"
					+ ")*"
					+"(([-]6[-]7)|([-]7)|([-]8)|([-]7[-]8))";

	/** The generex. */
	private static Generex generex = new Generex(sessionRegEx);

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Date dt = new Date();
		System.out.println(
				UserSessionGenerator.getInstance().generate("124", "abhishek", "abhishek", "abhishek", "1.1", "-1.1", "Indore", "chrome", dt, 200)
				);
		
		for (int i=0; i< 100; i++){
			System.out.println(generex.random(10));;
		}
	}

}
