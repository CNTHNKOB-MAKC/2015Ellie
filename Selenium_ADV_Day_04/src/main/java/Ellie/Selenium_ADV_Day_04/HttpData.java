package Ellie.Selenium_ADV_Day_04;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpData {

	final static private String CSRF_TOKEN_PATTERN = "name=\"_csrf_token\" value=\"(.*?)\"";
	
	static private CloseableHttpClient client = null;
	static private CloseableHttpResponse response = null;
	static private String sessionId = null;
	static private String csrf_token = null;

	private static String getPage(String url) {
		initializeClient();
		String body = null;
		csrf_token = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			if (sessionId != null)
				httpGet.addHeader("Cookie", "PHPSESSID="+sessionId+"; Loggedin=True");
			response = client.execute(httpGet);
			body = EntityUtils.toString(response.getEntity());
			csrf_token = matchRegexp(CSRF_TOKEN_PATTERN, body);		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeResponse();
		}
		return body;
	}
	
	public static void initializeClient() {
		if (client == null)
			client = HttpClients.createDefault();
		
	}
	
	public static void closeClient() {
		if(client != null) {
			try {
				client.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String getLoginPage() {
		return getPage("http://hrm.seleniumminutes.com");
	}
	
	private static String doGetMainPage() {
		return getPage("http://hrm.seleniumminutes.com/symfony/web/index.php/dashboard");
	}
	
	private static String doGetAddEmployeePage() {
		return getPage("http://hrm.seleniumminutes.com/symfony/web/index.php/pim/addEmployee");
	}
	
	public static String doLogin(String username, String password) throws Exception {
		getLoginPage();
		if (csrf_token == null) {
			throw new Exception("Error: Cannot find csrf_token required for authentication.");
		}
		HttpPost post = new HttpPost("http://hrm.seleniumminutes.com/symfony/web/index.php/auth/validateCredentials");
	    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	    nvps.add(new BasicNameValuePair("txtUsername", username));
	    nvps.add(new BasicNameValuePair("txtPassword", password));
	    nvps.add(new BasicNameValuePair("_csrf_token", csrf_token));
	    nvps.add(new BasicNameValuePair("Submit", "LOGIN"));
	    try {
	    	post.setEntity(new UrlEncodedFormEntity(nvps));
	    	response = client.execute(post);
	    	if(response.getStatusLine().getStatusCode() == 302) { //success
	    		Header[] headers = response.getHeaders("Set-Cookie");
		    	 for(Header i : headers) {
		    		 String v = i.getValue();
		    		 sessionId = matchRegexp("PHPSESSID=(.*?);", v);
		    		 if (sessionId != null) {
		    			 return sessionId;
		    		 }
		    	 }
	    	}
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	closeResponse();
	    }
	    return null;
	}
	
	public static String doAddEmployee(String firstName, String lastName) throws Exception {
		String middleName = "HTTP";
		String empNumber = matchRegexp("value=\"(.*)\" id=\"employeeId\"", doGetAddEmployeePage());

		if (csrf_token == null) {
			throw new Exception("Error: Cannot find csrf_token required to complete the HTTP request.");
		}
		
		HttpPost post = new HttpPost("http://hrm.seleniumminutes.com/symfony/web/index.php/pim/addEmployee");
	    
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	    nvps.add(new BasicNameValuePair("firstName", firstName));
	    nvps.add(new BasicNameValuePair("middleName", middleName));
	    nvps.add(new BasicNameValuePair("lastName", lastName));
	    nvps.add(new BasicNameValuePair("employeeId", empNumber));
	    nvps.add(new BasicNameValuePair("status", "Enabled"));
	    nvps.add(new BasicNameValuePair("empNumber", empNumber));
	    nvps.add(new BasicNameValuePair("_csrf_token", csrf_token));
	    
	    try {
	    	post.setEntity(new UrlEncodedFormEntity(nvps));
	    	response = client.execute(post);
	    	if(response.getStatusLine().getStatusCode() == 302)
	    		return empNumber;
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	closeResponse();
	    }
	    return null;
	}
	
	private static void closeResponse() {
		if(response != null) {
			try {
				response.close();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				response = null;
			}
		}
	}
	
	private static String matchRegexp(String pattern, String text) {
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(text);
		return (matcher.find()) ? matcher.group(1) : null;
	}
	
}
