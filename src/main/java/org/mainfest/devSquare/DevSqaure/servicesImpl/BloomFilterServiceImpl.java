package org.mainfest.devSquare.DevSqaure.servicesImpl;

import org.mainfest.devSquare.DevSqaure.services.BloomFilterService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterServiceImpl implements BloomFilterService {
    @Autowired
    private RedissonClient redissonClient;

    private RBloomFilter<String> _filter_userName;


    @Override
    public RBloomFilter<String> getUserNameBloomFilter() {
        if (_filter_userName == null) {
            _filter_userName = redissonClient.getBloomFilter("userName_1");
            _filter_userName.tryInit(1000000L,0.0000000001);
        }
        return _filter_userName;
    }


    @Override
    public void AddUserName(String userName) {
        RBloomFilter<String> stringFilter = getUserNameBloomFilter();
        stringFilter.add(userName);
    }


    @Override
    public Boolean ifSUserNameIsAvailable(String userName) {
        return getUserNameBloomFilter().contains(userName);
    }

    @Override
    public boolean Invalidate() {
        return getUserNameBloomFilter().delete();
    }

}
