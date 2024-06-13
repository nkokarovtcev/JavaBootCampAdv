package com.nox.JavaBootCampAdv.mapper;

import com.nox.JavaBootCampAdv.dto.EmployeeDto;
import com.nox.JavaBootCampAdv.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "positionId", source = "position.id")
    EmployeeDto toDto(Employee employee);

    Employee toEntity(EmployeeDto employeeDto);
}