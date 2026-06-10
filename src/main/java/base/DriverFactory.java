package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	 private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

	    public static WebDriver getDriver() {
	        return driverThreadLocal.get();
	    }


	    public static void setDriver(String browser) {
	        WebDriver driver;

	        switch (browser.toLowerCase()) {
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                FirefoxOptions firefoxOptions = new FirefoxOptions();
	                firefoxOptions.addArguments("--start-maximized");
	                driver = new FirefoxDriver(firefoxOptions);
	                break;

	            case "chrome":
	            default:
	                WebDriverManager.chromedriver().setup();
	                ChromeOptions chromeOptions = new ChromeOptions();
	                chromeOptions.addArguments("--start-maximized");
	                chromeOptions.addArguments("--disable-notifications");
	                chromeOptions.addArguments("--disable-popup-blocking");
	                // Uncomment below for headless execution
	                // chromeOptions.addArguments("--headless");
	                driver = new ChromeDriver(chromeOptions);
	                break;
	        }

	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	        driver.manage().window().maximize();
	        driverThreadLocal.set(driver);
	    }

	    public static void quitDriver() {
	        if (driverThreadLocal.get() != null) {
	            driverThreadLocal.get().quit();
	            driverThreadLocal.remove();
	        }
	    }
}
