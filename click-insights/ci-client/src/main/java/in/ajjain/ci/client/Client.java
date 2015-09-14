package in.ajjain.ci.client;

import in.ajjain.ci.client.event.EventBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
		ClassLoader classLoader = getClass().getClassLoader();
		File ufile = new File(classLoader.getResource("user.csv").getFile());
		File lfile = new File(classLoader.getResource("location.csv").getFile());
		try (BufferedReader br = new BufferedReader(new FileReader(ufile))) {
			String line;
			while ((line = br.readLine()) != null) {
				List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
				userList.add(items);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		try (BufferedReader br = new BufferedReader(new FileReader(lfile))) {
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
						lAttr.get(2), lAttr.get(2), lAttr.get(1), 
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
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 11, 00, 0, 0);
		Date dt = c.getTime();
		
		Client.getInstance().generateEventsinFile("D:\\RnD\\hadoop-labs\\clicks.dat", dt, 100);
	}
}
