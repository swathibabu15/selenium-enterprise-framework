package com.company.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;

    private final WebDriverWait wait;

    private final By pageTitle =
            By.cssSelector(".title");

    private final By backpackItem =
            By.id("item_4_title_link");

    private final By checkoutButton =
            By.id("checkout");

    public CartPage(WebDriver driver) {

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(10)
                );
    }

    public boolean isDisplayed() {

        return wait.until(
                ExpectedConditions
                        .textToBePresentInElementLocated(
                                pageTitle,
                                "Your Cart"
                        )
        );
    }

    public String getProductName() {

        return driver.findElement(backpackItem)
                .getText();
    }

    public CheckoutPage checkout() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                checkoutButton
                        )
        ).click();

        return new CheckoutPage(driver);
    }
}