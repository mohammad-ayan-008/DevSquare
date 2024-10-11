package org.mainfest.devSquare.DevSqaure.repositories;

import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.CommentDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepliesRepository extends MongoRepository<CommentDTO, ObjectId> {
}
