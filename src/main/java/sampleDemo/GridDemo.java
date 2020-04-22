package sampleDemo;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
/**
 * Hello world!
 *
 */
public class GridDemo 
{

	ExtentReports report;
	ExtentTest logger;
		
			// TODO Auto-generated method stub

			private RemoteWebDriver driver;
			
			@BeforeMethod(alwaysRun = true)
			public void beforeMethod() throws Exception {
				report=new ExtentReports("D:\\AppTestifyDemo\\src\\main\\java\\Reports\\VideoReport.html");
				
				// Example test environment. NOTE: Gridlastic auto scaling requires all
				// these 3 environment variables in each request.
				// see test environments for capabilities to use https://www.gridlastic.com/test-environments.html
				
				String platform_name = "win10";
				String browser_name = "chrome";
				String browser_version = "latest"; // you can use "latest" for chrome only or a specific version number. For Firefox and internet explorer use a specific version number.

				

				DesiredCapabilities capabilities = new DesiredCapabilities();
				
				if (platform_name.equalsIgnoreCase("win10")) {
					capabilities.setPlatform(Platform.WIN10);
					capabilities.setCapability("platformName", "windows"); //required from selenium version 3.9.1 when testing with firefox or IE. Required when testing with Chrome 77+.
					}
				
				
				capabilities.setBrowserName(browser_name);
				capabilities.setVersion(browser_version);

				// optional video recording
							String record_video = "True";
				// video record
				if (record_video.equalsIgnoreCase("True")) {
					capabilities.setCapability("video", "True"); // NOTE: "True" is a case sensitive string, not boolean.
				} else {
					capabilities.setCapability("video", "False"); // NOTE: "False" is a case sensitive string, not boolean.
				}
				
				//Chrome specifics
				if (browser_name.equalsIgnoreCase("chrome")){
					ChromeOptions options = new ChromeOptions();
					options.addArguments("disable-infobars"); // starting from Chrome 57 the info bar displays with "Chrome is being controlled by automated test software."
					// On Linux start-maximized does not expand browser window to max screen size. Always set a window size and position.
					if (platform_name.equalsIgnoreCase("linux")) {
						options.addArguments(Arrays.asList("--window-position=0,0"));
						options.addArguments(Arrays.asList("--window-size=1920,1080"));	
						} else {
						options.addArguments(Arrays.asList("--start-maximized"));
						}
					capabilities.setCapability(ChromeOptions.CAPABILITY, options);
					} 
			

				//Firefox version 55+ specifics
				
				
				
				driver = new RemoteWebDriver(new URL("http://Gsb18VMfZfjIYYwBkLx6jXdYQ71ux5i4:okVm5ysVDBtspQTArgwB4AqKxh5VdePr@AO2RQ52L.gridlastic.com:80/wd/hub"),capabilities);
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				
				// On LINUX/FIREFOX the "driver.manage().window().maximize()" option does not expand browser window to max screen size. Always set a window size.
				if (platform_name.equalsIgnoreCase("linux") && browser_name.equalsIgnoreCase("firefox")) {
					driver.manage().window().setSize(new Dimension(1920, 1080));	
				}
		        

				if (record_video.equalsIgnoreCase("True")) {
				 
					System.out.println("Test Video: " + "http://s3-us-west-1.amazonaws.com/027a15f2-530d-31e5-f8cc-7ceaf6355377/fa0d1331-1dc7-8ff1-a72e-0e4ac2c91bb2/play.html?" + ((RemoteWebDriver) driver).getSessionId());
					
					String Url="http://s3-us-west-1.amazonaws.com/027a15f2-530d-31e5-f8cc-7ceaf6355377/fa0d1331-1dc7-8ff1-a72e-0e4ac2c91bb2/play.html?" + ((RemoteWebDriver) driver).getSessionId();
					System.out.println(Url);
					
					logger=report.startTest("Test Started");
					  logger.log(LogStatus.INFO,Url);
				}

			}

			@Test(enabled = true)
			
			 public void test_site() throws Exception  { 
				  logger.log(LogStatus.INFO,"Launch The Url");
		        driver.get("https://www.google.com/ncr");
		        Thread.sleep(10000); //slow down for demo purposes
		        WebElement element = driver.findElement(By.name("q"));
		        element.sendKeys("webdriver");
		        element.submit();
		        Thread.sleep(5000);
		        logger.log(LogStatus.INFO,"Ending the Test");
		        report.endTest(logger);
		        report.flush();
		        
			}

			@AfterMethod(alwaysRun = true)
			public void tearDown() throws Exception {
				driver.quit();
			}
}
