package com.example.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By cartBadge = By.className("shopping_cart_badge");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Retourne le nombre d'articles dans le panier
    public int getCartItemsCount() {
        try {
            String count = driver.findElement(cartBadge).getText();
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0; // aucun article
        }
    }
}
