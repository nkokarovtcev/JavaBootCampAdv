package com.nox.JavaBootCampAdv.dto;

import lombok.Data;

import java.util.List;

@Data
public class BadRequestExceptionDto {
    private String message;
    private List<String> details;
}
