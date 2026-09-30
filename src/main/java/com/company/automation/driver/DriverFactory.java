package com.company.automation.driver;

import com.company.automation.config.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void createDriver() {

        String browser =
                ConfigReader.get("browser");

        boolean headless =
                ConfigReader.getBoolean("headless");

        WebDriver driver;

        if ("chrome".equalsIgnoreCase(browser)) {

            ChromeOptions options =
                    new ChromeOptions();

            if (headless) {
                options.addArguments("--headless=new");
            }

            options.addArguments(
                    "--window-size=1920,1080"
            );

            options.addArguments(
                    "--disable-notifications"
            );

            driver = new ChromeDriver(options);

        } else if ("firefox".equalsIgnoreCase(browser)) {

            FirefoxOptions options =
                    new FirefoxOptions();

            if (headless) {
                options.addArguments("--headless");
            }

            driver = new FirefoxDriver(options);

        } else {

            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        }

        DRIVER.set(driver);

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ZERO
                );

        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        Duration.ofSeconds(30)
                );
    }

    public static WebDriver getDriver() {

        WebDriver driver = DRIVER.get();

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized"
            );
        }

        return driver;
    }

    public static void quitDriver() {

        WebDriver driver = DRIVER.get();

        if (driver != null) {

            driver.quit();

            DRIVER.remove();
        }
    }
}