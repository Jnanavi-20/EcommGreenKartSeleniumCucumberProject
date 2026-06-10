@GreenKart
Feature: GreenKart - Vegetables and Fruits Online Shopping

  Background:
    Given User launches GreenKart application
	
  @SearchFunctionality
  Scenario: Verify search functionality with valid product
    When User enters "Carrot" in the search box
    Then Search results should display products matching "Carrot"
    And User clears the search box

  @SearchNoResults
  Scenario: Verify search with no matching product
    When User enters "Z123" in the search box
    Then No products should be displayed
    And User clears the search box

  @ProductListing
  Scenario: Verify all products are listed on the home page
    Then The following products should be visible on the page
      | Brocolli - 1 Kg       |
      | Cauliflower - 1 Kg    |
      | Cucumber - 1 Kg       |
      | Beetroot - 1 Kg       |
      | Carrot - 1 Kg         |
      | Tomato - 1 Kg         |
      | Beans - 1 Kg          |
      | Brinjal - 1 Kg        |
      | Capsicum              |
      | Mushroom - 1 Kg       |
      | Potato - 1 Kg         |
      | Pumpkin - 1 Kg        |
      | Corn - 1 Kg           |
      | Onion - 1 Kg          |
      | Apple - 1 Kg          |
      | Banana - 1 Kg         |
      | Grapes - 1 Kg         |
      | Mango - 1 Kg          |
      | Musk Melon - 1 Kg     |
      | Orange - 1 Kg         |
      | Pears - 1 Kg          |
      | Pomegranate - 1 Kg    |
      | Raspberry - 1/4 Kg    |
      | Strawberry - 1/4 Kg   |
      | Water Melon - 1 Kg    |
      | Almonds - 1/4 Kg      |
      | Pista - 1/4 Kg        |
      | Nuts Mixture - 1 Kg   |
      | Cashews - 1 Kg        |
      | Walnuts - 1/4 Kg      |

  @ProductPrice
  Scenario: Verify product prices are displayed correctly
    Then The following products should have correct prices
      | Product Name        | Price |
      | Brocolli - 1 Kg    | 120   |
      | Cauliflower - 1 Kg | 60    |
      | Cucumber - 1 Kg    | 48    |
      | Beetroot - 1 Kg    | 32    |
      | Carrot - 1 Kg      | 56    |
      | Tomato - 1 Kg      | 16    |
      | Beans - 1 Kg       | 82    |
      | Brinjal - 1 Kg     | 35    |
      | Capsicum           | 60    |
      | Mushroom - 1 Kg    | 75    |
      | Potato - 1 Kg      | 22    |
      | Pumpkin - 1 Kg     | 48    |
      | Corn - 1 Kg        | 75    |
      | Onion - 1 Kg       | 16    |
      | Apple - 1 Kg       | 72    |
      | Banana - 1 Kg      | 45    |
      | Grapes - 1 Kg      | 60    |
      | Mango - 1 Kg       | 75    |
      | Musk Melon - 1 Kg  | 36    |
      | Orange - 1 Kg      | 75    |
      | Pears - 1 Kg       | 69    |
      | Pomegranate - 1 Kg | 95    |
      | Raspberry - 1/4 Kg | 160   |
      | Strawberry - 1/4 Kg| 180   |
      | Water Melon - 1 Kg | 28    |
      | Almonds - 1/4 Kg   | 200   |
      | Pista - 1/4 Kg     | 190   |
      | Nuts Mixture - 1 Kg| 950   |
      | Cashews - 1 Kg     | 650   |
      | Walnuts - 1/4 Kg   | 170   |

  @AddToCart
  Scenario: Add a single product to cart
    When User adds "Brocolli - 1 Kg" to the cart
    Then Cart item count should be "1"
    And Cart total price should be "120"

  @AddMultipleToCart
  Scenario: Add multiple products to cart
    When User adds the following products to cart
      | Product Name        | Quantity |
      | Tomato - 1 Kg       | 1        |
      | Onion - 1 Kg        | 4        |
      | Potato - 1 Kg       | 3        |
    Then Cart item count should be "3"
    And Cart total price should be "146"

  @IncrementQuantity
  Scenario: Verify increment quantity stepper for a product
    When User increments quantity of "Carrot - 1 Kg" to "3"
    And User adds "Carrot - 1 Kg" to the cart
    Then Cart total price should be "168"

  @DecrementQuantity
  Scenario: Verify decrement quantity stepper for a product
    When User increments quantity of "Apple - 1 Kg" to "3"
    And User decrements quantity of "Apple - 1 Kg" to "2"
    And User adds "Apple - 1 Kg" to the cart
    Then Cart total price should be "144"

  @EmptyCart
  Scenario: Verify cart is empty on page load
    Then Cart item count should be "0"
    And Cart total price should be "0"
    And Proceed to checkout button should be disabled

  @CartPreview
  Scenario: Verify cart preview shows added items
    When User adds "Mango - 1 Kg" to the cart
    And User clicks on cart icon
    Then Cart preview should show "Mango - 1 Kg"

 @CheckoutFlow
Scenario: Verify checkout flow with country selection
  When User adds "Strawberry - 1/4 Kg" to the cart
  And User clicks on cart icon
  And User clicks proceed to checkout
  And User clicks on Place Order
  And User selects country "India"
  And User clicks on proceed
  Then Order confirmation message should be displayed
  @NavigationLinks
  Scenario: Verify Top Deals navigation link
    When User clicks on "Top Deals" link
    Then A new tab or redirect should open

  @HeaderElements
  Scenario: Verify header elements are displayed
    Then GreenKart brand logo should be displayed
    And Search bar should be visible
    And Cart icon should be visible
    And Cart info table should show items and price

  @AddToCartButtonState
  Scenario: Verify ADD TO CART button changes state after adding
    When User adds "Banana - 1 Kg" to the cart
    Then The "ADD TO CART" button for "Banana - 1 Kg" should show "✔ ADDED"
    
  @QuantityDefault
  Scenario: Verify default quantity is 1 for all products
    Then Default quantity for "Cucumber - 1 Kg" should be "1"
    And Default quantity for "Grapes - 1 Kg" should be "1"
    And Default quantity for "Cashews - 1 Kg" should be "1"
