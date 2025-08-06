package kz.onlinebank.bot.ui;

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

public class EndpointUnlockerBot {

    private static final Integer ENDPOINTS_SIZE = 342;
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

            scrollToRestBtn(js);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(text(), 'Ограничения')])[74]")))
                            .click();

            for (int i = 0; i < ENDPOINTS_SIZE; i++) {
                if (i == (ENDPOINTS_SIZE - 1)) {
                    System.out.printf("Reached to last %d-th element...%n", ENDPOINTS_SIZE);
                }

                // scroll to last service's checkbox and select
                WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@name='RestrictionsCheckboxGroup'])[last()]")));
                WebElement allowBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='SubmitButton' and @value='Разрешить']")));

                scrollToRestBtn(js);
                scrollToTheUpperElement(js, allowBtn);
                Thread.sleep(100);
                checkbox.click();

                // take screenshot of service
//                saveScreenshot(driver, String.format("service-%d", i));

                // click btn allow
                allowBtn.click();
                Thread.sleep(200);
            }

            System.out.println("Job is done!");
        }
        catch (Exception e) {
            throw new RuntimeException("Error occurred", e);
        }
        finally {
            BaseHelper.tearDownDriver();
        }
    }

    public static void scrollToTheUpperElement(JavascriptExecutor theJs, WebElement theElement) {
        theJs.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", theElement);
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
