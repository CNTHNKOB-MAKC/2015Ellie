
package Ellie.Selenium_ADV_Day_03;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.csvreader.CsvReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class DDCalculator {
	
	private WebDriver driver;
	private static final String URL = "http://chemistry.about.com/library/weekly/blcalculator.htm";
	private int num1;
	private int num2;
	private String oper;
	private double exp_result;
	
//	@Parameters
//    public static Collection<Object[]> get_data() {
//        return Arrays.asList(new Object[][] { { 0, "calplus", 7, 7.00 }, { 1, "caldiv", 5, 0.20 },
//                { 0, "caldiv", 9, 0 }, { 2, "calmul", 3, 6.00 }, { 1, "calminus", 5, -4.00 }, });
//    }

	@Parameters
    public static Collection<Object[]> data() throws FileNotFoundException {
		List<Object[]> data = new ArrayList<Object[]>();
		CsvReader reader = new CsvReader("data.csv");

		try {
			reader.readHeaders();
			while (reader.readRecord())
			{
				int num1 = Integer.valueOf(reader.get("num1"));
				String oper = reader.get("oper");
				int num2 = Integer.valueOf(reader.get("num2"));
				double exp_result = Double.valueOf(reader.get("exp_result"));
				
				Object[] singleTest = {num1, oper, num2, exp_result};
				data.add(singleTest);
				
				// perform program logic here

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.close();
        return data;
    }

	
    public DDCalculator(int num1, String oper, int num2, double result) {
        super();
        this.num1 = num1;
        this.num2 = num2;
        this.oper = oper;
        exp_result = result;
    }

    @Before
	public void setUp() throws Exception {
		driver = getBrowser();
	}

	@After
	public void tearDown() throws Exception {
		getBrowser().quit();
	}
    
    @Test
	public void testDDCalculator() throws Exception {
		driver.get(URL);
		assertEquals("Online Calculator", driver.getTitle());
		driver.findElement(By.name("calcResults")).sendKeys(String.valueOf(num1));
		driver.findElement(By.name(oper)).click();
		driver.findElement(By.name("calcResults")).sendKeys(String.valueOf(num2));
		driver.findElement(By.name("calequal")).click();
		Double result = Double.valueOf(driver.findElement(By.name("calcResults")).getAttribute("value"));

		assertTrue("Acquired result " + result + " does not equal expected result " + exp_result, result == exp_result);
	}
	
	private WebDriver getBrowser() {
		if (driver == null) {
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return driver;
	}
}
