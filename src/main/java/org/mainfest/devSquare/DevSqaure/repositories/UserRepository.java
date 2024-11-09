package org.mainfest.devSquare.DevSqaure.repositories;

import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.USER;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<USER, String> {
    USER findByUserName(String userName);
}
