package kz.onlinebank.bot;

import kz.onlinebank.helper.BaseHelper;
import kz.onlinebank.helper.GetConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class ScreenBot {

    private static WebDriver driver;
    private static final String RESTR_URL = GetConfig.get("RESTR_URL");

    public static void main(String[] args) {

        BaseHelper.setUpDriver();
        driver = BaseHelper.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            BaseHelper.logIn();

            Thread.sleep(15000);
            driver.get(RESTR_URL);
            Thread.sleep(10000);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(), 'Ограничения')])[1]")))
                            .click();

            for (int i = 0; i < 342; i++) {
                if (i == 341) {
                    System.out.println("Reached to last 342nd element...");
                }

                // scroll to top if located at the bottom
                if (i != 0)
                    scrollToTop(js);
                // select checkbox
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@name='RestrictionsCheckboxGroup'])[1]")))
                                .click();
                Thread.sleep(100);
                // take screenshot of service
                saveScreenshot(driver, String.format("service-%d", i));

                // scroll to btn restrict
                scrollToRestBtn(js);
                // click btn restrict
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='SubmitButton' and @value='Запретить']")))
                                .click();
                Thread.sleep(200);
            }

            System.out.println("Job is done!");
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            BaseHelper.tearDownDriver();
        }
    }

    public static void scrollToRestBtn(JavascriptExecutor theJs) {
        theJs.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollToTop(JavascriptExecutor theJs) {
        theJs.executeScript("window.scrollTo(0, 0);");
    }

    public static void saveScreenshot(WebDriver driver, String name) {
        String filename = "screenshots/" + name + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(filename);

        try {
            Files.createDirectories(destFile.getParentFile().toPath());
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved to: " + destFile.getPath());
        }
        catch (IOException e) {
            System.err.println("!Failed to save screenshot: " + e.getMessage());
        }
    }
}
