package com.nox.JavaBootCampAdv.mapper;

import com.nox.JavaBootCampAdv.dto.PositionDto;
import com.nox.JavaBootCampAdv.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    @Mapping(target = "companyId", source = "company.id")
    PositionDto toDto(Position position);

    Position toEntity(PositionDto positionDto);
}