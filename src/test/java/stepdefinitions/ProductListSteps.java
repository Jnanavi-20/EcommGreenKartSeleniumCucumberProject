package stepdefinitions;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ProductListPage;
import utils.ConfigReader;

public class ProductListSteps {
	
	private WebDriver driver;
	 ProductListPage productPage;

	// ─── Background ──────────────────────────────────────────────────────────────

	    @Given("User launches GreenKart application")
	    public void user_launches_greenkart_application() {
	        driver = DriverFactory.getDriver();
	        driver.get(ConfigReader.getBaseUrl());
	        productPage = new ProductListPage(driver);
	    }

	    // ─── Search Steps ─────────────────────────────────────────────────────────

	    @When("User enters {string} in the search box")
	    public void user_enters_in_the_search_box(String keyword) {
	    	productPage.enterSearchKeyword(keyword);
	    	
	    }

	    @Then("Search results should display products matching {string}")
	    public void search_results_should_display_products_matching(String keyword) {
	    	    List<String> names = productPage.getVisibleProductNames();
	    	    Assert.assertTrue("No products found for keyword: " + keyword, names.size() > 0);
	    	    for (String name : names) {
	    	        Assert.assertTrue(
	    	            "Product '" + name + "' does not match keyword: " + keyword,
	    	            name.equalsIgnoreCase(keyword) || name.toLowerCase().contains(keyword.toLowerCase()));
	    	    }
//	    	boolean result = productPage.validateSearchResults(keyword);
//	    	Assert.assertTrue("Search results are not matching!", result);
	    }
	    

	    @Then("No products should be displayed")
	    public void no_products_should_be_displayed() {
	        int count = productPage.getVisibleProductCount();
	        Assert.assertEquals("Expected 0 products but found: " + count, 0, count);
	    }

	    @And("User clears the search box")
	    public void user_clears_the_search_box() {
	    	productPage.clearSearch();
	    }

	    // ─── Product Listing Steps ────────────────────────────────────────────────

	    @Then("The following products should be visible on the page")
	    public void the_following_products_should_be_visible(DataTable dataTable) {
	        List<String> expectedProducts = dataTable.asList();
	        for (String product : expectedProducts) {
	            Assert.assertTrue(
	                    "Product not found on page: " + product,
	                    productPage.isProductVisible(product.trim()));
	        }
	    }

	    // ─── Product Price Steps ──────────────────────────────────────────────────

	    @Then("The following products should have correct prices")
	    public void the_following_products_should_have_correct_prices(DataTable dataTable) {
	        List<Map<String, String>> productData = dataTable.asMaps(String.class, String.class);
	        for (Map<String, String> row : productData) {
	            String productName = row.get("Product Name").trim();
	            String expectedPrice = row.get("Price").trim();
	            String actualPrice = productPage.getProductPrice(productName);
	            Assert.assertEquals(
	                    "Price mismatch for product: " + productName,
	                    expectedPrice, actualPrice);
	        }
	    }
	    

	    // ─── Quantity Stepper Steps ───────────────────────────────────────────────

	    @When("User increments quantity of {string} to {string}")
	    public void user_increments_quantity_of_to(String productName, String quantity) {
	    	productPage.incrementProductQuantity(productName, Integer.parseInt(quantity));
	    }

	    @When("User decrements quantity of {string} to {string}")
	    public void user_decrements_quantity_of_to(String productName, String quantity) {
	    	productPage.decrementProductQuantity(productName, Integer.parseInt(quantity));
	    }

	    @Then("Default quantity for {string} should be {string}")
	    public void default_quantity_for_should_be(String productName, String expectedQty) {
	        String actualQty = productPage.getProductQuantity(productName);
	        Assert.assertEquals(
	                "Default quantity mismatch for: " + productName,
	                expectedQty, actualQty);
	    }

	    // ─── Add to Cart Steps ────────────────────────────────────────────────────

	    @When("User adds {string} to the cart")
	    public void user_adds_to_the_cart(String productName) {
	    	productPage.addProductToCart(productName);
	    }

	    @When("User adds the following products to cart")
	    public void user_adds_the_following_products_to_cart(DataTable dataTable) {
	    	 List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
	    	    int runningCount = Integer.parseInt(productPage.getCartItemCount()); // start from current count

	    	    for (Map<String, String> row : products) {
	    	        String productName = row.get("Product Name").trim();
	    	        int quantity = Integer.parseInt(row.get("Quantity").trim());
	    	        if (quantity <= 0) {
	    	            throw new IllegalArgumentException(
	    	                "Invalid quantity for product: " + productName + ". Quantity must be >= 1"
	    	            );
	    	        }

	    	        if (quantity > 1) {
	    	            productPage.incrementProductQuantity(productName, quantity);
	    	        }

	    	        productPage.addProductToCart(productName);
	    	        runningCount++;

	    	        // Wait for cart to actually reflect the new item before moving on
	    	        final int expectedCount = runningCount;
	    	        new WebDriverWait(driver, Duration.ofSeconds(10))
	    	            .until(d -> productPage.getCartItemCount().equals(String.valueOf(expectedCount)));
	    	    }

	    }

	    @Then("Cart item count should be {string}")
	    public void cart_item_count_should_be(String expectedCount) {
	        String actualCount = productPage.getCartItemCount();
	        Assert.assertEquals(
	                "Cart item count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount,
	                expectedCount, actualCount);
	    }

	    @Then("Cart total price should be {string}")
	    public void cart_total_price_should_be(String expectedPrice) {
	        String actualPrice = productPage.getCartTotalPrice();
	        Assert.assertEquals(
	                "Cart price mismatch. Expected: " + expectedPrice + ", Actual: " + actualPrice,
	                expectedPrice, actualPrice);
	    }
	    
	    @And("User clicks on Place Order")
	    public void User_clicks_on_Place_Order() {
	    	productPage.clickPlaceOrder();
	    		
	    }
	    
	    @And("User clicks on proceed")
	    public void User_clicks_on_proceed() {
	    	    productPage.checkBoxTandC();
	    	   
	    		productPage.clickProceedBTN();
	    	}
	    

	    @Then("Proceed to checkout button should be disabled")
	    public void proceed_to_checkout_button_should_be_disabled() {
	        Assert.assertTrue(
	                "Proceed to checkout button should be disabled when cart is empty",
	                productPage.isProceedToCheckoutDisabled());
	    }

	    @Then("The {string} button for {string} should show {string}")
	    public void the_button_for_should_show(String buttonLabel, String productName, String expectedText) {
	    	  String actualText = productPage.getAddToCartButtonText(productName);
	    	    Assert.assertTrue(
	    	        "Button text mismatch for product: " + productName +
	    	        ". Expected to contain: '" + expectedText + "', Actual: '" + actualText + "'",
	    	        actualText.contains(expectedText));
	    }

	    // ─── Cart Preview Steps ───────────────────────────────────────────────────

	    @When("User clicks on cart icon")
	    public void user_clicks_on_cart_icon() {
	    	productPage.clickCartIcon();
	    }

	    @Then("Cart preview should show {string}")
	    public void cart_preview_should_show(String productName) {
	        Assert.assertTrue(
	                "Product '" + productName + "' not found in cart preview",
	                productPage.isProductInCartPreview(productName));
	    }

	    // ─── Checkout Steps ───────────────────────────────────────────────────────

	    @When("User clicks proceed to checkout")
	    public void user_clicks_proceed_to_checkout() {
	    	productPage.clickonProceedtoCheckoutBTN();
	    }

	    @When("User selects country {string}")
	    public void user_selects_country(String country) {
	    	productPage.CountrySelect(country);
	    }

	    @When("User clicks Buy Now")
	    public void user_clicks_buy_now() {
	    	productPage.clickBuyNow();
	    }

	    @Then("Order confirmation message should be displayed")
	    public void order_confirmation_message_should_be_displayed() {
	        Assert.assertTrue(
	                "Thank you, your order has been placed successfully",
	                productPage.isOrderConfirmationDisplayed());
	    }

	    // ─── Navigation Steps ─────────────────────────────────────────────────────

	    @When("User clicks on {string} link")
	    public void user_clicks_on_link(String linkText) {
	        if (linkText.equals("Top Deals")) {
	        	productPage.clickTopDealsLink();
	        }
	    }

	    @Then("A new tab or redirect should open")
	    public void a_new_tab_or_redirect_should_open() {
	        Assert.assertTrue(
	                "Expected more than one window/tab to be open",
	                driver.getWindowHandles().size() > 1);
	    }

	    // ─── Header Element Steps ─────────────────────────────────────────────────

	    @Then("GreenKart brand logo should be displayed")
	    public void greenkart_brand_logo_should_be_displayed() {
	        Assert.assertTrue("Brand logo not displayed", productPage.isBrandLogoDisplayed());
	    }

	    @Then("Search bar should be visible")
	    public void search_bar_should_be_visible() {
	        Assert.assertTrue("Search bar not visible", productPage.isSearchBarVisible());
	    }

	    @Then("Cart icon should be visible")
	    public void cart_icon_should_be_visible() {
	        Assert.assertTrue("Cart icon not visible", productPage.isCartIconVisible());
	    }

	    @Then("Cart info table should show items and price")
	    public void cart_info_table_should_show_items_and_price() {
	        Assert.assertTrue("Cart info table not visible", productPage.isCartInfoTableVisible());
	    }
	}

