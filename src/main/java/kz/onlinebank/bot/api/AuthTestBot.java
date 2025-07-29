package kz.onlinebank.bot.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import kz.onlinebank.helper.GetConfig;

public class AuthTestBot {

    private static final String BASE_URL = GetConfig.get("BASE_URL");
    private static final String ACCESS_TOKEN = GetConfig.get("ACCESS_TOKEN");

    public static void main(String[] args) {

        checkWithToken();
    }

    public static void checkWithToken() {
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .header("Authorization", String.format("Bearer %s", ACCESS_TOKEN))
                .when()
                .head(GetConfig.get("SAMPLE_API"));

        System.out.printf("Response code - %s", response.getStatusCode());

    }
}
