package kz.onlinebank.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseHelper {

    private static WebDriver driver;
    private static final String NAV_BASE_URL = GetConfig.get("NAV_BASE_URL");
    private static final String NAV_LOGIN = GetConfig.get("NAV_LOGIN");
    private static final String NAV_PASS = GetConfig.get("NAV_PASS");

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

    public static void logIn() {
        driver.get(NAV_BASE_URL);

        driver.findElement(By.xpath("(//input[@class='loginTextFiled'])[1]"))
                .sendKeys(NAV_LOGIN);
        driver.findElement(By.xpath("(//input[@class='loginTextFiled'])[2]"))
                .sendKeys(NAV_PASS);
        driver.findElement(By.xpath("//input[@type=\"submit\"]"))
                .click();
    }
}
