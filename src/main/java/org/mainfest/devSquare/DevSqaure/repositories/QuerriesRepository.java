package org.mainfest.devSquare.DevSqaure.repositories;

import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuerriesRepository extends MongoRepository<Querry, ObjectId> {

}
