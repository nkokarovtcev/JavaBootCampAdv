package com.nox.JavaBootCampAdv.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PositionDto {
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Salary cannot be null")
    private Double salary;
    @NotNull(message = "Company id cannot be null")
    private Long companyId;
}