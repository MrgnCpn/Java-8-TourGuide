package tourGuide;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import tourGuide.service.HTTPRequestService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHTTPRequestService {

    @Test
    public void testGetReq() throws IOException, JSONException {
        HTTPRequestService httpRequestService = new HTTPRequestService();
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("userId", UUID.randomUUID().toString());
        urlParams.put("attractionId", UUID.randomUUID().toString());

        JSONObject data = httpRequestService.getReq("http://localhost:8083/getAttractionRewardPoints", urlParams);

        assertThat(data.getInt("status")).isInstanceOf(Integer.class);
        assertThat(data.getInt("status")).isLessThanOrEqualTo(599);
        assertThat(data.getInt("status")).isGreaterThanOrEqualTo(200);
    }
}