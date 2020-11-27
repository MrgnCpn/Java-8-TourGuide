package com.tripmaster.trippricer;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TripPricerController {
    @Autowired
    private TripPricerService tripPricerService;

    @PostMapping("/getPrice")
    public String getPrice(@RequestParam Map<String, Object> requestParams) {
        return JsonStream.serialize(tripPricerService.getPrice(requestParams));
    }
}
