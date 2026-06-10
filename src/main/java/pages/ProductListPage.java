package pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert; 
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductListPage {

	    private WebDriver driver;
	    private WebDriverWait wait;

	    // ─── Locators ────────────────────────────────────────────────────────────────

	    // Header
	    private By brandLogo           = By.cssSelector(".brand.greenLogo");
	    private By searchInput         = By.cssSelector("input.search-keyword");
	    private By searchButton        = By.cssSelector("button.search-button");
	    private By cartIcon            = By.cssSelector("a.cart-icon");
	    private By cartInfoTable       = By.cssSelector(".cart-info table");
	    private By cartItemCount       = By.cssSelector(".cart-info tr:nth-child(1) strong");
	    private By cartTotalPrice      = By.cssSelector(".cart-info tr:nth-child(2) strong");
	    private By topDealsLink        = By.xpath("//a[text()='Top Deals']");

	    // Products
	    private By allProducts         = By.cssSelector(".product");
	    private By productName         = By.cssSelector(".product-name");
	    private By productPrice        = By.cssSelector(".product-price");
	    private By incrementBtn        = By.cssSelector("a.increment");
	    private By decrementBtn        = By.cssSelector("a.decrement");
	    private By quantityInput       = By.cssSelector("input.quantity");
	    private By addToCartBtn        = By.cssSelector(".product-action button");

	    // Cart Preview
	    private By cartPreview         = By.cssSelector(".cart-preview");
	    private By proceedToCheckoutBtn = By.xpath(
	    		"//div[contains(@class,'cart-preview')]//button[not(contains(@class,'disabled'))]");
        private By placeOrderBtn = By.xpath("//button[text()='Place Order']");
        private By checkBox = By.cssSelector(".chkAgree");
        
        
	    // Checkout Modal
	    private By checkoutModal       = By.cssSelector(".checkoutKartModal");
	    private By checkoutModalBtnOne = By.cssSelector("#checkoutModalBtnOne:not(.disabled)");
	    private By checkoutModalBtnTwo = By.cssSelector("#checkoutModalBtnTwo:not(.disabled)");
	    private By countryInput        = By.cssSelector("#react-select-2-input");
	    private By countryDropdownMenu = By.cssSelector(".css-26l3qy-menu");
	    private By countryOptions      = By.cssSelector("[class*='option']");
	    private By orderConfirmation   = By.cssSelector(".wrapperTwo");
	    private By dropdown = By.xpath("//label[text()='Choose Country']/following::select[1]");

	    private By proceedBtn = By.xpath("//button[text()='Proceed']");
	    // ─── Constructor ─────────────────────────────────────────────────────────────

	    public ProductListPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20));
	    }

	    // ─── Private Helper ───────────────────────────────────────────────────────────

	    private WebElement getProductElement(String name) {
	    	List<WebElement> products = wait.until(
	                ExpectedConditions.presenceOfAllElementsLocatedBy(allProducts));
	        return products.stream()
	                .filter(p -> {
	                    try {
	                        return p.findElement(productName)
	                                 .getText().equalsIgnoreCase(name);
	                    } catch (StaleElementReferenceException e) {
	                        return false; // skip stale, stream will find fresh one
	                    }
	                })
	                .findFirst()
	                .orElseThrow(() -> new RuntimeException("Product not found: " + name));
	    }
	    
	    

	    // ─── Header Actions ───────────────────────────────────────────────────────────

	    public boolean isBrandLogoDisplayed() {
	        return wait.until(ExpectedConditions
	                .visibilityOfElementLocated(brandLogo)).isDisplayed();
	    }

	    public boolean isSearchBarVisible() {
	        return driver.findElement(searchInput).isDisplayed();
	    }

	    public boolean isCartIconVisible() {
	        return driver.findElement(cartIcon).isDisplayed();
	    }

	    public boolean isCartInfoTableVisible() {
	        return driver.findElement(cartInfoTable).isDisplayed();
	    }

	    public void enterSearchKeyword(String keyword) {
	    	WebElement input = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(searchInput));
	        input.clear();
	        input.sendKeys(keyword);

	        // Give the app a moment to start filtering
	        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
	        
	    }
	    public void enterSearchText(String text) {
	        WebElement search = driver.findElement(searchInput);
	        search.clear();
	        search.sendKeys(text);
	    }
	    public List<WebElement> getSearchResults() {
	    	wait.until(ExpectedConditions
	                .numberOfElementsToBeMoreThan(allProducts, 0));
	        return driver.findElements(productName);
	    }

	    public void clearSearch() {
	        driver.findElement(searchInput).clear();
	    }

	    public void clickTopDealsLink() {
	        wait.until(ExpectedConditions
	                .elementToBeClickable(topDealsLink)).click();
	    }

	    // ─── Product Actions ──────────────────────────────────────────────────────────

	    public List<String> getVisibleProductNames() {
	        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(allProducts, 0));
	        return driver.findElements(allProducts).stream()
	                .filter(WebElement::isDisplayed)
	                .map(p -> p.findElement(productName).getText().trim())
	                .collect(Collectors.toList());
	    }


	    public int getVisibleProductCount() {
	        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
	        return (int) driver.findElements(allProducts).stream()
	                .filter(WebElement::isDisplayed)
	                .count();
	    }

	    public boolean isProductVisible(String name) {
	        return driver.findElements(allProducts).stream()
	                .anyMatch(p -> p.findElement(productName)
	                        .getText().equalsIgnoreCase(name));
	    }
	    public boolean validateSearchResults(String expectedText) {

	        List<WebElement> results = getSearchResults();

	        for (WebElement product : results) {
	            String name = product.getText().toLowerCase();

	            if (!name.contains(expectedText.toLowerCase())) {
	                return false;
	            }
	        }
	        return results.size() > 0;
	    }

	  
	    
	

	    public String getProductPrice(String name) {
	        WebElement product = getProductElement(name);
	        return product.findElement(productPrice).getText();
	    }

	    public String getProductQuantity(String name) {
	        WebElement product = getProductElement(name);
	        return product.findElement(quantityInput).getAttribute("value");
	    }

	    public void incrementProductQuantity(String name, int targetQty) {
	    	 // Re-read current quantity fresh every time — don't cache it
	        wait.until(driver -> {
	            try {
	                int current = Integer.parseInt(
	                    getProductElement(name).findElement(quantityInput).getAttribute("value"));
	                if (current >= targetQty) return true;

	                WebElement btn = getProductElement(name).findElement(incrementBtn);
	                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
	                return false; // keep looping
	            } catch (StaleElementReferenceException e) {
	                return false; // retry on stale
	            }
	        });
	    }

	    public void decrementProductQuantity(String name, int targetQty) {
	    	int current = Integer.parseInt(
	                getProductElement(name).findElement(quantityInput).getAttribute("value"));

	        for (int i = current; i > targetQty; i--) {
	            // Re-fetch fresh on every click
	            WebElement freshProduct = getProductElement(name);
	            WebElement btn = freshProduct.findElement(decrementBtn);
	            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();

	            int expected = i - 1;
	            wait.until(driver -> {
	                String val = getProductElement(name)
	                        .findElement(quantityInput).getAttribute("value");
	                return Integer.parseInt(val) == expected;
	            });
	        }
	    }

	    public void addProductToCart(String name) {
	        WebElement product = getProductElement(name);
	        wait.until(ExpectedConditions
	                .elementToBeClickable(product.findElement(addToCartBtn))).click();
	    }

	    public String getAddToCartButtonText(String name) {
	        WebElement product = getProductElement(name);
	        return product.findElement(addToCartBtn).getText();
	    }

	    // ─── Cart Actions ─────────────────────────────────────────────────────────────

	    public String getCartItemCount() {
	        return driver.findElement(cartItemCount).getText();
	    }

	    public String getCartTotalPrice() {
	        return driver.findElement(cartTotalPrice).getText();
	    }

	    public boolean isProceedToCheckoutDisabled() {
	        WebElement btn = driver.findElement(
	                By.cssSelector(".cart-preview .action-block button"));
	        return btn.getAttribute("class").contains("disabled");
	    }

	    public void clickCartIcon() {
	        wait.until(ExpectedConditions
	                .elementToBeClickable(cartIcon)).click();
	        wait.until(ExpectedConditions
	                .visibilityOfElementLocated(cartPreview));
	    }

	    public boolean isProductInCartPreview(String name) {
	        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
	        WebElement preview = driver.findElement(cartPreview);
	        return preview.getText().contains(name);
	    }

	    // ─── Checkout Actions ─────────────────────────────────────────────────────────
	    public void clickPlaceOrder() {
	    	wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
	    }

	    public void clickProceedToCheckout() {
	    	 // Wait for cart preview to be visible first
	        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPreview));

	        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.cssSelector(".cart-preview .action-block button:not(.disabled)")));

	        ((org.openqa.selenium.JavascriptExecutor) driver)
	                .executeScript("arguments[0].scrollIntoView(true);", btn);
	        ((org.openqa.selenium.JavascriptExecutor) driver)
	                .executeScript("arguments[0].click();", btn);

	        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutModal));
	    }
	    

	    public void selectCountry(String country) {
	        driver.findElement(By.cssSelector("#checkoutModalBtnOne")).click();
	        wait.until(ExpectedConditions
	                .elementToBeClickable(countryInput)).sendKeys(country);
	        wait.until(ExpectedConditions
	                .visibilityOfElementLocated(countryDropdownMenu));

	        List<WebElement> options = driver.findElements(countryOptions);
	        for (WebElement option : options) {
	            if (option.getText().contains(country)) {
	                option.click();
	                break;
	            }
	        }
	    }
	    
	    public void CountrySelect(String country) {
	    	WebElement drop = driver.findElement(dropdown);
	    	Select select = new Select(drop);
	    	select.selectByVisibleText(country);
	    	
	    	
	    }
	    
	    public void clickonProceedtoCheckoutBTN() {
	    	wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutBtn));
	    	driver.findElement(proceedToCheckoutBtn).click();
	    }
	    
	    public void clickProceedBTN() {
	    	wait.until(ExpectedConditions.elementToBeClickable(proceedBtn));
	    	driver.findElement(proceedBtn).click();
	    }
	    
	    public void checkBoxTandC() {
	    	wait.until(ExpectedConditions.elementToBeClickable (checkBox));
	    	driver.findElement(checkBox).click();
	    }

	    public void clickBuyNow() {
	        wait.until(ExpectedConditions
	                .elementToBeClickable(checkoutModalBtnTwo)).click();
	    }

	    public boolean isOrderConfirmationDisplayed() {
	        try {
	            WebElement confirmation = wait.until(ExpectedConditions
	                    .visibilityOfElementLocated(orderConfirmation));
	            return confirmation.isDisplayed() &&
	                   confirmation.getText().contains("Thank you, your order has been placed successfully");
	        } catch (Exception e) {
	            return false;
	        }
	    }
	
}
