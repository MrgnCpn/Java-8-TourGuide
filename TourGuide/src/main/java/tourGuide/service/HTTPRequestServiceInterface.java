package tourGuide.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * @author MorganCpn
 */
public interface HTTPRequestServiceInterface {
    /**
     * API reader, HTTP GET request
     * @param url
     * @param params
     * @return Content of API GET request
     * @throws IOException
     */
    JSONObject getReq(String url, Map<String, String> params) throws IOException, JSONException;
}
