package com.company.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private final WebDriver driver;

    private final WebDriverWait wait;

    private final By pageTitle =
            By.cssSelector(".title");

    private final By backpackAddButton =
            By.id("add-to-cart-sauce-labs-backpack");

    private final By cartBadge =
            By.cssSelector(".shopping_cart_badge");

    private final By cartLink =
            By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {

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
                                "Products"
                        )
        );
    }

    public String getTitle() {

        return driver.findElement(pageTitle)
                .getText();
    }

    public InventoryPage addBackpackToCart() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(
                                backpackAddButton
                        )
        ).click();

        return this;
    }

    public String getCartItemCount() {

        return driver.findElement(cartBadge)
                .getText();
    }

    public CartPage openCart() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(cartLink)
        ).click();

        return new CartPage(driver);
    }
}