package com.tripmaster.rewardcentral;

import com.jsoniter.output.JsonStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rewardCentral.RewardCentral;

import java.util.UUID;

@RestController
public class RewardCentralController {
    @Autowired
    RewardCentral rewardCentral;

    @GetMapping("/getAttractionRewardPoints")
    public String getAttractionRewardPoints(@RequestParam(required = true) String userId, String attractionId) {
        if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(attractionId)) {
            return JsonStream.serialize(
                    rewardCentral.getAttractionRewardPoints(
                            UUID.fromString(userId),
                            UUID.fromString(attractionId)
                    )
            );
        }
        return null;
    }
}
