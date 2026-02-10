package com.example.automation.configuration;

import com.example.automation.steps.ImportTokenXray;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;

public class Hooks {

    private static WebDriver driver;
    private static final ImportTokenXray importTokenXray = new ImportTokenXray();

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {

        if (scenario.isFailed() && driver != null) {
            TakesScreenshot ts = (TakesScreenshot) driver;

            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File("target/screenshots/" + scenario.getName() + ".png");
            FileUtils.copyFile(source, destination);

            scenario.attach(
                    ts.getScreenshotAs(OutputType.BYTES),
                    "image/png",
                    "Failure Screenshot"
            );
        }

        DriverFactory.quitDriver();
    }

    @AfterAll
    public static void publishToXray() throws Exception {
        importTokenXray.remonterXray();
        System.out.println("Résultats envoyés vers Xray ✔");
    }
}
