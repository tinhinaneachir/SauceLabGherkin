package com.example.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart(String productName) {
        driver.findElement(
                By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button")
        ).click();
    }
}
