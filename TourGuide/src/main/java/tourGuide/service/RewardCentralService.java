package tourGuide.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RewardCentralService extends ExternalApiService {

    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("RewardCentralService");

    /**
     * Constructor
     * @param httpRequestService
     * @param configurationFilePath
     */
    public RewardCentralService(HTTPRequestService httpRequestService, String configurationFilePath) {
        super(httpRequestService, configurationFilePath);
    }

    /**
     * Get Attraction reward points from external reward central api
     * @param attractionId
     * @param userId
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public int getAttractionRewardPoints(UUID attractionId, UUID userId) throws IOException, JSONException {
        int result = 0;
        String url = super.getApiServerUrl("rewardcentral.host") + "/getAttractionRewardPoints";

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("userId", userId.toString());
        urlParams.put("attractionId", attractionId.toString());
        JSONObject data = super.httpRequestService.getReq(url, urlParams);
        if (data != null) {
            Integer status = data.getInt("status");
            if (status < 299) {
                result = data.getInt("content");
            } else {
                logger.error("Api Request Error : " + url + ", status : " + status);
            }
        } else {
            logger.error("Api Request Error : " + url + ", no data");
        }
        return result;
    }
}
