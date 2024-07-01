package com.nox.JavaBootCampAdv.service;

import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.dto.PaymentRequestDto;
import com.nox.JavaBootCampAdv.dto.PositionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FakeDataGenerator {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final PositionService positionService;
    private final SalaryService salaryService;

    public void generateData(int companiesCount, int positionsCount, int employeesCount) {
        log.info("=======================================================");
        log.info("            Faker data generation started              ");
        log.info("=======================================================");
        try {
            List<CompanyDto> companies = companyService.generateCompanies(companiesCount);
            List<PositionDto> positions = positionService.generatePositions(positionsCount, companies);
            employeeService.generateEmployees(employeesCount, positions);

            PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
            paymentRequestDto.setYear(2024);
            paymentRequestDto.setMonth(Month.JANUARY);
            salaryService.payMonthlyPayments(paymentRequestDto);

            paymentRequestDto.setMonth(Month.FEBRUARY);
            salaryService.payMonthlyPayments(paymentRequestDto);
        } catch (Exception e) {
            log.error("Error during data generation: %s".formatted(e.getMessage()));
        }
        log.info("=======================================================");
        log.info("            Faker data generation finished             ");
        log.info("=======================================================");
    }
}
