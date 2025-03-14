package api.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @GetMapping("/api/weather")
    public ApiResult getWeather(@RequestParam String city) {
        return WeatherService.fetchWeather(city);
    }
}
