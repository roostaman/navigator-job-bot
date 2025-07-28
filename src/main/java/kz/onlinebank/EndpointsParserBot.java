package kz.onlinebank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class EndpointsParserBot {

    private static WebDriver driver;
    private static final String BASE_URL = "https://halyk.test.onlinebank.kz/navigator/";
    private static final String ENDPOINTS_URL = "https://halyk.test.onlinebank.kz/navigator/onlinebank/security/OnlinebankUserRestrictionsPage";
    private static final String login = System.getenv("NAV_LOGIN");
    private static final String pass = System.getenv("NAV_PASS");

    public static void main(String[] args) {
        BaseHelper.setUpDriver();

        driver = BaseHelper.getDriver();

        try {
            driver.get(BASE_URL);

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
            BaseHelper.tearDownDriver();
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
}
