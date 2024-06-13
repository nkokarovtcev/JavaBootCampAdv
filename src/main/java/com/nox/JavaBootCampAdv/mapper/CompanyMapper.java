package com.nox.JavaBootCampAdv.mapper;

import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDtoDto);
}