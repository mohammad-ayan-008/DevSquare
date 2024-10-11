package org.mainfest.devSquare.DevSqaure.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.mainfest.devSquare.DevSqaure.entities.payloads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Notificationservice {
    private Logger logger = LoggerFactory.getLogger(Notificationservice.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotification(Querry querry){
        logger.info("{} sending info",querry);
        simpMessagingTemplate.convertAndSend("/topic/fetch_querry_Updates",querry);
    }

    public void sendMentionedNotification(payloads pls){
        logger.info("sending info {}",pls);
        simpMessagingTemplate.convertAndSend("/topic/fetch_notification_mentioned/"+pls.getMentioned_name(),pls);
    }

}
