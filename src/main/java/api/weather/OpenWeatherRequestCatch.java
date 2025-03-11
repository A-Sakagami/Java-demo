package api.weather;

import api.httprequest.HttpClientService;
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

            // 整形して表示
            System.out.println("🌍 City: " + jsonObject.get("name").getAsString());
            System.out.println("🌡️ Temperature: " + jsonObject.getAsJsonObject("main").get("temp").getAsDouble() + "°C");
            System.out.println("💨 Wind Speed: " + jsonObject.getAsJsonObject("wind").get("speed").getAsDouble() + " m/s");
            System.out.println("☁️ Cloud Coverage: " + jsonObject.getAsJsonObject("clouds").get("all").getAsInt() + "%");
            System.out.println("📍 Coordinates: " + jsonObject.getAsJsonObject("coord").get("lat").getAsDouble() + ", " + jsonObject.getAsJsonObject("coord").get("lon").getAsDouble());

        } catch (Exception e) {
            System.out.println("Error retrieving weather data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
