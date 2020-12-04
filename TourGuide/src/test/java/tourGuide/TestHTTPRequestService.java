package tourGuide;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import tourGuide.service.HTTPRequestService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHTTPRequestService {

    @Test
    public void testGetReq() throws IOException, JSONException {
        HTTPRequestService httpRequestService = new HTTPRequestService();
        Map<String, String> params = new HashMap<>();
        params.put("fields", "name");

        JSONObject data = httpRequestService.getReq("https://restcountries.eu/rest/v2/alpha/FRA", params);
        JSONObject content = (JSONObject) data.get("content");


        assertThat(data.getInt("status")).isInstanceOf(Integer.class);
        assertThat(data.getInt("status")).isLessThanOrEqualTo(599);
        assertThat(data.getInt("status")).isGreaterThanOrEqualTo(200);

        assertThat(content.getString("name").toLowerCase()).isGreaterThanOrEqualTo("france");
    }
}