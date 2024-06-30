package Core;

import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public interface ApiRequest {


     ApiBuilder.MethodType methodType();

     Object body();

     Map<String, String> headers();

     String basePath();

     String baseURL();

     ContentType contentType();

     HashMap<String,?> queryParams();


}
