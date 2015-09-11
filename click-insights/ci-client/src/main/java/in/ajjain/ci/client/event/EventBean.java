package in.ajjain.ci.client.event;

import com.google.gson.Gson;

/**
 * The Class EventBean.
 * 
 * @author ajjain
 */
public class EventBean {
	
	/** The gson. */
	private static Gson gson = new Gson();
	
	/** The timestamp. */
	private String timestamp;
	
	/** The url. */
	private String url;
	
	/** The method. */
	private String method;
	
	/** The browser. */
	private String browser;
	
	/** The email. */
	private String email;
	
	/** The fname. */
	private String fname;
	
	/** The lname. */
	private String lname;
	
	/** The geoname. */
	private String geoname;
	
	/** The latitude. */
	private String latitude;
	
	/** The longitude. */
	private String longitude;
	
	/** The sessionid. */
	private String sessionid;

	/**
	 * Instantiates a new event bean.
	 *
	 * @param sessionid the sessionid
	 * @param email the email
	 * @param fname the fname
	 * @param lname the lname
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param timestamp the timestamp
	 * @param geoname the geoname
	 * @param browser the browser
	 * @param url the url
	 * @param method the method
	 */
	public EventBean(String sessionid, String email, String fname,
			String lname, String latitude, String longitude, String timestamp,
			String geoname, String browser, String url, String method) {
		super();
		this.sessionid = sessionid;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.geoname = geoname;
		this.browser = browser;
		this.url = url;
		this.method = method;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * @return the geoname
	 */
	public String getGeoname() {
		return geoname;
	}

	/**
	 * @param geoname the geoname to set
	 */
	public void setGeoname(String geoname) {
		this.geoname = geoname;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the sessionid
	 */
	public String getSessionid() {
		return sessionid;
	}

	/**
	 * @param sessionid the sessionid to set
	 */
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return gson.toJson(this);
	}
}
