package com.nox.JavaBootCampAdv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class StartupSetup {
    @Value("${startup.generateData:false}")
    private String generateData;
    @Value("${startup.generateData.companies:0}")
    private String companiesCount;
    @Value("${startup.generateData.positions-per-company:0}")
    private String positionsCount;
    @Value("${startup.generateData.employees-per-position:0}")
    private String employeesCount;
    @Autowired
    FakeDataGenerator fakeDataGenerator;

    @Async("threadPoolTaskExecutor")
    @EventListener(ApplicationReadyEvent.class)
    public void generateFakeData() {
        if (!Boolean.parseBoolean(generateData)) {
            return;
        }
        try {
            fakeDataGenerator.generateData(Integer.parseInt(companiesCount), Integer.parseInt(positionsCount), Integer.parseInt(employeesCount));
        } catch (Exception e) {
            log.error("Error during data generation: %s".formatted(e.getMessage()));
        }
    }
}
