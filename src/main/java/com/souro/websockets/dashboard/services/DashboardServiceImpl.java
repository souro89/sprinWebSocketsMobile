package com.souro.websockets.dashboard.services;

import com.souro.websockets.dashboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DashboardServiceImpl {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Random random = new Random();

    public int randomInt(int range){
        return random.nextInt(range);
    }


    @Scheduled(fixedDelay = 2000)
    public void getUserDetails(){

        User user1 = new User();
        user1.setId(1);
        user1.setName("Souro");
        user1.setError("App Hangs");

        messagingTemplate.convertAndSend("/topic/User",user1);

    }

    @Scheduled(fixedDelay = 3000)
    public void getUpdatedCounts(){
        messagingTemplate.convertAndSend("/topic/userCount",randomInt(10));
        messagingTemplate.convertAndSend("/topic/loginCount",randomInt(10));
        messagingTemplate.convertAndSend("/topic/errorCount",randomInt(10));
        messagingTemplate.convertAndSend("/topic/downloadCount",randomInt(10));
    }


}
