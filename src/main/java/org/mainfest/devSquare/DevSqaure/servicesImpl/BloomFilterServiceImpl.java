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
    private RBloomFilter<String> _filter_user_handle;

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
    public void AddUserhandle(String userhandle) {
        RBloomFilter<String> stringFilter = getUserHandleBloomFilter();
        stringFilter.add(userhandle);
    }


    @Override
    public Boolean ifSUserHandleIsAvailable(String userhandle) {
        return getUserHandleBloomFilter().contains(userhandle);
    }

    @Override
    public RBloomFilter<String> getUserHandleBloomFilter() {
        if (_filter_user_handle == null) {
            _filter_user_handle= redissonClient.getBloomFilter("userHandle_1");
            _filter_user_handle.tryInit(1000000L,0.0000000001);
        }
        return _filter_user_handle;
    }

}
