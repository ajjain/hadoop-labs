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
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * The Class Client.
 */
public class Client {

	/** The location list. */
	private List<List<String>> locationList = new ArrayList<>();

	/** The user list. */
	private List<List<String>> userList = new ArrayList<>();

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

	public List<List<String>> getRandomUserSession(){
		return null;
	}

	private static final Random rand = new Random();
	private static final Random srand = new Random(543291341223L);

	private static final String[] browsers = {"chrome", "firefox", "safari", "opera", "IE", "ME"};

	public void generateEventsinFile(String location, Date date, int eventCounts) throws FileNotFoundException, UnsupportedEncodingException{
		int uIndex = (int)(userList.size() * rand.nextFloat());
		int lIndex = (int)(locationList.size() * rand.nextFloat());
		List<String> uAttr = userList.get(uIndex);
		List<String> lAttr = locationList.get(lIndex);
		int totalCnt = 0;
		PrintWriter writer = new PrintWriter(location, "UTF-8");
		while(totalCnt < eventCounts){
			List<EventBean> resultEvents = UserSessionGenerator.getInstance().generate(
					""+srand.nextInt(), 
					uAttr.get(2), uAttr.get(0), uAttr.get(1), 
					lAttr.get(2), lAttr.get(2), lAttr.get(1), 
					browsers[(int)(rand.nextFloat() * browsers.length)], 
					new Date(date.getTime()+(int)(rand.nextFloat() * 1000*3600*24)), 
					(int)(rand.nextFloat() * 15*60)
					);
			totalCnt = totalCnt + resultEvents.size();
		}
	}
}
