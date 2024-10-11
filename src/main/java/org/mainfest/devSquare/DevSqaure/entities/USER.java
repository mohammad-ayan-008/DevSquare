package org.mainfest.devSquare.DevSqaure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "user_collection")
public class USER {
    @Id
    private ObjectId id;

    @NotNull
    @Indexed(unique = true)
    private String userName;

    @NotNull
    private String password;

    private List<String> roles= new ArrayList<>();

    @DBRef
    private ArrayList<Querry> querries = new ArrayList<>();

    @Transient
    private int no_of_querries_asked;


}
