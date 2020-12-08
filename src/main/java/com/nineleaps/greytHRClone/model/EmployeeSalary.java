package com.nineleaps.greytHRClone.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Data
@Entity
@Table(name="employee_salary")
public class EmployeeSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @Column(name = "eid")
    @NotNull(message = "eid must not be null!")
    private  int eid;

    @Column(name = "total_salary")
    @NotNull(message = "Total must not be null!")
    private  int totalSalary;

//    @JsonFormat(pattern = "MM-yyyy")
    @Column(name = "from_date")
    private LocalDate fromDate=LocalDate.now();

    @Column(name = "to_date")
    private LocalDate toDate;


}
