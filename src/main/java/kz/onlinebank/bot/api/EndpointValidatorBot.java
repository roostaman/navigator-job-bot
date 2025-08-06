package kz.onlinebank.bot.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import kz.onlinebank.helper.GetConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EndpointValidatorBot {

    private static final String BASE_URL = GetConfig.get("BASE_URL");
    private static final String ACCESS_TOKEN = GetConfig.get("ACCESS_TOKEN");

    public static void main(String[] args) {

//        checkEndpointWithBody();

        checkEndpoint();
    }

    public static List<String> getEndpointsData(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException ex) {
            System.out.println("Exception occurred while reading post-endpoints.txt");
        }
        return lines;
    }


    public static void checkEndpoint() {
        List<String> endpoints = getEndpointsData("src/main/resources/post-endpoints.txt");

        System.out.println("=== Отчет по ограничениям эндпоинтов ===");
        System.out.println("------");

        for (String endpoint : endpoints) {
            Response response = RestAssured.given()
                    .baseUri(BASE_URL)
                    .header("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
                    .when()
                    .post("/api/" + endpoint);

            System.out.println("Эндпоинт: " + endpoint);
            System.out.println("Код статуса: " + response.statusCode());
            System.out.println("Запрос: " + response.asPrettyString());
            System.out.println("------");
        }
    }


    public static void checkEndpointWithBody() {
        List<String> endpoints = getEndpointsData("src/main/resources/post-body-endpoints.txt");

        String dtoJson = "{ \"currency\": \"USD\", \"amount\": 1000, \"receiver\": \"John Doe\" }";
        File dummyFile = new File("src/main/resources/dummy.txt");

        System.out.println("=== Отчет по ограничениям эндпоинтов ===");

        for (String endpoint : endpoints) {
            Response response = RestAssured.given()
                    .baseUri(BASE_URL)
                    .header("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
                    .multiPart("dto", "dto.json", dtoJson, "application/json")
                    .multiPart("files", dummyFile)
                    .when()
                    .post("/api/" + endpoint);

            System.out.println("\n------");
            System.out.println("Эндпоинт: " + endpoint);
            System.out.println("Код статуса: " + response.statusCode());
            System.out.println("Запрос: " + response.asPrettyString());
            System.out.println("------");
        }
    }
}
