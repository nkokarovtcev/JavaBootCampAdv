package com.nox.JavaBootCampAdv.mapper;

import com.nox.JavaBootCampAdv.dto.SalaryPaymentDto;
import com.nox.JavaBootCampAdv.entity.SalaryPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalaryPaymentMapper {
    @Mapping(target = "employeeId", source = "employee.id")
    SalaryPaymentDto toDto(SalaryPayment salaryPayment);

    SalaryPayment toEntity(SalaryPaymentDto salaryPaymentDto);
}