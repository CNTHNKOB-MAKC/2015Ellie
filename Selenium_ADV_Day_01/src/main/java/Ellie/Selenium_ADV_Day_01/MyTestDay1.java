package Ellie.Selenium_ADV_Day_01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MyTestDay1  {
	    

	 public static void main(String[] args) {
		
		 WebDriver driver;
		    String baseUrl;
		    boolean acceptNextAlert = true;
		    StringBuffer verificationErrors = new StringBuffer();
 
	    driver = new FirefoxDriver();
	    baseUrl = "http://hrm.seleniumminutes.com/";
	   // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  

	 
	    driver.get(baseUrl); //+ "/symfony/web/index.php/auth/login");
	    driver.findElement(By.cssSelector("span.form-hint")).click();
	    driver.findElement(By.id("txtUsername")).clear();
	    driver.findElement(By.id("txtUsername")).sendKeys("admin");
	    driver.findElement(By.id("txtPassword")).clear();
	    driver.findElement(By.id("txtPassword")).sendKeys("Password");
	    driver.findElement(By.id("btnLogin")).click();
	    
//		Basic Example    
	    driver.findElement(By.cssSelector("#menu_pim_viewPimModule > b")).click();
	    driver.findElement(By.id("menu_pim_viewEmployeeList")).click();
	    
	//  Actions Un-chained Example
//	    Actions action = new Actions(driver);
//	    action.moveToElement(driver.findElement(By.cssSelector("#menu_pim_viewPimModule > b")));
//	    action.click(driver.findElement(By.id("menu_pim_viewEmployeeList")));
//	    action.perform();

	//  Action Chain Example  
//	    Actions action = new Actions(driver);
//			action.moveToElement(driver.findElement(By.cssSelector("#menu_pim_viewPimModule > b")))
//					.click(driver.findElement(By.id("menu_pim_viewEmployeeList")))
//					.perform();
	    
	    // Solution 1
	    List<WebElement> all_even_rows = driver.findElement(By.id("resultTable")).findElements(By.xpath(".//tr[(position() mod 2)=0]"));
	    for (WebElement row: all_even_rows) {
	    	String row_type = row.getAttribute("class");
	    	assertEquals("Row style did not match the expected 'even' style.", "even", row_type);
	    }
	    
	     
 
	    driver.quit();
	    
}}

	 
	  
