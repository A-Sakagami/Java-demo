package api.httprequest;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;

public class HttpClientService {
    private HttpClient httpClient;

    public HttpClientService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String sendRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
}
