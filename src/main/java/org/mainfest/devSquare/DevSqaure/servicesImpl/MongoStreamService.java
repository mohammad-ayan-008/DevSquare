package org.mainfest.devSquare.DevSqaure.servicesImpl;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
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

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        MongoClient mongoClient = MongoClients.create(mongoUri);
        MongoDatabase database = mongoClient.getDatabase("DevCluster");
        MongoCollection<Document> collection = database.getCollection("querry_collection");

        collection.watch().forEach((ChangeStreamDocument<Document> changeStreamDocument) -> {
            String json = changeStreamDocument.getFullDocument().toJson();
            Querry querry = gson.fromJson(json, Querry.class);
            notificationservice.sendNotification(querry);
        });
    }



}
