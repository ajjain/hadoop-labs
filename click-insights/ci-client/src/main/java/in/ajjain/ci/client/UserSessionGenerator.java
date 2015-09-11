package in.ajjain.ci.client;

import in.ajjain.ci.client.event.EventBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mifmif.common.regex.Generex;

/**
 * The Class UserSessionGenerator.
 */
public class UserSessionGenerator {

	/** The page list. */
	private List<List<String>> pageList = new ArrayList<>();

	/**
	 * Instantiates a new user session generator.
	 */
	private UserSessionGenerator(){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("page.csv").getFile());
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				pageList.add(items);
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

	public List<EventBean> generate(String sessionid, String email, String fname,
			String lname, String latitude, String longitude, String geoname, String browser, Date fromTime, int sessionlength){
		List<EventBean> eventList = new ArrayList<>();
		String accessPattern = generex.random();
		String[] urlIndexStrArray = accessPattern.split("-");
		long timeL = fromTime.getTime();
		long increment = (sessionlength*1000)/urlIndexStrArray.length;
		for (String urlIndexStr : urlIndexStrArray) {
			List<String> pageEvent = pageList.get(Integer.parseInt(urlIndexStr)-1);
			timeL = timeL + increment;
			Date eventdt = new Date(timeL);
			EventBean eb = new EventBean(sessionid, email, fname, lname, latitude, longitude, df.format(eventdt), geoname, browser, pageEvent.get(0), pageEvent.get(1));
			eventList.add(eb);
		}
		return eventList;
	}
	/** The Constant sessionRegEx. */
	private static final String sessionRegEx = 
			"1([-]2)+"
					+"("
					+ "(([-]3)|([-]3[-]4)|([-]3[-]4[-]5))|"
					+ "(([-]6)|([-]6[-]7)|([-]6[-]7[-]8))|"
					+ "(([-]9)|([-]9[-]10)|([-]9[-]10[-]11))|"
					+ "(([-]12)|([-]12[-]13)|([-]12[-]13[-]14))|"
					+ "(([-]15)|([-]15[-]16)|([-]15[-]16[-]17))|"
					+ "(([-]18)|([-]18[-]19)|([-]18[-]19[-]20))|"
					+ "(([-]21)|([-]21[-]22)|([-]21[-]22[-]23))|"
					+ "([-]24)"
					+ ")*"
					+"(([-]25[-]26)|([-]25)|([-]26))";

	/** The generex. */
	private static Generex generex = new Generex(sessionRegEx);

	private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:S");


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
	}

}
