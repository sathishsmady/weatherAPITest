package Core;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {


    private Response response = null;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public ResponseBody body() {
        return this.response.body();
    }

    public String content() {
        return this.response.contentType();
    }

    public int statusCode() {
        return this.response.statusCode();
    }

    public Response response() {
        return this.response;
    }

    public Map<String, String> headers() {
        Map<String, String> s = new HashMap();
        this.response.headers().asList().forEach((header) -> {
            s.put(header.getName(), header.getValue());
        });
        return s;
    }

    public ValidatableResponse validatableResponse() {
        return (ValidatableResponse)this.response.then();
    }

}
