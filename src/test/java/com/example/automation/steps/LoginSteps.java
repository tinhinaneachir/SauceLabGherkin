package com.example.automation.steps;

import com.example.automation.configuration.ConfigReader;
import com.example.automation.configuration.DriverFactory;
import com.example.automation.pages.LoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private ConfigReader settings = new ConfigReader();

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();   // Assure que le driver est prêt
        loginPage = new LoginPage(driver);    // Initialise la page ici
    }

    @Given("l'utilisateur est sur la page de connexion")
    public void openLogin() {
        loginPage.open(settings.getProperty("url"));
    }

    @When("il saisit le login {string} et le mot de passe {string}")
    public void login(String user, String pass) {
        loginPage.login(user, pass);
    }

    @Then("le statut de la connexion devrait être {string}")
    public void etatConnexion(String etat) {
        if (etat.equalsIgnoreCase("success")) {
            Assert.assertTrue("L'utilisateur n'a pas été redirigé vers la page d'inventaire",
                    driver.getCurrentUrl().contains("inventory"));
        } else {
            Assert.assertTrue("Le message d'erreur n'est pas affiché",
                    loginPage.isErrorMessageDisplayed());
            Assert.assertTrue("Le message d'erreur est incorrect",
                    loginPage.getErrorMessage().contains("Epic sadface"));
        }
    }
}
