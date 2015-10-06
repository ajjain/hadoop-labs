package in.ajjain.ci.client;

import in.ajjain.ci.client.event.EventBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * The Class Client.
 */
public class Client {

	/** The location list. */
	private static final List<List<String>> locationList = new ArrayList<>();

	/** The user list. */
	private static final List<List<String>> userList = new ArrayList<>();

	/** The singleton. */
	private static Client singleton = new Client();
	
	public static Client getInstance(){
		return singleton;
	}
	/**
	 * Instantiates a new client.
	 */
	private Client(){
		InputStream uinput = getClass().getResourceAsStream("/user.csv");
		InputStream linput = getClass().getResourceAsStream("/location.csv");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(uinput))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				userList.add(items);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		try (BufferedReader br = new BufferedReader(new InputStreamReader(linput))){
			String line;
			while ((line = br.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				locationList.add(items);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the random user session.
	 *
	 * @return the random user session
	 */
	public List<List<String>> getRandomUserSession(){
		return null;
	}

	/** The Constant rand. */
	private static final Random rand = new Random();

	/** The Constant srand. */
	private static final Random srand = new Random(543291341223L);

	/** The Constant browsers. */
	private static final String[] browsers = {"chrome", "firefox", "safari", "opera", "IE", "ME"};

	/**
	 * Generate eventsin file.
	 *
	 * @param location the location
	 * @param date the date
	 * @param eventCounts the event counts
	 */
	public void generateEventsinFile(String location, Date date, int eventCounts) {
		int totalCnt = 0;
		try (PrintWriter writer = new PrintWriter(location)){
			while(totalCnt < eventCounts){
				
				int uIndex = (int)(userList.size() * rand.nextFloat());
				int lIndex = (int)(locationList.size() * rand.nextFloat());
				List<String> uAttr = userList.get(uIndex);
				List<String> lAttr = locationList.get(lIndex);
				
				List<EventBean> resultEvents = UserSessionGenerator.getInstance().generate(
						""+Math.abs(srand.nextInt()), 
						uAttr.get(2), uAttr.get(0), uAttr.get(1), 
						lAttr.get(2), lAttr.get(3), lAttr.get(1), 
						browsers[(int)(rand.nextFloat() * browsers.length)], 
						new Date(date.getTime()+(int)(rand.nextFloat() * 1000*3600*24)), 
						(int)(rand.nextFloat() * 15*60)
						);
				
				for(EventBean bean: resultEvents){
					writer.println(bean.toString());
				}
				
				totalCnt = totalCnt + resultEvents.size();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * The main method.
	 * args[0]: date in MM-DD-YYYY format
	 * args[1]: output folder name
	 * args[2]: number of events to be generated
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		String dateStr = args[0];
		String outputLocation = args[1];
		String eventCountsStr = args[2];
		String[] dateArr = dateStr.split("-");
		Calendar c = Calendar.getInstance();
		c.set(
				Integer.parseInt(dateArr[2]), 
				Integer.parseInt(dateArr[0])-1, 
				Integer.parseInt(dateArr[1]), 
				00, 0, 0
		);
		Date dt = c.getTime();
		int eventCounts = Integer.parseInt(eventCountsStr);
		Client.getInstance().generateEventsinFile(
				outputLocation+System.getProperty("file.separator")+dateStr+".data", 
				dt, 
				eventCounts
		);
	}
}
