package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;

public class Hooks {


	 @Before(order = 0)
	    public void setUp(Scenario scenario) {
	        System.out.println("========================================");
	        System.out.println("Starting Scenario: " + scenario.getName());
	        System.out.println("Tags: " + scenario.getSourceTagNames());
	        System.out.println("========================================");
	        String browser = ConfigReader.getBrowser();
	        DriverFactory.setDriver(browser);
	    }

	    @AfterStep
	    public void afterStep(Scenario scenario) {
	        if (scenario.isFailed()) {
	            // Capture screenshot on step failure
	            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
	                    .getScreenshotAs(OutputType.BYTES);
	            scenario.attach(screenshot, "image/png", "Step Failure Screenshot");
	        }
	    }

	    @After(order = 0)
	    public void tearDown(Scenario scenario) {
	        System.out.println("========================================");
	        System.out.println("Scenario: " + scenario.getName());
	        System.out.println("Status: " + scenario.getStatus());
	        System.out.println("========================================");

	        if (scenario.isFailed()) {
	            // Capture final screenshot on scenario failure
	            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
	                    .getScreenshotAs(OutputType.BYTES);
	            scenario.attach(screenshot, "image/png", "Final Failure Screenshot - " + scenario.getName());
	        }

	        DriverFactory.quitDriver();
	    }
}
