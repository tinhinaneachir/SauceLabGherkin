package com.example.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(className = "shopping_cart_badge")
    WebElement cartBadge;

    public int getCartItemsCount() {
        return Integer.parseInt(cartBadge.getText());
    }

}
