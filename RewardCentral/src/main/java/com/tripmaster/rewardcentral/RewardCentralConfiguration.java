package com.tripmaster.rewardcentral;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

@Configuration
public class RewardCentralConfiguration {
    @Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }
}
