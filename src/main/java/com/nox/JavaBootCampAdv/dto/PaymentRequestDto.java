package com.nox.JavaBootCampAdv.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Month;

@Data
public class PaymentRequestDto {
    private Long employeeId;
    private Long positionId;
    private Long companyId;
    @NotNull(message = "Year cannot be null")
    private Integer year;
    @NotNull(message = "Month cannot be null")
    private Month month;
    private Boolean throwIfPaid;
}
