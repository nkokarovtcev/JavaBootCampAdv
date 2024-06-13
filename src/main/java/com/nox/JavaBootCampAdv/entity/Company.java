package com.nox.JavaBootCampAdv.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies", uniqueConstraints = {@UniqueConstraint(name = "uc_company_name", columnNames = {"name"})})
@Getter
@Setter
@RequiredArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String location;
}
