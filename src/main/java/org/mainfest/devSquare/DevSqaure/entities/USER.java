package org.mainfest.devSquare.DevSqaure.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mainfest.devSquare.DevSqaure.Validations.UniqueUserName;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "user_collection")
public class USER {
    @Id
    private String id;

    @NotNull
    @NotBlank
    @Indexed(unique = true)
    @UniqueUserName
    private String userName;

    @NotNull
    @NotBlank
    @Size(min = 8,message = "password must contain atleast 8 characters")
    private String password;

    private List<String> roles= new ArrayList<>();

    @DBRef
    private ArrayList<Querry> querries = new ArrayList<>();

    @Transient
    private int no_of_querries_asked;

}
