package base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	    protected WebDriver driver;
	    protected WebDriverWait wait;

	    public BasePage() {
	        this.driver = DriverFactory.getDriver();
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    // Click action
	    public void click(By locator) {
	        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	    }

	    // SendKeys action
	    public void type(By locator, String text) {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
	    }

	    // Get text
	    public String getText(By locator) {
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
	    }

	    // Check visibility
	    public boolean isDisplayed(By locator) {
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
	    }
}
