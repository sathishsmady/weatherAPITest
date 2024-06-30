package Data.ApiRequests;

import Core.ApiBuilder;
import Core.ApiRequest;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class WeatherForecastAPI extends WeatherAPI {

    public WeatherForecastAPI(HashMap<String, Object> queryParam) {
        super(queryParam);
    }

    @Override
    public String basePath() {
        return "data/2.5/forecast";
    }


}
