package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;


public class BaseTest {
	public static WebDriver driver;
	

	public static WebDriver initializeDriver() throws IOException

	{
		// properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/test/resources/Config.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		//prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			//WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")){
			options.addArguments("--headless");
			options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
			options.addArguments("blink-settings=imagesEnabled=false");
			options.addArguments("--disk-cache-dir=/path/to/cache");
			}		
			driver = new ChromeDriver(options);
			

		} else if (browserName.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("permissions.default.image", 2);
			driver = new FirefoxDriver(options);
			// Firefox
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			EdgeOptions options = new EdgeOptions();
			options.addArguments("blink-settings=imagesEnabled=false");
			driver = new EdgeDriver(options);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	    driver.get(prop.getProperty("URL"));
		return driver;
	}
	

	
//	@AfterMethod(alwaysRun=true)
//	
//	public void tearDown() throws IOException
//	{
//
//		 driver = initializeDriver();
//		driver.close();
//	}



}