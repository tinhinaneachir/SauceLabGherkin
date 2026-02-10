package com.example.automation.steps;

import com.example.automation.configuration.ConfigReader;
import com.example.automation.configuration.DriverFactory;
import com.example.automation.configuration.Hooks;
import com.example.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    WebDriver driver = Hooks.getDriver();
    LoginPage loginPage;
    ConfigReader settings = new ConfigReader();

    @Given("l'utilisateur est sur la page de connexion")
    public void openLogin() {
        loginPage = new LoginPage(driver);
        loginPage.open(settings.getProperty("url"));
    }

    @When("il saisit le login {string} et le mot de passe {string}")
    public void login(String user, String pass) {
        loginPage.login(user, pass);
    }

    @Then("le résultat de la connexion est {string}")
    public void le_résultat_de_la_connexion_est(String resultat) {
        if (resultat.equalsIgnoreCase("success")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        }
    }
}

