package util;

import driver.DriverSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class TestListener implements ITestListener {
    private Logger log = LogManager.getRootLogger();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Started test case is "+ iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test case passed is "+iTestResult.getName());
    }

    public void onTestFailure(ITestResult iTestResult){
        System.out.println("Test case failed is "+iTestResult.getName());
        saveScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test case skipped is :"+iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test case passed with failure is "+iTestResult.getName());
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("Start all!");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Finish all!");
    }

    private void saveScreenshot() {
        File screenCapture = ((TakesScreenshot) DriverSingleton
                .getDriver())
                .getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenCapture,
                    new File(String.format(".//target/screenshots/%s%s",getCurrentTime(), ".png")));
        } catch (IOException ex){
            log.error("Failed to save screenshot: " + ex.getMessage());
        }
    }

    private String getCurrentTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
        return Instant.now().atZone(ZoneId.systemDefault()).format(formatter);
    }
}