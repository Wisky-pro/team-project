package API;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import org.json.JSONObject;


public abstract class Fetcher {
    protected static final HttpClient client = HttpClient.newHttpClient();
    protected String productURL;
    protected double price;

    protected abstract HttpResponse<String> sendRequest();

    public void updateJson() {}

    protected abstract JSONObject formatData(JSONObject responseJson);

}
