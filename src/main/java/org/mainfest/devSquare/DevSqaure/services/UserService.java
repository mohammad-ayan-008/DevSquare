package org.mainfest.devSquare.DevSqaure.services;

import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.USER;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    USER save(USER user);
    boolean delete(String id);
    USER update(USER user);
    List<USER> fetchAll();
    USER fetchByID(String id);
    void deleteAll();
    boolean invalidate();
}
