package com.nox.JavaBootCampAdv.service;

import com.github.javafaker.Faker;
import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.dto.PositionDto;
import com.nox.JavaBootCampAdv.entity.Position;
import com.nox.JavaBootCampAdv.mapper.PositionMapper;
import com.nox.JavaBootCampAdv.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final CompanyService companyService;
    private final Faker faker;

    public List<PositionDto> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(positionMapper::toDto)
                .toList();
    }

    public Optional<PositionDto> getPositionById(Long id) {
        return positionRepository
                .findById(id)
                .map(positionMapper::toDto);
    }

    public PositionDto createPosition(PositionDto positionDto) {
        Position position = positionMapper.toEntity(positionDto);
        position.setCompany(companyService.getCompanyByIdOrThrow(positionDto.getCompanyId()));
        return positionMapper.toDto(
                positionRepository.save(position)
        );
    }

    public PositionDto updatePosition(Long id, PositionDto positionDto) {
        Optional<Position> positionOptional = positionRepository.findById(id);
        if (positionOptional.isPresent()) {
            Position positionToUpdate = positionOptional.get();
            positionToUpdate.setCompany(companyService.getCompanyByIdOrThrow(positionDto.getCompanyId()));
            positionToUpdate.setName(positionDto.getName());
            positionToUpdate.setSalary(positionDto.getSalary());
            return positionMapper.toDto(
                    positionRepository.save(positionToUpdate)
            );
        } else {
            throw new RuntimeException("Position not found by id = %s".formatted(id));
        }
    }

    public void deletePosition(Long id) {
        Optional<Position> positionOptional = positionRepository.findById(id);
        if (positionOptional.isPresent()) {
            positionRepository.delete(positionOptional.get());
        } else {
            throw new RuntimeException("Position not found by id = %s".formatted(id));
        }
    }

    public List<PositionDto> getPositionsByCompany(Long id) {
        return positionRepository.findByCompanyId(id)
                .stream()
                .map(positionMapper::toDto)
                .toList();
    }

    public List<PositionDto> generatePositions(int count, List<CompanyDto> Companies) {
        List<CompanyDto> companiesToUse = Objects.requireNonNullElseGet(
                Companies,
                companyService::getAllCompanies
        );
        if (companiesToUse.isEmpty()) {
            throw new RuntimeException("No Companies found to generate positions!");
        }
        return companiesToUse.stream()
                .flatMap(company -> IntStream.range(0, count)
                        .mapToObj(_ -> {
                            PositionDto position = new PositionDto();
                            position.setName(faker.company().profession());
                            position.setCompanyId(company.getId());
                            position.setSalary(faker.number().randomDouble(0, 1000, 5000));
                            return createPosition(position);
                        })
                )
                .toList();
    }

    Position getPositionByIdOrThrow(Long positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found by id = %s".formatted(positionId)));
    }
}