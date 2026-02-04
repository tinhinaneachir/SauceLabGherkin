package com.example.automation.steps;

import com.example.automation.configuration.DriverFactory;
import com.example.automation.pages.BasePage;
import com.example.automation.pages.CartPage;
import com.example.automation.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;



public class ProductSteps extends  BasePage  {

    ProductPage productsPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);

    public ProductSteps() {
        super(DriverFactory.getDriver());
        this.driver = DriverFactory.getDriver();
        this.productsPage = new ProductPage(driver);
    }


    @When("il ajoute le produit {string} au panier")
    public void addProductToCart(String productName) {
        productsPage.addProductToCart(productName);
    }

    @Then("le panier contient {int} produit")
    public void verifyCartCount(int count) {
        Assert.assertEquals(count, cartPage.getCartItemsCount());
    }
}
