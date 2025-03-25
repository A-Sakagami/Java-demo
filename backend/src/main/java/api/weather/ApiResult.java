package api.weather;

/**
 * APIの結果を格納するクラス
 * @author @A-Sakagami
 * @param weatherIcon 天気アイコン
 * @param weatherIconUrl 天気アイコンのURL
 * @param weatherDescription 天気の説明
 * @param cityName 場所名
 * @param country 国名
 * @param temperature 気温
 * @param windSpeed 風速
 * @param cloudiness 降水確率
 * @param description 緯度・経度の座標
 */
public class ApiResult {
    private String weatherIcon;
    private String weatherIconUrl;
    private String weatherDescription;


    private String cityName;
    private String country;
    private double temperature;
    private double windSpeed;
    private int cloudiness;
    private String description;
    
    /** 
     * コンストラクタ
    */
    public ApiResult() {}

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getName() {
        return cityName;
    }

    public void setName(String name) {
        this.cityName = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    public String getDescription() {
        return addDirection(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 緯度、経度に東西南北を追加する
     * @param description 緯度、経度
     * @param latresult 緯度
     * @param lonresult 経度
     * @return 緯度、経度に東西南北を追加した文字列
     */
    private String addDirection(String description) {
        String latresult = "";
        String lonresult = "";
        if (description != null && !description.isEmpty()) {
            double lat = Double.parseDouble(description.split(",")[0].trim());
            double lon = Double.parseDouble(description.split(",")[1].trim());
            
            if (lat > 0) {
                latresult = "北緯 " + lat + "度, ";
            } else {
                latresult += "南緯 " + lat + "度, ";
            }

            if (lon > 0) {
                lonresult = "東経 " + lon + "度";
            } else {
                lonresult = "西経 " + lon + "度";
            }
        } else {
            return "座標情報がありません。";
        }
        return latresult + lonresult;
    }
    
}
