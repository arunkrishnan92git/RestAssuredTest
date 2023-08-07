package io.genderize.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RestHelper;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GenderizeTest {
    @Test
    public void successfulGenderNameTest() {

        RestHelper restHelper = new RestHelper();
        Map<String,String> queryParam = new HashMap();
        queryParam.put("name","tendon");
        restHelper.constructQueryParameters(queryParam);

        Response response= restHelper.sendRequest("get","genderizeUri");
        Assert.assertEquals(response.statusCode(),200);
    }


}
