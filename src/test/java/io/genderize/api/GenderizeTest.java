package io.genderize.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GenderizeTest {
    @Test
    public void successfulGenderNameTest() {

        given().
        when().
              get("https://api.genderize.io/?name=Arun123").
        then().log().all().
              statusCode(200);
    }


}
