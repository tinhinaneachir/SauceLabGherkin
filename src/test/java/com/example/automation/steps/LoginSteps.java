package com.example.automation.steps;

import com.example.automation.configuration.ConfigReader;
import com.example.automation.configuration.DriverFactory;
import com.example.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    ConfigReader settings = new ConfigReader();

    @Given("l'utilisateur est sur la page de connexion")
    public void openLogin() {
        loginPage.open(settings.getProperty("url"));
    }

    @When("il saisit le login {string} et le mot de passe {string}")
    public void login(String user, String pass) {
        loginPage.login(user, pass);
    }

    @Then("la connexion est échouée et un message s'affiche")
    public void verifierLoginErreur() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
    }

    @Then("le statut de la connexion devrait être {string}")
    public void etatConnexion(String etat) {
        if (etat.equalsIgnoreCase("success")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        }
    }
}
