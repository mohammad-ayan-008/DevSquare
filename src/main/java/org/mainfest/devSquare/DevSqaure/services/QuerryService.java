package org.mainfest.devSquare.DevSqaure.services;

import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.CommentDTO;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface QuerryService {

    Querry save(Querry querry,String name);
    Querry delete(String id,String name);
    Querry update(Querry querry,String name,String id);
    List<Querry> fetchAll(String name);
    Querry fetchByID(String id);
    Querry uploadComment(String id, CommentDTO commentDTO);
    Page<Querry> getAllQuerries(int offset,int page);
    boolean deleteComment(String comment_id,String Querry_id);
    Querry helpful_btn(String post_id, String name);
    void deleteAll();
}
