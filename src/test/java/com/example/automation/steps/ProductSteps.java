package com.example.automation.steps;

import com.example.automation.configuration.DriverFactory;
import com.example.automation.pages.CartPage;
import com.example.automation.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class ProductSteps {

    WebDriver driver = DriverFactory.getDriver();
    ProductPage productPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);

    @When("il ajoute le produit {string} au panier")
    public void addProductToCart(String productName) {
        productPage.addProductToCart(productName);
    }

    @Then("le panier contient {int} produit")
    public void verifyCartCount(int expectedCount) {
        int actualCount = cartPage.getCartItemsCount();
        Assert.assertEquals(expectedCount, actualCount);
    }
}
