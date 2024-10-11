package org.mainfest.devSquare.DevSqaure.services;

import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;

@Service
public interface BloomFilterService {
    RBloomFilter<String> getUserNameBloomFilter();
    void AddUserName(String userName);
    Boolean ifSUserNameIsAvailable(String userName);
    RBloomFilter<String> getUserHandleBloomFilter();
    void AddUserhandle(String userhandle);
    Boolean ifSUserHandleIsAvailable(String userhandle);
}
