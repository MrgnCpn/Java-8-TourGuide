package tourGuide.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tourGuide.models.Attraction;
import tourGuide.models.Location;
import tourGuide.models.VisitedLocation;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class GpsUtilService extends ExternalApiService {

    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("GpsUtilService");

    /**
     * Constructor
     * @param httpRequestService
     * @param configurationFilePath
     */
    public GpsUtilService(HTTPRequestService httpRequestService, String configurationFilePath) {
        super(httpRequestService, configurationFilePath);
    }

    /**
     * Get User Location
     * @param userId
     * @return
     * @throws IOException
     * @throws JSONException
     * @throws ParseException
     */
    public VisitedLocation getUserLocation(UUID userId) throws IOException, JSONException {
        VisitedLocation result = null;
        String url = super.getApiServerUrl("gpsutil.host") + "/getUserLocation";

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("userId", userId.toString());
        JSONObject data = super.httpRequestService.getReq(url, urlParams);
        if (data != null) {
            Integer status = data.getInt("status");
            if (status < 299) {
                JSONObject content = (JSONObject) data.get("content");
                JSONObject dateJson = content.getJSONObject("timeVisited");

                LocalDateTime localDateTime = LocalDateTime.of(
                        dateJson.getInt("year"),
                        dateJson.getInt("month"),
                        dateJson.getInt("date"),
                        dateJson.getInt("hours"),
                        dateJson.getInt("minutes"),
                        dateJson.getInt("seconds")
                );

                JSONObject locationJson = content.getJSONObject("location");
                Location location = new Location(locationJson.getDouble("latitude"), locationJson.getDouble("longitude"));
                result = new VisitedLocation(
                    userId,
                    location,
                    Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
                );
            } else {
                logger.error("Api Request Error : " + url + ", status : " + status);
            }
        } else {
            logger.error("Api Request Error : " + url + ", no data");
        }
        return result;
    }

    /**
     * Get All Attraction of GPSUtil Api
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public List<Attraction> getAttractions() throws IOException, JSONException {
        List<Attraction> attractionList = null;
        String url = super.getApiServerUrl("gpsutil.host") + "/getAttractions";

        JSONObject data = super.httpRequestService.getReq(url, null);
        if (data != null) {
            Integer status = data.getInt("status");
            if (status < 299) {
                attractionList = new ArrayList<>();
                JSONArray content = (JSONArray) data.get("content");
                for (int i = 0; i < content.length(); i++) {
                    JSONObject attractionJson = content.getJSONObject(i);
                    Attraction attraction = new Attraction(
                            attractionJson.getString("attractionName"),
                            attractionJson.getString("city"),
                            attractionJson.getString("state"),
                            attractionJson.getDouble("latitude"),
                            attractionJson.getDouble("longitude")
                    );
                    attractionList.add(attraction);
                }
            } else {
                logger.error("Api Request Error : " + url + ", status : " + status);
            }
        } else {
            logger.error("Api Request Error : " + url + ", no data");
        }
        return attractionList;
    }
}
