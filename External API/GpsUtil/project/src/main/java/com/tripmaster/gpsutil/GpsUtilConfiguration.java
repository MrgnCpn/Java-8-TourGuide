package com.tripmaster.gpsutil;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpsUtilConfiguration {
    @Bean
    public GpsUtil getGpsUtil(){
        return new GpsUtil();
    }
}
