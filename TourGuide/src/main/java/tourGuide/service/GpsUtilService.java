package tourGuide.service;

import com.jsoniter.annotation.JsonObject;
import gpsUtil.location.Attraction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tourGuide.models.AttractionTMP;
import tourGuide.models.LocationTMP;
import tourGuide.models.VisitedLocationTMP;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class GpsUtilService extends ExternalApiService {

    /**
     * Constructor
     * @param httpRequestService
     * @param configurationFilePath
     */
    public GpsUtilService(HTTPRequestService httpRequestService, String configurationFilePath) {
        super(httpRequestService, configurationFilePath);
    }

    public VisitedLocationTMP getUserLocation(UUID userId) throws IOException, JSONException, ParseException {
        VisitedLocationTMP result = null;

        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("userId", userId.toString());
        JSONObject data = super.httpRequestService.getReq(super.getApiServerUrl("gpsutil.host") + "/getUserLocation", urlParams);
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
                LocationTMP location = new LocationTMP(locationJson.getDouble("latitude"), locationJson.getDouble("longitude"));
                result = new VisitedLocationTMP(
                    userId,
                    location,
                    Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
                );
            }
        }
        return result;
    }

    public List<AttractionTMP> getAttractions() throws IOException, JSONException {
        List<AttractionTMP> attractionList = null;

        JSONObject data = super.httpRequestService.getReq(super.getApiServerUrl("gpsutil.host") + "/getAttractions", null);
        if (data != null) {
            Integer status = data.getInt("status");
            if (status < 299) {
                attractionList = new ArrayList<>();
                JSONArray content = (JSONArray) data.get("content");
                for (int i = 0; i < content.length(); i++) {
                    JSONObject attractionJson = content.getJSONObject(i);
                    AttractionTMP attraction = new AttractionTMP(
                            attractionJson.getString("attractionName"),
                            attractionJson.getString("city"),
                            attractionJson.getString("state"),
                            attractionJson.getDouble("latitude"),
                            attractionJson.getDouble("longitude")
                    );
                    attractionList.add(attraction);
                }
            }
        }
        return attractionList;
    }
}
