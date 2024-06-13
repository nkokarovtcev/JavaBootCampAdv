package com.nox.JavaBootCampAdv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;

@Entity(name = "salary_payments")
@Table(indexes = {
        @Index(name = "idx_salarypayment_employee_id", columnList = "employee_id"),
        @Index(name = "idx_salarypayment_year_month", columnList = "year, month")
})
@Getter
@Setter
@RequiredArgsConstructor
public class SalaryPayment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private Month month;
    private Double amount;
    private LocalDate paymentDate;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
