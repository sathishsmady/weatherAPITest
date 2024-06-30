package Data.ApiRequests;

import Core.ApiBuilder;
import Core.ApiRequest;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class WeatherAPI implements ApiRequest {


    String API_KEY = "beba02bbac55c1201eedc725f42f9be3";
    HashMap<String,Object> queryParam ;

    public WeatherAPI(HashMap<String, Object> queryParam) {
        queryParam.put("appid",API_KEY);
        this.queryParam = queryParam;
    }
    public WeatherAPI(HashMap<String, Object> queryParam,String apiKey) {
        queryParam.put("appid",apiKey);
        this.queryParam = queryParam;
    }

    @Override
    public ApiBuilder.MethodType methodType() {
        return ApiBuilder.MethodType.GET;
    }

    @Override
    public Object body() {
        return null;
    }

    @Override
    public Map<String, String> headers() {
        return new HashMap<>();
    }

    @Override
    public String basePath() {
        return "data/2.5/weather";
    }

    @Override
    public String baseURL() {
        return "https://api.openweathermap.org/";
    }

    @Override
    public ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    public HashMap<String, ?> queryParams() {
        return queryParam;
    }


}
