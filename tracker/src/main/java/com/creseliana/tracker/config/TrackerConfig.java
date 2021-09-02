package com.creseliana.tracker.config;

import com.creseliana.tracker.TrackerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrackerConfig {
    @Bean
    public TrackerAspect trackerAspect() {
        return new TrackerAspect();
    }
}
