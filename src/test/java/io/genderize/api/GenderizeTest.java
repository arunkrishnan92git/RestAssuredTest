package io.genderize.api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.RestHelper;

import java.util.HashMap;
import java.util.Map;


public class GenderizeTest {
    RestHelper restHelper;
    @DataProvider(name = "successQueryParamValues")
    public Object[][] successQueryParamData(){
        return new Object[][]{
                {"Meena","female",23},{"Bangalore","male",29},{"Chennai","female",23},{"!@#$","null",23}
        };
    }

    @Test(dataProvider = "successQueryParamValues")
    public void successfulGenderNameTest(String paramValue,String gender, int age) {
        restHelper = new RestHelper();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name",paramValue);
        restHelper.constructQueryParameters(queryParam);

        Response response = restHelper.sendRequest("get", "genderizeUri");
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void errorQueryParameterMissingTest() {
        restHelper = new RestHelper();
        Response response = restHelper.sendRequest("get", "genderizeUri");
        Assert.assertEquals(response.statusCode(), 422);
    }

    @Test
    public void errorQueryParameterValueMissingTest() {
        restHelper = new RestHelper();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name", "");
        restHelper.constructQueryParameters(queryParam);
        Response response = restHelper.sendRequest("get", "genderizeUri");
        response.then().statusCode(200);
    }

    @Test
    public void invalidQueryParameterTest() {
        restHelper = new RestHelper();
        Map<String,Integer> queryParam = new HashMap<String, Integer>();
        queryParam.put("name", 123);
        restHelper.constructQueryParameters(queryParam);
        Response response = restHelper.sendRequest("get", "genderizeUri");
        response.then().statusCode(422);
    }

}
