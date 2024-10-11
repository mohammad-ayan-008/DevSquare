package org.mainfest.devSquare.DevSqaure.servicesImpl;


import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.USER;
import org.mainfest.devSquare.DevSqaure.repositories.UserRepository;
import org.mainfest.devSquare.DevSqaure.services.BloomFilterService;
import org.mainfest.devSquare.DevSqaure.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userrepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BloomFilterService bloomFilterService;

    @Override
    public USER save(USER user) {

        if (bloomFilterService.ifSUserNameIsAvailable(user.getUserName())) {
            logger.info("UserName Already Exists");
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        bloomFilterService.AddUserName(user.getUserName());

        return userrepository.save(user);
    }

    @Override
    public boolean delete(ObjectId id) {
        if (userrepository.findById(id).get() == null)  return false;
        userrepository.deleteById(id);
        return true;
    }

    @Override
    public USER update(USER user) {
        return userrepository.save(user);
    }

    @Override
    public List<USER> fetchAll() {
        return userrepository.findAll()
                .stream()
                .peek(x-> x.setNo_of_querries_asked(x.getQuerries().size()))
                .collect(Collectors.toList());
    }

    @Override
    public USER fetchByID(ObjectId id) {
         USER user = userrepository.findById(id).get();
         if (user != null){
             user.setNo_of_querries_asked(user.getQuerries().size());
             return user;
         }
         return null;
    }

    @Override
    public void deleteAll() {
        userrepository.deleteAll();
    }
}
