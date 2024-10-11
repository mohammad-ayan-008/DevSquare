package org.mainfest.devSquare.DevSqaure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("redisson")
@Data
public class ReddisionConfig {

    private String redisUrl;

}
