package com.example.automation.pages;
import io.cucumber.java.de.Wenn;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(id = "user-name")
    WebElement usernameInput;
    @FindBy(id = "password")
    WebElement passwordInput;
    @FindBy(id = "login-button")
    WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();

        // renseigne les champs
        // appuye sur le bouton connexion
    }

    public void open(String url) {
        driver.get(url);
    }

    @FindBy(xpath = "//h3[@data-test='error']")
    WebElement errorMessage;

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean afficheErreur() {
        return errorMessage.isDisplayed();
    }

    public boolean isErrorMessageDisplayed() {
        return !driver.findElements((By) errorMessage).isEmpty();
    }
}
