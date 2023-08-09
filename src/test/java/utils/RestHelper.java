package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class RestHelper {
    static RequestSpecification request;
    Response response;

    public RestHelper() {
        request=given();
        RestAssured.baseURI = TestConfig.getEnvProperty("baseUrl");
        // Trust all hosts
        RestAssured.useRelaxedHTTPSValidation();
        // request = RestAssured.given().filter(new AllureRestAssured());

    }
    /**
     * Construct request headers.
     *
     * @param headerMap map of the headers
     */
    public void constructHeaders(final Map<String, String> headerMap) {
        for (String name : headerMap.keySet()) {
        request.headers(name, headerMap.get(name));
        }
    }
    /**
     * Construct Query Parameters
     *
     * @param queryParam map
     */
    public void constructQueryParameters(final Map<String,?> queryParam) {

        for (String name : queryParam.keySet()) {
            request.queryParam(name, queryParam.get(name));
        }
    }
    /**
     * Construct request body.
     *
     * @param requestBody
     */
    public void constructRequestBody(final Object requestBody) {
       request.body(requestBody);
    }
    /**
     * Send HTTP request.
     * /* @param httpMethod
     *
     * @param uriKey incoming uri
     */
    public Response sendRequest(final String requestType, final String uriKey) {

        HttpMethod method = (requestType.equals("get")) ? HttpMethod.GET
                : (requestType.equals("post"))? HttpMethod.POST
                : (requestType.equals("put")) ? HttpMethod.PUT
                : (requestType.equals("delete"))? HttpMethod.DELETE
                : (requestType.equals("patch")) ? HttpMethod.PATCH : HttpMethod.OPTIONS;

        String parameterUri = TestConfig.getEnvProperty(uriKey);
        request.log().all();


        switch (method) {
            case GET:
                response = request.filter(new AllureRestAssured()).when().get(baseURI + parameterUri);
                break;
            case PUT:
                response = request.filter(new AllureRestAssured()).when().put(baseURI + parameterUri);
                break;
            case POST:
                response = request.filter(new AllureRestAssured()).when().post(baseURI + parameterUri);
                break;
            case PATCH:
                response = request.filter(new AllureRestAssured()).when().patch(baseURI + parameterUri);
                break;
            case DELETE:
                response = request.filter(new AllureRestAssured()).when().delete(baseURI + parameterUri);
                break;
            case OPTIONS:
                response = request.filter(new AllureRestAssured()).when().options(baseURI + parameterUri);
                break;
            default:
                break;
        }

        response.then().log().all();
        return response;
    }
    public void jsonSchemaValidator(Response response, String schemaFile){
        InputStream jsonSchema = getClass().getClassLoader().getResourceAsStream(schemaFile);
        response.then().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }
}

/**
 * Enum class to hold all HTTP methods for RESTful web services.
 */
enum HttpMethod {
    /**
     * HTTP GET.
     */
    GET,

    /**
     * HTTP PUT.
     */
    PUT,

    /**
     * HTTP POST.
     */
    POST,

    /**
     * HTTP PATCH.
     */
    PATCH,

    /**
     * HTTP DELETE.
     */
    DELETE,

    /**
     * HTTP OPTIONS.
     */
    OPTIONS,
}