package com.company.automation.tests;

import com.company.automation.config.ConfigReader;
import com.company.automation.driver.DriverFactory;
import com.company.automation.pages.CartPage;
import com.company.automation.pages.CheckoutPage;
import com.company.automation.pages.InventoryPage;
import com.company.automation.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;
//test
public class SauceDemoTests
        extends BaseTest {

    // =========================================================
    // TEST 1 - Successful Login
    // =========================================================

    @Test(
            description = "Verify successful login",
            groups = {"smoke", "regression"}
    )
    public void verifySuccessfulLogin() {

        LoginPage loginPage =
                new LoginPage(
                        DriverFactory.getDriver()
                );

        InventoryPage inventoryPage =
                loginPage.login(
                        ConfigReader.get(
                                "standard.username"
                        ),
                        ConfigReader.get(
                                "standard.password"
                        )
                );

        Assert.assertTrue(
                inventoryPage.isDisplayed(),
                "Inventory page was not displayed"
        );

        Assert.assertEquals(
                inventoryPage.getTitle(),
                "Products"
        );
    }


    // =========================================================
    // TEST 2 - Invalid Login
    // =========================================================

    @Test(
            description = "Verify invalid login",
            groups = {"smoke", "regression"}
    )
    public void verifyInvalidLogin() {

        LoginPage loginPage =
                new LoginPage(
                        DriverFactory.getDriver()
                );

        loginPage.loginExpectingFailure(
                "invalid_user",
                "invalid_password"
        );

        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains(
                                "Username and password do not match"
                        ),
                "Expected error message not displayed"
        );
    }


    // =========================================================
    // TEST 3 - Add Product To Cart
    // =========================================================

    @Test(
            description = "Verify product can be added to cart",
            groups = {"smoke", "regression"}
    )
    public void verifyAddProductToCart() {

        LoginPage loginPage =
                new LoginPage(
                        DriverFactory.getDriver()
                );

        InventoryPage inventoryPage =
                loginPage.login(
                        ConfigReader.get(
                                "standard.username"
                        ),
                        ConfigReader.get(
                                "standard.password"
                        )
                );

        inventoryPage.addBackpackToCart();

        Assert.assertEquals(
                inventoryPage.getCartItemCount(),
                "1",
                "Cart should contain one product"
        );

        CartPage cartPage =
                inventoryPage.openCart();

        Assert.assertTrue(
                cartPage.isDisplayed(),
                "Cart page was not displayed"
        );

        Assert.assertEquals(
                cartPage.getProductName(),
                "Sauce Labs Backpack"
        );
    }


    // =========================================================
    // TEST 4 - Complete Checkout
    // =========================================================

    @Test(
            description = "Verify checkout can be completed",
            groups = {"regression"}
    )
    public void verifyCheckout() {

        LoginPage loginPage =
                new LoginPage(
                        DriverFactory.getDriver()
                );

        InventoryPage inventoryPage =
                loginPage.login(
                        ConfigReader.get(
                                "standard.username"
                        ),
                        ConfigReader.get(
                                "standard.password"
                        )
                );

        inventoryPage.addBackpackToCart();

        CartPage cartPage =
                inventoryPage.openCart();

        CheckoutPage checkoutPage =
                cartPage.checkout();

        checkoutPage
                .enterCustomerDetails(
                        "Test",
                        "User",
                        "560001"
                )
                .continueToOverview()
                .finishOrder();

        Assert.assertEquals(
                checkoutPage.getCompletionMessage(),
                "Thank you for your order!"
        );
    }
}