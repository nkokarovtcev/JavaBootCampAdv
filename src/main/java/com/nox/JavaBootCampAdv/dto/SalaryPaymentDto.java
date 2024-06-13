package com.nox.JavaBootCampAdv.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.Month;

@Data
public class SalaryPaymentDto {
    private Long id;
    private Integer year;
    private Month month;
    private LocalDate paymentDate;
    private Double amount;
    private Long employeeId;
}