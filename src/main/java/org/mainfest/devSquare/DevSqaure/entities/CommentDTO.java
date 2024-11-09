package org.mainfest.devSquare.DevSqaure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CommentDTO {
    private String id;
    private String reply_by;
    private String reply_to;
    private String reply;
}