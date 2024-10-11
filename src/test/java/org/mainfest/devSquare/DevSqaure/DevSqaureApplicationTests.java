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


//@SpringBootTest

class DevSqaureApplicationTests {
}


//	@Autowired
//	private QuerryService querryService;
//
//	@Autowired
//	private UserService userService;
//
//
//@Test//
//	void addUser() {
//Insert the user
//		USER user = USER.builder()
//				.userName("Test1#412")
//				.password("AYAN#123")
//				.build();
//		USER save = userService.save(user);
//		assertNotNull(save);
//	}

//
//    @Disabled
//	@Test
//	void Addquerry(){
//		Querry querry = Querry.builder()
//				.Querry("How to integrate  RabbitMq to Spring Boot")
//				.replies(new ArrayList<>(
//						List.of(CommentDTO.builder()
//										.id(new ObjectId())
//										.reply_by("Test#412")
//										.reply_to("Test#412")
//										.reply("hey @Test#412 just follow the official docs").build())))
//				.build();
//		Querry save = querryService.save(querry, "Test#412");
//		assertNotNull(save);
//	}
//	@Test
//	void AddLike(){
//		ObjectId id = new ObjectId("670406d3d88a8d691d358f93");
//		assertNotNull(querryService.helpful_btn(id,"Test#412"));
//	}
//
//
//	@AfterEach
//	public  void afterAll(){
//		userService.deleteAll();
//		querryService.deleteAll();
//	}


