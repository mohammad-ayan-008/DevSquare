package org.mainfest.devSquare.DevSqaure.controller;



import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.CommentDTO;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.mainfest.devSquare.DevSqaure.services.QuerryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/querries")
public class QuerriesController {
    @Autowired
    private QuerryService querryService;

    @PostMapping
    public ResponseEntity<Querry> post(@RequestBody Querry querry){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Querry save = querryService.save(querry,name);
        if (save == null) return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return  new ResponseEntity<>(save,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Querry>> getMyQuerries(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(querryService.fetchAll(name));
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Querry> getById(@PathVariable String id){
        return ResponseEntity.ok(querryService.fetchByID(id));
    }

    @GetMapping
    public ResponseEntity<Page<Querry>> getAllQuerries(
            @RequestParam int offset,
            @RequestParam int page
    ){
        Page<Querry> allQuerries = querryService.getAllQuerries(offset, page);
        if (allQuerries != null || !allQuerries.isEmpty()) return new ResponseEntity<>(allQuerries,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Querry> delete(@RequestParam String id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Querry querry = querryService.delete(id,name);
        if ( querry != null) return  ResponseEntity.ok(querry);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PatchMapping("/post_comment/{uid}")
    public ResponseEntity<Querry> post(@PathVariable String uid, @RequestBody CommentDTO commentDTO){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        commentDTO.setReply_by(name);
        commentDTO.setId(new ObjectId().toString());
        Querry querry = querryService.uploadComment(uid, commentDTO);
        if (querry != null) return ResponseEntity.ok(querry);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete_comment")
    public ResponseEntity<Map<String,String>> deleteComment(
            @RequestParam String comment_id,
            @RequestParam String querry_id
            ){
        Map<String,String> result = new HashMap<>();

        if (querryService.deleteComment(comment_id,querry_id)){
            result.put("status","success");
            return  ResponseEntity.ok(result);
        }
        else result.put("status","failed");
        return new ResponseEntity<>(result,HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<Map<String,String>> addLike(
            @RequestParam String id) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (querryService.helpful_btn(id,name)!=null) return ResponseEntity.ok(Map.of("status","OK"));
        return new ResponseEntity<>(Map.of("status","failed"),HttpStatus.CONFLICT);
    }


}
