package kz.onlinebank.bot.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import kz.onlinebank.helper.GetConfig;

import java.io.File;

public class AuthTestBot {

    private static final String BASE_URL = GetConfig.get("BASE_URL");
    private static final String ACCESS_TOKEN = GetConfig.get("ACCESS_TOKEN");

    public static void main(String[] args) {
        checkWithToken();
//        checkWithBodyAndToken();
    }

    public static void checkWithToken() {
        long sentDocId = 47809326068L;

        String endpoint = GetConfig.get("SAMPLE_API");

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
                .queryParam("sentDocId", sentDocId)
                .when()
                .get("/api/" + endpoint);

        System.out.println("Эндпоинт: " + BASE_URL + "/api/" + endpoint);
        System.out.println("Код статуса: " + response.statusCode());
        System.out.println("Response: " + response.asPrettyString());
        System.out.println("------");

    }

    public static void checkWithBodyAndToken() {
        String endpoint = GetConfig.get("SAMPLE_API");

        String dtoJson = "{ \"currency\": \"USD\", \"amount\": 1000, \"receiver\": \"John Doe\" }";
        File dummyFile = new File("src/main/resources/dummy.txt");

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
                .multiPart("dto", "dto.json", dtoJson, "application/json")
                .multiPart("files", dummyFile)
                .when()
                .post("/api/" + endpoint)
                .then()
                .log().all()
                .extract().response();

        System.out.println("Эндпоинт: " + BASE_URL + "/api/" + endpoint);
        System.out.println("Код статуса: " + response.statusCode());
        System.out.println("Response: " + response.asPrettyString());
        System.out.println("------");
    }
}
