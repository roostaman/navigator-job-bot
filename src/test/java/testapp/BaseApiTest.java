package testapp;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static RequestSpecification requestSpec;

//    @BeforeAll
//    public static void setup() {
//        RestAssured.baseURI = "https://beta.test.onlinebank.kz/";
//        requestSpec = RestAssured.given()
//                .auth().preemptive().basic("yourUsername", "yourPassword")
//                .contentType("application/json");
//    }

}
