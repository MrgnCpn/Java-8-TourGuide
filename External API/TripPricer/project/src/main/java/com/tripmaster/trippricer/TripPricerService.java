package com.tripmaster.trippricer;

import org.springframework.util.StringUtils;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TripPricerService {
    private final TripPricer tripPricer;

    public TripPricerService(TripPricer tripPricer) {
        this.tripPricer = tripPricer;
    }

    private boolean StringUtilsIsEmpty(String str) {
        if (str == null) return true;
        else return StringUtils.isEmpty(str);
    }

    public List<Provider> getPrice(Map<String, Object> requestParams){
        if (
                requestParams != null &&
                !StringUtilsIsEmpty((String) requestParams.get("apiKey")) &&
                !StringUtilsIsEmpty((String) requestParams.get("attractionId")) &&
                (requestParams.get("adults") != null) &&
                (requestParams.get("children") != null) &&
                (requestParams.get("nightsStay") != null) &&
                (requestParams.get("rewardsPoints") != null)
        ) {
            return tripPricer.getPrice(
                    (String) requestParams.get("apiKey"),
                    UUID.fromString((String) requestParams.get("attractionId")),
                    Integer.valueOf((String) requestParams.get("adults")),
                    Integer.valueOf((String) requestParams.get("children")),
                    Integer.valueOf((String) requestParams.get("nightsStay")),
                    Integer.valueOf((String) requestParams.get("rewardsPoints"))
            );
        }
        return null;
    }
}
