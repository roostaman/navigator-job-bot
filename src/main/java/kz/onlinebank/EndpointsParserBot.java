package kz.onlinebank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EndpointsParserBot {

    public static WebDriver driver;
    protected static final String BASE_URL = "https://halyk.test.onlinebank.kz/navigator/";
    protected static final String ENDPOINTS_URL = "https://halyk.test.onlinebank.kz/navigator/onlinebank/security/OnlinebankUserRestrictionsPage";
    private static final String login = System.getenv("NAV_LOGIN");
    private static final String pass = System.getenv("NAV_PASS");

    public static void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-infobars", "--disable-extensions", "--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    public static void main(String[] args) {
        setUpDriver();

        try {
            driver.findElement(By.xpath("(//input[@class='loginTextFiled'])[1]"))
                    .sendKeys(login);

            driver.findElement(By.xpath("(//input[@class='loginTextFiled'])[2]"))
                    .sendKeys(pass);

            driver.findElement(By.xpath("//input[@type=\"submit\"]"))
                    .click();

            Thread.sleep(15000);

            driver.get(ENDPOINTS_URL);

            Thread.sleep(10000);

            List<WebElement> endpoints = driver.findElements(By.xpath("(//*[@id=\"ext-gen1009\"]/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr/td[5])[position() > 1]"));
            Thread.sleep(5000);

            List<String> result = new ArrayList<>();
            endpoints.stream()
                            .limit(342)
                                    .forEach(element -> result.add(element.getText()));

            saveData(result);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            tearDownDriver();
        }
    }

    public static void saveData(List<String> data) {
        try {
            Path filePath = Paths.get("src/main/resources/endpoints.txt");
            Files.write(filePath, data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
