package com.nineleaps.greytHRClone.helper;

import com.nineleaps.greytHRClone.service.AttendanceService;
import com.nineleaps.greytHRClone.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;


@Component
public class Scheduler {


    private FeedService feedService;
    private AttendanceService attendanceService;

    @Autowired
    public Scheduler(FeedService feedService, AttendanceService attendanceService) {
        this.feedService = feedService;
        this.attendanceService = attendanceService;
    }

    @Async
    @Scheduled(cron = "0 35 11 * * ?")//sec,min,hour,dayDate,monthDate,dayWeek/year
    public void AddBirthdayAndAnniversary() {
        feedService.createEventFeed();
    }



    @Async
    @Scheduled(cron = "0 39 10 * * 1-5")//sec,min,hour,dayDate,monthDate,dayWeek/yearday of week (0 - 6) (0 is Sunday, or use names)
    public void markAttendence() throws ParseException {
        attendanceService.markAttendence();
    }


}
