package org.mainfest.devSquare.DevSqaure;
import org.bson.types.ObjectId;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.mainfest.devSquare.DevSqaure.entities.CommentDTO;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.mainfest.devSquare.DevSqaure.entities.USER;
import org.mainfest.devSquare.DevSqaure.services.QuerryService;
import org.mainfest.devSquare.DevSqaure.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest

class DevSqaureApplicationTests {
    @Test
    void initialize(){

    }
}

