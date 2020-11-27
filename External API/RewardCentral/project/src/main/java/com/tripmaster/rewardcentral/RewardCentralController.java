package com.tripmaster.rewardcentral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rewardCentral.RewardCentral;

import java.util.UUID;

@RestController
public class RewardCentralController {
    @Autowired
    private RewardCentral rewardCentral;

    @GetMapping("/getAttractionRewardPoints")
    public String getAttractionRewardPoints(@RequestParam(required = true) String userId, String attractionId) {
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(attractionId)) {
            StringBuilder result = new StringBuilder();
            result.append("\"")
                  .append(rewardCentral.getAttractionRewardPoints(UUID.fromString(userId), UUID.fromString(attractionId)))
                  .append("\"");
            return result.toString();
        }
        return null;
    }
}
