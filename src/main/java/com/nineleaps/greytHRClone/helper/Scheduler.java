package com.nineleaps.greytHRClone.helper;

import com.nineleaps.greytHRClone.model.EventType;
import com.nineleaps.greytHRClone.model.Feed;
import com.nineleaps.greytHRClone.model.FeedType;
import com.nineleaps.greytHRClone.repository.EmployeeDataRepository;
import com.nineleaps.greytHRClone.repository.FeedRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    private EmployeeDataRepository employeeDataRepository;
    private FeedRepository feedRepository;
    @Autowired
    public Scheduler(EmployeeDataRepository employeeDataRepository, FeedRepository feedRepository) {
        this.employeeDataRepository = employeeDataRepository;
        this.feedRepository = feedRepository;
    }

    @Async
    @Scheduled(cron = "0 30 6 * * ?")//sec,min,hour,dayDate,monthDate,dayWeek/year
    public void AddBirthdayAndAnniversary(){
        List<JSONObject> birthdayList= employeeDataRepository.BirthdayList();
        List<JSONObject> anniversaryList=employeeDataRepository.AnniversaryList();

       for(JSONObject eventObj:birthdayList){
           Feed feed=new Feed();
           feed.setEventType(EventType.BIRTHDAY);
           feed.setFeedType(FeedType.EVENTS);
           feed.setName((String)eventObj.get("name"));
           feedRepository.save(feed);
       }
        for(JSONObject eventObj:anniversaryList){
            Feed feed=new Feed();
            feed.setEventType(EventType.ANNIVERSARY);
            feed.setFeedType(FeedType.EVENTS);
            feed.setName((String)eventObj.get("name"));
            feedRepository.save(feed);
        }


    }



}
