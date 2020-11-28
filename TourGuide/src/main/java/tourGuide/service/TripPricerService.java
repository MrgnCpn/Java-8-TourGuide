package tourGuide.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tourGuide.models.Provider;

import java.io.IOException;
import java.util.*;

public class TripPricerService extends ExternalApiService {

    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("TripPricerService");

    /**
     * Constructor
     * @param httpRequestService
     * @param configurationFilePath
     */
    public TripPricerService(HTTPRequestService httpRequestService, String configurationFilePath) {
        super(httpRequestService, configurationFilePath);
    }

    /**
     * Get user price plan from TripPricer API
     * @param apiKey
     * @param attractionId
     * @param adults
     * @param children
     * @param nightsStay
     * @param rewardsPoints
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public List<Provider> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay, int rewardsPoints) throws JSONException, IOException {
        List<Provider> result = null;
        String url = super.getApiServerUrl("trippricer.host") + "/getPrice";

        Map<String, String> postParams = new HashMap<>();
        postParams.put("apiKey", apiKey);
        postParams.put("attractionId", attractionId.toString());
        postParams.put("adults", String.valueOf(adults));
        postParams.put("children", String.valueOf(children));
        postParams.put("nightsStay", String.valueOf(nightsStay));
        postParams.put("rewardsPoints", String.valueOf(rewardsPoints));

        JSONObject data = super.httpRequestService.postFormReq(url, postParams);
        if (data != null) {
            result = new ArrayList<>();
            Integer status = data.getInt("status");
            if (status < 299) {
                JSONArray content = (JSONArray) data.get("content");
                for (int i = 0; i < content.length(); i++) {
                    JSONObject providerJson = content.getJSONObject(i);
                    Provider provider = new Provider(
                        UUID.fromString(providerJson.getString("tripId")),
                        providerJson.getString("name"),
                        providerJson.getDouble("price")
                    );
                    result.add(provider);
                }
            } else {
                logger.error("Api Request Error : " + url + ", status : " + status);
            }
        } else {
            logger.error("Api Request Error : " + url + ", no data");
        }
        return result;
    }
}
