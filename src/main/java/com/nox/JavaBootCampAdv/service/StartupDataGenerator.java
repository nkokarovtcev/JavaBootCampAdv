package com.nox.JavaBootCampAdv.service;

import com.nox.JavaBootCampAdv.dto.CompanyDto;
import com.nox.JavaBootCampAdv.dto.PaymentRequestDto;
import com.nox.JavaBootCampAdv.dto.PositionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class StartupDataGenerator {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final PositionService positionService;
    private final SalaryService salaryService;
    @Value("${startup.generateData:false}")
    private String generateData;
    @Value("${startup.generateData.companies:0}")
    private String companiesCount;
    @Value("${startup.generateData.positions-per-company:0}")
    private String positionsCount;
    @Value("${startup.generateData.employees-per-position:0}")
    private String employeesCount;

    @Async("threadPoolTaskExecutor")
    @EventListener(ApplicationReadyEvent.class)
    public void generateFakeData() {
        if (!Boolean.parseBoolean(generateData)) {
            return;
        }
        log.info("=======================================================");
        log.info("            Faker data generation started              ");
        log.info("=======================================================");
        try {
            List<CompanyDto> companies = companyService.generateCompanies(Integer.parseInt(companiesCount));
            List<PositionDto> positions = positionService.generatePositions(Integer.parseInt(positionsCount), companies);
            employeeService.generateEmployees(Integer.parseInt(employeesCount), positions);

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
