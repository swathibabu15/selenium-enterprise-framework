package com.company.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
//test
public class CheckoutPage {

    private final WebDriver driver;

    private final WebDriverWait wait;

    private final By firstName =
            By.id("first-name");

    private final By lastName =
            By.id("last-name");

    private final By postalCode =
            By.id("postal-code");

    private final By continueButton =
            By.id("continue");

    private final By finishButton =
            By.id("finish");

    private final By completeHeader =
            By.cssSelector(".complete-header");

    public CheckoutPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(10)
                );
    }

    public CheckoutPage enterCustomerDetails(
            String first,
            String last,
            String zip) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                firstName
                        )
        ).sendKeys(first);

        driver.findElement(lastName)
                .sendKeys(last);

        driver.findElement(postalCode)
                .sendKeys(zip);

        return this;
    }

    public CheckoutPage continueToOverview() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                continueButton
                        )
        ).click();

        return this;
    }

    public CheckoutPage finishOrder() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                finishButton
                        )
        ).click();

        return this;
    }

    public String getCompletionMessage() {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                completeHeader
                        )
        ).getText();
    }
}