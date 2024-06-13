package com.nox.JavaBootCampAdv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "positions", indexes = {@Index(name = "idx_position_company_id", columnList = "company_id")})
@Getter
@Setter
@RequiredArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private Double salary;
}
