package Ellie.Selenium_ADV_Day_05;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


@RunWith(Parameterized.class)
public class JDBC2Calculator {
	private WebDriver driver;
	private String baseUrl;
	private int num1;
	private String operator;
	private int num2;
	private String expected_result;

	public JDBC2Calculator(int num1, String operator, int num2,
			String expected_result) {
		super();
		this.num1 = num1;
		this.operator = operator;
		this.num2 = num2;
		this.expected_result = expected_result;
	}

	@Parameters
	public static Collection<Object[]> data() {
		String query = "SELECT * FROM Calculator_Data";
		List <Object[]> data = new ArrayList<Object[]>();
		
		CommonMethods.initDBConnection();
		Connection connection = CommonMethods.getDBConnection();
	
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(query);
		
			System.out.print(result.getFetchSize());
			
			while(result.next()) {
				int num1 = result.getInt("num1");
				int num2 = result.getInt("num2");
				String operator = result.getString("oper");
				String expected_result = result.getString("exp_result");
				
				Object[] test = {num1, operator, num2, expected_result};
				data.add(test);
				
			}
			
			result.close();
			stmt.close();
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CommonMethods.closeDBConnection();
		return data;

	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://chemistry.about.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testDDCalculator() throws Exception {
		driver.get(baseUrl + "/library/weekly/blcalculator.htm");
		assertEquals("Online Calculator", driver.getTitle());
		driver.findElement(By.name("cal" + num1)).click();
		driver.findElement(By.name("cal" + operator)).click();
		driver.findElement(By.name("cal" + num2)).click();
		driver.findElement(By.name("calequal")).click();
		assertEquals(expected_result, driver
				.findElement(By.name("calcResults")).getAttribute("value"));
		driver.findElement(By.name("calclear")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
