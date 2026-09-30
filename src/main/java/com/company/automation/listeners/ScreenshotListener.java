package com.company.automation.listeners;

import com.company.automation.driver.DriverFactory;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotListener
        implements ITestListener {

    @Override
    public void onTestFailure(
            ITestResult result) {

        try {

            Path directory =
                    Path.of(
                            "target",
                            "screenshots"
                    );

            Files.createDirectories(
                    directory
            );

            File source =
                    ((TakesScreenshot)
                            DriverFactory.getDriver())
                            .getScreenshotAs(
                                    OutputType.FILE
                            );

            File destination =
                    directory
                            .resolve(
                                    result.getName()
                                            + "_"
                                            + System.currentTimeMillis()
                                            + ".png"
                            )
                            .toFile();

            FileUtils.copyFile(
                    source,
                    destination
            );

        } catch (Exception ignored) {

            // Don't hide the original test failure.
        }
    }
}