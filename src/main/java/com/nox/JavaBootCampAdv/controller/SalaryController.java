package com.nox.JavaBootCampAdv.controller;

import com.nox.JavaBootCampAdv.dto.PaymentRequestDto;
import com.nox.JavaBootCampAdv.dto.SalaryPaymentDto;
import com.nox.JavaBootCampAdv.service.SalaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("salaryPayments")
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("payout")
    public ResponseEntity<List<SalaryPaymentDto>> payMonthlyPayments(@Valid @RequestBody PaymentRequestDto paymentRequest) {
        return ResponseEntity.ok(salaryService.payMonthlyPayments(paymentRequest));
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<List<SalaryPaymentDto>> getPaymentsByEmployee(
            @PathVariable Long id,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getPaymentsByEmployee(id, year, month));
    }

    @GetMapping("company/{id}")
    public ResponseEntity<List<SalaryPaymentDto>> getPaymentsByCompany(
            @PathVariable Long id,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getPaymentsByCompany(id, year, month));
    }

    @GetMapping()
    public ResponseEntity<List<SalaryPaymentDto>> getPayments(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getPayments(year, month));
    }

    @GetMapping("total")
    public ResponseEntity<Double> getTotalPayments(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getTotalPayments(year, month));
    }

    @GetMapping("total/employee/{id}")
    public ResponseEntity<Double> getTotalPaymentsByEmployee(
            @PathVariable Long id,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getTotalPaymentsByEmployee(id, year, month));
    }

    @GetMapping("total/company/{id}")
    public ResponseEntity<Double> getTotalPaymentsByCompany(
            @PathVariable Long id,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getTotalPaymentsByCompany(id, year, month));
    }

    @GetMapping("average")
    public ResponseEntity<Double> getAveragePayments(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getAveragePayments(year, month));
    }

    @GetMapping("average/employee/{id}")
    public ResponseEntity<Double> getAveragePaymentsByEmployee(
            @PathVariable Long id,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getAveragePaymentsByEmployee(id, year, month));
    }

    @GetMapping("average/company/{id}")
    public ResponseEntity<Double> getAveragePaymentsByCompany(
            @PathVariable Long id,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Month month
    ) {
        return ResponseEntity.ok(salaryService.getAveragePaymentsByCompany(id, year, month));
    }
}