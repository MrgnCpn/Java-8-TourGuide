package com.tripmaster.trippricer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tripPricer.Provider;

import java.util.List;
import java.util.Map;

@RestController
public class TripPricerController {
    @Autowired
    private TripPricerService tripPricerService;

    @PostMapping("/getPrice")
    public String getPrice(@RequestParam Map<String, Object> requestParams) {

        List<Provider> allProviders = tripPricerService.getPrice(requestParams);
        StringBuffer result = new StringBuffer();

        result.append("[");
        for (Provider provider : allProviders) {
            result.append("{\"name\" : \"").append(provider.name).append("\",");
            result.append("\"price\" : ").append(provider.price).append(",");
            result.append("\"tripId\" : \"").append(provider.tripId.toString()).append("\"}");

            if (!provider.equals(allProviders.get(allProviders.size() - 1))){
                result.append(",");
            }
        }
        result.append("]");
        return result.toString();
    }
}
