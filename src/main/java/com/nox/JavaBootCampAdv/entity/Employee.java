package com.nox.JavaBootCampAdv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "employees", indexes = {@Index(name = "idx_employee_position_id", columnList = "position_id")})
@Getter
@Setter
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @OneToMany(mappedBy = "employee")
    private List<SalaryPayment> salaryPayments;
}