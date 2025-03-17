package api.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @GetMapping("hello")
    public String hello() {
        return "Hello, World!";
    }
    

    @GetMapping("/city")
    public ApiResult getWeather(@RequestParam String city) {
        return WeatherService.fetchWeather(city);
    }
}
