package org.mainfest.devSquare.DevSqaure.servicesImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MongoStreamService {

    private Logger logger = LoggerFactory.getLogger(MongoStreamService.class);
    @Autowired
    private Notificationservice notificationservice;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    @Autowired
    private Gson gson;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Thread thread = new Thread(() -> {
            MongoClient mongoClient = MongoClients.create(mongoUri);
            MongoDatabase database = mongoClient.getDatabase("DevCluster");
            MongoCollection<Document> collection = database.getCollection("querry_collection");

            collection.watch()
                    .fullDocument(FullDocument.UPDATE_LOOKUP)
                    .forEach((ChangeStreamDocument<Document> changeStreamDocument) -> {

                        Document document = changeStreamDocument.getFullDocument();
                        System.out.println(document);

                        if (document != null && document.containsKey("_id")) {
                            ObjectId id = (ObjectId) document.getObjectId("_id");
                            document.put("_id", id.toHexString());

                            System.out.println(document.toJson());
                            Querry querry = gson.fromJson(document.toJson(), Querry.class);
                            notificationservice.sendNotification(querry);
                        }
                    });
        });
        thread.setDaemon(true);
        thread.start();

    }



}
