package com.example.automation.steps;

import com.example.automation.pages.BasePage;
import com.example.automation.configuration.DriverFactory;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class HomeSteps extends BasePage {

    public HomeSteps() {
        super(DriverFactory.getDriver());
    }

    @Then("il est redirigé vers la page d'accueil")
    public void checkHomePage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }
}
