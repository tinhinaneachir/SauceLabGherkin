package com.example.automation.configuration;

import com.example.automation.steps.ImportTokenXray;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;
import org.slf4j.LoggerFactory;


public class Hooks {

    //private static final Logger logger = (Logger) LoggerFactory.getLogger(ImportTokenXray.class);
    WebDriver driver = DriverFactory.getDriver();
    public static ImportTokenXray importTokenXray = new ImportTokenXray();


    @Before
    public void setUp() {
        DriverFactory.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) throws IOException, NoSuchAlgorithmException, KeyStoreException, InterruptedException, KeyManagementException {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File("target/screenshots/" + scenario.getName() + ".png");
            FileUtils.copyFile(source, destination);


            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }

        DriverFactory.quitDriver();
    }

    /*


    @AfterClass
    public static void remonterXray() throws Exception {
        importTokenXray.remonterXray();
        //logger.info("L'exucution a fonctionné!!!");
        System.out.println("Le test passe par la!!!");

    }
    */

}
