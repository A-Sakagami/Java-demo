package api.weather;

import api.httprequest.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

@Service
public class WeatherService {
    public static ApiResult fetchWeather(String city) {
        try {
            String apiKey = GetAPIKEY.getOpenweatherApi();
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            Scanner scanner = new Scanner(url.openStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            
            // 結果を ApiResult にマッピング
            ApiResult result = new ApiResult();
            result.setName(jsonObject.get("name").getAsString());
            result.setTemperature(jsonObject.getAsJsonObject("main").get("temp").getAsDouble());
            result.setWindSpeed(jsonObject.getAsJsonObject("wind").get("speed").getAsDouble());
            result.setCloudiness(jsonObject.getAsJsonObject("clouds").get("all").getAsInt());
            result.setDescription(jsonObject.getAsJsonObject("coord").get("lat").getAsDouble() + ", " + jsonObject.getAsJsonObject("coord").get("lon").getAsDouble());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
