package api.weather;

import api.httprequest.HttpClientService;
import api.utilities.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

public class OpenWeatherRequestCatch {
    public static void main(String[] args) throws Exception {
        System.out.println("DEBUG: プログラム開始");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city: ");
        String city = scanner.nextLine().trim();
        System.out.println("DEBUG: 入力された都市名: " + city);
        scanner.close();

        // APIのURLを生成
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + api.httprequest.getAPIKEY.getOpenweatherApi() + "&units=metric";

        HttpClientService httpClientService = new HttpClientService(HttpClients.createDefault());

        try {
            String jsonResponse = httpClientService.sendRequest(url);
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            // 整形しないver
            System.out.println("DEBUG: APIからのレスポンス: " + jsonResponse);
            // 整形して表示
            System.out.println("🌍 都市名: " + jsonObject.get("name").getAsString());
            System.out.println(countryFlagConverter.getFlagEmoji(jsonObject.getAsJsonObject("sys").get("country").getAsString()));
            System.out.println("🌡️ 気温(°C): " + jsonObject.getAsJsonObject("main").get("temp").getAsDouble() + "°C");
            System.out.println("💨 風速: " + jsonObject.getAsJsonObject("wind").get("speed").getAsDouble() + " m/s");
            System.out.println("☁️ 雲量: " + jsonObject.getAsJsonObject("clouds").get("all").getAsInt() + "%");
            System.out.println("📍 座標: " + jsonObject.getAsJsonObject("coord").get("lat").getAsDouble() + ", " + jsonObject.getAsJsonObject("coord").get("lon").getAsDouble());

        } catch (Exception e) {
            System.out.println("Error retrieving weather data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
