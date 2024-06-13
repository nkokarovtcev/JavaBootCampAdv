package com.nox.JavaBootCampAdv.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyDto {
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Location cannot be null")
    private String location;
}