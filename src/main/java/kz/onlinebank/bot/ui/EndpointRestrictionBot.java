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

public class EndpointRestrictionBot {

    private static final Integer ENDPOINTS_SIZE = 337;
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
                    System.out.printf("Reached to last %d-th element...", ENDPOINTS_SIZE);
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
                WebElement restrictBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='SubmitButton' and @value='Запретить']")));
                scrollToRestBtn(js);
                scrollToTheUpperElement(js, restrictBtn);
                // click btn restrict
                restrictBtn.click();

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
        EndpointUnlockerBot.saveScreenshot(driver, name);
    }
}
