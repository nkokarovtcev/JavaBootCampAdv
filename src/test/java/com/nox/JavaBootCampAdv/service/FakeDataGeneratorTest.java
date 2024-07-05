package com.nox.JavaBootCampAdv.service;

import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.dto.PositionDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FakeDataGeneratorTest {
    @Autowired
    FakeDataGenerator fakeDataGenerator;
    @Autowired
    CompanyService companyService;
    @Autowired
    PositionService positionService;
    @Autowired
    EmployeeService employeeService;

    @AfterEach
    void tearDown() {
        employeeService.getAllEmployees().forEach(employee -> employeeService.deleteEmployee(employee.getId()));
        positionService.getAllPositions().forEach(position -> positionService.deletePosition(position.getId()));
        companyService.getAllCompanies().forEach(company -> companyService.deleteCompany(company.getId()));
    }

    @Test
    void generateDataTest() {
        fakeDataGenerator.generateData(1, 1, 1);
        assertEquals(1, companyService.getAllCompanies().size());
        assertEquals(1, positionService.getAllPositions().size());
        assertEquals(1, employeeService.getAllEmployees().size());
    }

    @Test
    void generateDataTest2() {
        fakeDataGenerator.generateData(2, 2, 2);
        assertEquals(2, companyService.getAllCompanies().size());
        assertEquals(4, positionService.getAllPositions().size());
        assertEquals(8, employeeService.getAllEmployees().size());
    }

    @Test
    void generateDataTest3() {
        fakeDataGenerator.generateData(3, 10, 20);
        assertEquals(3, companyService.getAllCompanies().size());

        CompanyDto firstCompany = companyService.getAllCompanies().getFirst();
        List<PositionDto> positionsByCompany = positionService.getPositionsByCompany(firstCompany.getId());
        assertEquals(10, positionsByCompany.size());

        positionsByCompany.forEach(position -> assertEquals(20, employeeService.getEmployeesByPosition(position.getId()).size()));
    }
}