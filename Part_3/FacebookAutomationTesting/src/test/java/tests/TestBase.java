package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class TestBase {

	public static WebDriver driver ;

	@BeforeSuite
	@Parameters({"browser"})
	public void StartDriver (@Optional("chrome") String browserName) {
		//Create a instance of ChromeOptions class
		ChromeOptions options = new ChromeOptions();
//
//		//Add chrome switch to disable notification - "**--disable-notifications**"
		options.addArguments("--disable-notifications");

		if (browserName.equalsIgnoreCase("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver1.exe");
			driver= new ChromeDriver(options);
		}
		else if (browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
			driver= new FirefoxDriver();
		} 
		else if (browserName.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.IE.driver", System.getProperty("user.dir")+"/drivers/IEDriver.exe");
			driver= new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("http://www.facebook.com");
	}
	@AfterSuite(enabled=true)
	public void StopDriver() {
		driver.close();
	}
}
