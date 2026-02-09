package com.example.automation.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {

            // Choix du navigateur via Jenkins ou par défaut
            String browser = System.getProperty("browser", "CHROME").toUpperCase();

            try {
                switch (browser) {
                    case "CHROME":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--incognito");
                        chromeOptions.addArguments("--start-maximized");
                        chromeOptions.addArguments("--remote-allow-origins=*");
                        driver = new ChromeDriver(chromeOptions);

                        /*
                        // URL du Selenium Grid
                        driver = new RemoteWebDriver(
                                new URL("http://admin:admin@172.16.14.237:4444/wd/hub"),
                                chromeOptions
                        );

                         */
                        break;

                    case "FIREFOX":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.addArguments("-private");
                        driver = new RemoteWebDriver(
                                new URL("http://admin:admin@172.16.14.237:4444/wd/hub"),
                                firefoxOptions
                        );
                        break;

                    case "EDGE":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        driver = new RemoteWebDriver(
                                new URL("http://admin:admin@172.16.14.237:4444/wd/hub"),
                                edgeOptions
                        );
                        break;

                    default:
                        throw new RuntimeException("Navigateur non supporté: " + browser);
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException("URL Grid invalide", e);
            }

            // Timeout global
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
