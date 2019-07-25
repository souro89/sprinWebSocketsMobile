package com.souro.websockets.dashboard.services;

import com.souro.websockets.dashboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DashboardServiceImpl {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Random random = new Random();


    public int randomInt(int range){
        return random.nextInt(range);
    }

    public int randomIntRange(int range1,int range2){
        return ThreadLocalRandom.current().nextInt(range1,range2);
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

    @Scheduled(fixedDelay = 15000)
    public void getSegmentCounts(){
        HashMap<String,Integer> segmentCounts = new HashMap<>();
        segmentCounts.put("Mass",randomInt(70));
        segmentCounts.put("Premium Mass",randomInt(40));
        segmentCounts.put("Elite",randomInt(20));
        messagingTemplate.convertAndSend("/topic/segmentCount",segmentCounts);
    }

    @Scheduled(fixedDelay = 12000)
    public void getUsageCounts(){
        HashMap<String,Integer> usageCounts = new HashMap<>();
        HashMap<String,Integer> errorCounts = new HashMap<>();

        usageCounts.put("Fund Transfer",randomIntRange(5000,8000));
        usageCounts.put("DEWA",randomIntRange(1000,5000));
        usageCounts.put("ETISALAT",randomIntRange(500,1000));
        usageCounts.put("Du",randomIntRange(500,1000));
        usageCounts.put("CC Payment",randomIntRange(200,1000));

        errorCounts.put("Fund Transfer",randomIntRange(1000,2000));
        errorCounts.put("DEWA",randomIntRange(100,500));
        errorCounts.put("ETISALAT",randomIntRange(100,200));
        errorCounts.put("Du",randomIntRange(10,50));
        errorCounts.put("CC Payment",randomIntRange(100,500));

        HashMap<String,Integer> sorteduserCounts = sortByValue(usageCounts);

        messagingTemplate.convertAndSend("/topic/usageCount",sorteduserCounts);
    }

    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
