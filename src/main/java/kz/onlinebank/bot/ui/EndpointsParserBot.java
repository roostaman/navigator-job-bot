package kz.onlinebank.bot.ui;

import kz.onlinebank.helper.BaseHelper;
import kz.onlinebank.helper.GetConfig;
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
    private static final String ENDPOINTS_URL = GetConfig.get("ENDPOINTS_URL");

    public static void main(String[] args) {
        BaseHelper.setUpDriver();

        driver = BaseHelper.getDriver();

        try {
            BaseHelper.logIn();
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
