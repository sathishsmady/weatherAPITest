package Scripts.ApiTestCases;

import Core.ApiExecutor;
import Core.ApiResponse;
import Data.ApiRequests.WeatherAPI;
import Data.ApiRequests.WeatherForecastAPI;
import Scripts.TestBase;
import Util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static Util.DateUtil.formatter;


public class WeatherAPITests extends TestBase {

    Logger logger = LogManager.getLogger(WeatherAPITests.class);

    String cityName = "Chennai";
    String invalidCityName = "Sathish";


    @Test(description = "Validate Weather API functionality for a city")
    public void weatherTest() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("q", cityName);

        WeatherAPI weatherAPI = new WeatherAPI(map);

        ApiResponse response = new ApiExecutor(weatherAPI).execute();

        logger.info(response.body().asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);

        Assert.assertEquals(response.body().jsonPath().get("name"), cityName);
        Assert.assertEquals(response.body().jsonPath().get("sys.country"), "IN");


        Assert.assertNotNull(response.body().jsonPath().get("main.temp"));
        Assert.assertNotNull(response.body().jsonPath().get("main.humidity"));
        Assert.assertNotNull(response.body().jsonPath().get("main.temp_min"));
        Assert.assertNotNull(response.body().jsonPath().get("main.temp_max"));
        Assert.assertNotNull(response.body().jsonPath().get("main.pressure"));


        Assert.assertNotNull(response.body().jsonPath().get("wind.speed"));
        Assert.assertNotNull(response.body().jsonPath().get("wind.deg"));
        Assert.assertNotNull(response.body().jsonPath().get("sys.sunrise"));
        Assert.assertNotNull(response.body().jsonPath().get("sys.sunset"));

    }

    @Test(description = "Validate 5 days forecast for a city")
    public void weatherForecastTest() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("q", cityName);

        WeatherForecastAPI weatherForecastAPI = new WeatherForecastAPI(map);

        ApiResponse response = new ApiExecutor(weatherForecastAPI).execute();

        logger.info(response.body().asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.body().jsonPath().get("city.name"), cityName);
        Assert.assertEquals(response.body().jsonPath().get("city.country"), "IN");

        String temp = response.body().jsonPath().get("list[0].dt_txt").toString();

        StringBuilder currentTime = new StringBuilder(temp);

        /**
         * Adding a logic to validate the forecast should be only next 5 days
         */

        Assert.assertEquals(response.body().jsonPath().getList("list").size(), 40);

        for (int i = 0; i < response.body().jsonPath().getList("list").size(); i++) {

            logger.info("currentTime :  " + currentTime);


            Assert.assertEquals(response.body().jsonPath().get("list[" + i + "].dt_txt").toString(), currentTime.toString());

            Assert.assertNotNull(response.body().jsonPath().getString("list[" + i + "].main.temp"));
            Assert.assertNotNull(response.body().jsonPath().getString("list[" + i + "].main.pressure"));
            Assert.assertNotNull(response.body().jsonPath().getString("list[" + i + "].main.humidity"));

            Assert.assertNotNull(response.body().jsonPath().getString("list[" + i + "].wind.speed"));
            Assert.assertNotNull(response.body().jsonPath().getString("list[" + i + "].wind.gust"));

            LocalDateTime datetime = LocalDateTime.parse(currentTime.toString(), formatter);

            LocalDateTime d2 = DateUtil.addHours(datetime, 3);

            currentTime = new StringBuilder(d2.format(formatter));

        }


    }

    @Test(description = "Validate when we pass invalid city")
    public void negativeValidation1() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("q", invalidCityName);

        WeatherAPI weatherAPI = new WeatherAPI(map);

        ApiResponse response = new ApiExecutor(weatherAPI).execute();

        logger.info(response.body().asPrettyString());

        Assert.assertEquals(response.body().jsonPath().getString("message"), "city not found");
        Assert.assertEquals(response.body().jsonPath().getString("cod"), "404");

        Assert.assertEquals(response.statusCode(), 404);

    }

    @Test(description = "Validate when we pass invalid API Key")
    public void negativeValidation2() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("q", invalidCityName);

        WeatherAPI weatherAPI = new WeatherAPI(map, UUID.randomUUID().toString());


        ApiResponse response = new ApiExecutor(weatherAPI).execute();

        logger.info(response.body().asPrettyString());

        Assert.assertEquals(response.statusCode(), 401);

        Assert.assertEquals(response.body().jsonPath().getString("message"), "Invalid API key. Please see https://openweathermap.org/faq#error401 for more info.");
        Assert.assertEquals(response.body().jsonPath().getString("cod"), "401");


    }


}
