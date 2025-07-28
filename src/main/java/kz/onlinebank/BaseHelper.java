package kz.onlinebank;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseHelper {

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-infobars", "--disable-extensions", "--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
