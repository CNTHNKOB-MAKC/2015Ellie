package Ellie.Selenium_ADV_Day_01;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumSingleTitle {
	public static void main(String[] args) {
		 
		WebDriver driver = new FirefoxDriver();    // Version 1.1 :: Firefox
 
		String text_case_id = "TC-001.01Selenium";
		String url = "http://www.newsli.ru/";
		String title_expected = "Newsli - новости из первых уст";
		
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String title_actual = driver.getTitle();
 
		if (title_expected.equals(title_actual)) {
			System.out.println("Test Case ID: \t\t" + text_case_id);
			System.out.println("URL: \t\t\t" + url);
			System.out.println("Title Expected: \t" + title_expected);
			System.out.println("Title Actual: \t\t" + title_actual);
			System.out.println("Test Case Result: \t" + "PASSED");
		} else {
			System.out.println("Test Case ID: \t\t" + text_case_id);
			System.out.println("URL: \t\t\t" + url);
			System.out.println("Title Expected: \t" + title_expected);
			System.out.println("Title Actual: \t\t" + title_actual);
			System.out.println("Test Case Result: \t" + "FAILED");
		}
		driver.quit();
	}
}