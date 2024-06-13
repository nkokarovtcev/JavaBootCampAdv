package com.nox.JavaBootCampAdv.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    @NotNull(message = "First name cannot be null")
    private String firstName;
    private String lastName;
    @NotNull(message = "Position id cannot be null")
    private Long positionId;
}