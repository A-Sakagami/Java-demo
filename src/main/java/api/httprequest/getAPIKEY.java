package api.httprequest;

import io.github.cdimascio.dotenv.Dotenv;

public class getAPIKEY {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getOpenweatherApi() {
        String apiKey = dotenv.get("OPEN_WEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("APIキーが設定されていません。'.env' ファイルに 'OPEN_WEATHER_API_KEY' を追加してください。");
        }
        return apiKey;
    }
}

