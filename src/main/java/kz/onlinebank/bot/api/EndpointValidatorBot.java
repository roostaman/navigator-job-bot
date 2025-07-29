package kz.onlinebank.bot.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import kz.onlinebank.helper.GetConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EndpointValidatorBot {

    private static final String BASE_URL = GetConfig.get("BASE_URL");
    private static final String ACCESS_TOKEN = GetConfig.get("ACCESS_TOKEN");

    public static void main(String[] args) {
        List<String> endpoints = getEndpointsData();

        System.out.println("=== Endpoint Decommissioning Report ===");

        for (String endpoint : endpoints) {
            Response response = RestAssured.given()
                    .baseUri(BASE_URL)
                    .header("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
                    .when()
                    .head("/api/" + endpoint);

            System.out.println("Эндпоинт: " + BASE_URL + "/api/" + endpoint);
            System.out.println("Код статуса: " + response.statusCode());
            System.out.println("------");
//            break;
        }
    }

    public static List<String> getEndpointsData() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("src/main/resources/endpoints.txt"));
        } catch (IOException ex) {
            System.out.println("Exception occurred while reading endpoints.txt");
        }
        return lines;
    }

}
