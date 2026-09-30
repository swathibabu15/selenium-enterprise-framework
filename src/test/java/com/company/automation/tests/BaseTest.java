package com.company.automation.tests;

import com.company.automation.driver.DriverFactory;
import com.company.automation.pages.LoginPage;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(
        com.company.automation.listeners
                .ScreenshotListener.class
)
public abstract class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        DriverFactory.createDriver();

        new LoginPage(
                DriverFactory.getDriver()
        ).open();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}