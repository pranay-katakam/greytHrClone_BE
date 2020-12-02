package com.nineleaps.greytHRClone.helper;

import com.nineleaps.greytHRClone.service.AttendanceService;
import com.nineleaps.greytHRClone.service.EmployeeSalaryService;
import com.nineleaps.greytHRClone.service.FeedService;
import com.nineleaps.greytHRClone.service.LeaveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;


@Component
public class Scheduler {


    private FeedService feedService;
    private AttendanceService attendanceService;
    private LeaveServices leaveServices;
    private EmployeeSalaryService employeeSalaryService;

    @Autowired
    public Scheduler(FeedService feedService, AttendanceService attendanceService, LeaveServices leaveServices,EmployeeSalaryService employeeSalaryService)  {
        this.feedService = feedService;
        this.attendanceService = attendanceService;
        this.leaveServices = leaveServices;
        this.employeeSalaryService  =employeeSalaryService;
    }

    @Async
    @Scheduled(cron = "0 35 11 * * ?")//sec,min,hour,dayDate,monthDate,dayWeek/year
    public void AddBirthdayAndAnniversary() {
        feedService.createEventFeed();
    }



    @Async
    @Scheduled(cron = "0 48 7 * * 1-5")//sec,min,hour,dayDate,monthDate,dayWeek/yearday of week (0 - 6) (0 is Sunday, or use names)
    public void markAttendence() {
        attendanceService.markAttendence();
    }


    @Async
    @Scheduled(cron = "50 52 10 18 * *")//sec,min,hour,dayDate,monthDate,dayWeek/yearday of week (0 - 6) (0 is Sunday, or use names)
    public void addEarnedLeaveMonthly()  {
        leaveServices.addEarnedLeaveMonthly();
    }


    @Async
    @Scheduled(cron = "30 19 10 * * *")//sec,min,hour,dayDate,monthDate,dayWeek/yearday of week (0 - 6) (0 is Sunday, or use names)
    public void addSalaryMonthly()  {
//        employeeSalaryService.addSalaryMonthly();

    }
}
