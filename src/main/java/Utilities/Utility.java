package Utilities;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Utility {
    private static final String SCREENSHOTS_PATH = "test-outputs/Screenshots/";

    public static void clickingOnElement (WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public static void sendData(WebDriver driver, By locator, String data){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(data);
    }

    public static String getText (WebDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    public static void selectingFromDropDown (WebDriver driver, By locator, String option){
        new Select(findWebElement(driver, locator)).selectByVisibleText(option);
    }

    public static WebDriverWait generalWait (WebDriver driver){
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public static void scrolling (WebDriver driver, By locator){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",findWebElement(driver, locator));
    }

    public static WebElement findWebElement(WebDriver driver, By locator){
        return driver.findElement(locator);
    }

    public static void takeScreenShot (WebDriver driver, String screenshotName){
        try{
            // Capture screen shot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshotFile
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName+"-"+getTimeStamp()+".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // Attach the screenshot to Allure
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotFile.getPath())));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getTimeStamp (){
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }
}
