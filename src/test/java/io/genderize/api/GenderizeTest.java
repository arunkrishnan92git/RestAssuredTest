package io.genderize.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Gender;
import utils.RestHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class GenderizeTest {
    RestHelper restHelper;
    ObjectMapper mapper;
    @DataProvider(name = "successQueryParamValues")
    public Object[][] successQueryParamData(){
        return new Object[][]{
                {"Patrick","male"},{"Nancy","female"},{"123",null},{"Q123",null},{"!@#$",null}
        };
    }
    @Epic("Genderize API Implementation")
    @Story("Probability of Gender API")
    @Link(value = "https://genderize.io/")
    @Description("This is a data driven test that verifies various success scenarios")

    @Test(dataProvider = "successQueryParamValues")
    public void successfulGenderNameTest(String paramValue,String gender) {
        restHelper = new RestHelper();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name",paramValue);
        restHelper.constructQueryParameters(queryParam);

        Response response = restHelper.sendRequest("get", "genderizeUri");
        Assert.assertEquals(response.statusCode(), 200);
        Gender actualResponse = response.getBody().as(Gender.class);
        Assert.assertEquals(actualResponse.getGender(),gender);
        restHelper.jsonSchemaValidator(response,"genderSchema.json");
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
        mapper = new ObjectMapper();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name", "");
        restHelper.constructQueryParameters(queryParam);
        Response response = restHelper.sendRequest("get", "genderizeUri");
        response.then().statusCode(200);
        Gender actualResponse = response.getBody().as(Gender.class);



        try {
            Gender expectedResponse = mapper.readValue(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\expectedResults\\nullQueryParameterResponse.json"), Gender.class);
            assertThat(actualResponse).isEqualToComparingFieldByField(expectedResponse);
        } catch (IOException e) {
           Assert.fail("Expected File not found");
        }
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
