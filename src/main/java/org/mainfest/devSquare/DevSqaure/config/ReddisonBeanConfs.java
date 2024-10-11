package org.mainfest.devSquare.DevSqaure.config;

import com.google.gson.Gson;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReddisonBeanConfs {

    @Autowired
    private ReddisionConfig reddisionConfig;

    @Bean
    public Config getConfs(){
        Config config = new Config();
        config.useSingleServer().setAddress(reddisionConfig.getRedisUrl());
        return config;
    }
    @Bean
    public RedissonClient getClient(Config config){
        return Redisson.create(config);
    }

    @Bean
    public Gson gson(){
        return  new Gson();
    }


}
