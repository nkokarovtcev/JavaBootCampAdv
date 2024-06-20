package com.nox.JavaBootCampAdv.service;

import com.github.javafaker.Faker;
import com.nox.JavaBootCampAdv.dto.EmployeeDto;
import com.nox.JavaBootCampAdv.dto.PositionDto;
import com.nox.JavaBootCampAdv.entity.Employee;
import com.nox.JavaBootCampAdv.mapper.EmployeeMapper;
import com.nox.JavaBootCampAdv.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PositionService positionService;
    private final Faker faker;

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return employeeRepository
                .findById(id)
                .map(employeeMapper::toDto);
    }

    public Employee getEmployeeByIdOrThrow(Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found by id = %s".formatted(id)));
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setPosition(positionService.getPositionByIdOrThrow(employeeDto.getPositionId()));
        return employeeMapper.toDto(
                employeeRepository.save(employee)
        );
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employeeToUpdate = employeeOptional.get();
            employeeToUpdate.setFirstName(employeeDto.getFirstName());
            employeeToUpdate.setLastName(employeeDto.getLastName());
            employeeToUpdate.setPosition(positionService.getPositionByIdOrThrow(employeeDto.getPositionId()));
            return employeeMapper.toDto(
                    employeeRepository.save(employeeToUpdate)
            );
        } else {
            throw new RuntimeException("Employee not found by id = %s".formatted(id));
        }
    }

    public void deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            employeeRepository.delete(employeeOptional.get());
        } else {
            throw new RuntimeException("Employee not found by id = %s".formatted(id));
        }
    }

    public List<EmployeeDto> getEmployeesByPosition(Long id) {
        return employeeRepository.findByPositionId(id)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public List<EmployeeDto> generateEmployees(int count, List<PositionDto> positions) {
        List<PositionDto> positionsToUse = Objects.requireNonNullElseGet(
                positions,
                positionService::getAllPositions
        );
        if (positionsToUse.isEmpty()) {
            throw new RuntimeException("No positions found to generate employees!");
        }
        return positionsToUse.stream()
                .flatMap(position ->
                        IntStream.range(0, count)
                                .parallel()
                                .mapToObj(_ -> {
                                    EmployeeDto employeeDto = new EmployeeDto();
                                    employeeDto.setFirstName(faker.name().firstName());
                                    employeeDto.setLastName(faker.name().lastName());
                                    employeeDto.setPositionId(position.getId());
                                    return createEmployee(employeeDto);
                                })
                )
                .toList();
    }
}