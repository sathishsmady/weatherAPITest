package Core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class ApiBuilder {

    private final RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
    private MethodType method;

    public ApiBuilder() {
    }

    public MethodType getMethod() {
        return this.method;
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public RequestSpecBuilder getRequestSpecBuilder() {
        return this.requestSpecBuilder;
    }

    public Response execute() {
        RequestSpecification requestSpecification = this.requestSpecBuilder.build();
        RestAssured.defaultParser = Parser.JSON;
        RestAssuredConfig config = RestAssured.config();

        Response response;
        switch (this.method) {
            case GET:
                response = RestAssured.given().config(config).relaxedHTTPSValidation().spec(requestSpecification).when().get();
                break;
            case POST:
                response = RestAssured.given().config(config).relaxedHTTPSValidation().spec(requestSpecification).when().post();
                break;
            case PUT:
                response = RestAssured.given().config(config).relaxedHTTPSValidation().spec(requestSpecification).when().put();
                break;
            case DELETE:
                response = RestAssured.given().config(config).relaxedHTTPSValidation().spec(requestSpecification).when().delete();
                break;
            case PATCH:
                response = RestAssured.given().config(config).relaxedHTTPSValidation().spec(requestSpecification).when().patch();
                break;
            default:
                throw new RuntimeException("API method not specified");
        }

        return response;
    }

    public enum MethodType {
        POST,
        GET,
        PUT,
        DELETE,
        PATCH;

        MethodType() {
        }
    }
}
