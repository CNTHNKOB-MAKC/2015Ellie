package Ellie.Selenium_ADV_Day_05JDBC_Data_Java database connectivity technology ;

import static org.junit.Assert.assertEquals;

import java.sql.*;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class CommonMethods {
	//private static String baseUrl = "http://hrm.tehportal.net";
	 private static String baseUrl = "http://hrm.portnov.com/";
	private static boolean acceptNextAlert = true;
	private static Connection m_connection = null;

	public static void login(WebDriver driver, String username, String password) {
		driver.get(baseUrl + "/login.php");
		assertEquals("OrangeHRM - New Level of HR Management",
				driver.getTitle());
		type(driver, By.name("txtUserName"), username);
		type(driver, By.name("txtPassword"), password);
		driver.findElement(By.name("Submit")).click();
		waitToLoadElement(driver, By.id("top-menu"), 3);
		assertEquals("OrangeHRM", driver.getTitle());
	}

	
	
	public static void logout(WebDriver driver) throws InterruptedException {
		driver.switchTo().defaultContent();
		driver.findElement(By.linkText("Logout")).click();

		waitToLoadElement(driver, By.xpath("//input[@value='Login']"), 5);
		assertEquals("OrangeHRM - New Level of HR Management",
				driver.getTitle());
	}

	public static void type(WebDriver driver, By locator, String text) {
		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(text);
	}

	public static boolean waitToLoadElement(WebDriver driver, By by, int seconds) {
		boolean found = true;

		long bailOutPeriod = 1000 * seconds;
		long lStartTime = new Date().getTime();

		while (!isElementPresent(driver, by)) {
			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime;

			if (difference < bailOutPeriod) {
				pause(500);
			} else {
				found = false;
				break;
			}
		}

		return found;
	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String closeAlertAndGetItsText(WebDriver driver) {
		try {
			Alert alert = driver.switchTo().alert();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alert.getText();
		} finally {
			acceptNextAlert = true;
		}
	}

	public static void initDBConnection() {
		if (m_connection == null) {
			
			String url = "jdbc:mysql://p3plcpnl0912.prod.phx3.secureserver.net:3306/";
			String dbName = "seleniumminutes";
			String driver = "com.mysql.jdbc.Driver";
			String username = "student";
			String password = "Student2015";

			try {
				Class.forName(driver).newInstance();
				m_connection = DriverManager.getConnection(url + dbName, username, password);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
		}
	}
	
	public static void closeDBConnection() {
		if (m_connection != null) {
			try {
				m_connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_connection = null;
		}
	}
	
	public static Connection getDBConnection() {
		initDBConnection();
		return m_connection;
	}

}
