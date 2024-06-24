package com.nox.JavaBootCampAdv.service;

import com.nox.JavaBootCampAdv.dto.PaymentRequestDto;
import com.nox.JavaBootCampAdv.dto.SalaryPaymentDto;
import com.nox.JavaBootCampAdv.entity.Employee;
import com.nox.JavaBootCampAdv.entity.SalaryPayment;
import com.nox.JavaBootCampAdv.mapper.SalaryPaymentMapper;
import com.nox.JavaBootCampAdv.repository.EmployeeRepository;
import com.nox.JavaBootCampAdv.repository.SalaryAnalysisCachedRepository;
import com.nox.JavaBootCampAdv.repository.SalaryPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryService {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final SalaryPaymentMapper salaryPaymentMapper;
    private final SalaryPaymentRepository salaryPaymentRepository;
    private final SalaryAnalysisCachedRepository salaryAnalysisCachedRepository;

    @Transactional
    public List<SalaryPaymentDto> payMonthlyPayments(PaymentRequestDto paymentRequest) {
        List<Employee> employees = new ArrayList<>();

        if (paymentRequest.getEmployeeId() != null) {
            Employee employee = employeeService.getEmployeeByIdOrThrow(paymentRequest.getEmployeeId());
            employees.add(employee);
        } else if (paymentRequest.getPositionId() != null) {
            employees = employeeRepository.findByPositionId(paymentRequest.getPositionId());
        } else if (paymentRequest.getCompanyId() != null) {
            employees = employeeRepository.findByCompanyId(paymentRequest.getCompanyId());
        } else {
            employees = employeeRepository.findAll();
        }

        List<SalaryPaymentDto> salaryPayments = new ArrayList<>();
        for (Employee employee : employees) {
            if (hasPaymentThisMonth(employee, paymentRequest.getYear(), paymentRequest.getMonth())) {
                if (Boolean.TRUE.equals(paymentRequest.getThrowIfPaid())) {
                    throw new RuntimeException("Payment for employee id = " + employee.getId() + " already exists this period (%s %s)".formatted(paymentRequest.getMonth(), paymentRequest.getYear()));
                }
            } else {
                SalaryPayment salaryPayment = createSalaryPayment(employee, paymentRequest.getYear(), paymentRequest.getMonth());
                salaryPayments.add(salaryPaymentMapper.toDto(salaryPaymentRepository.save(salaryPayment)));
            }
        }
        salaryAnalysisCachedRepository.invalidateCache(paymentRequest.getYear(), paymentRequest.getMonth());
        return salaryPayments;
    }

    public List<SalaryPaymentDto> getPaymentsByEmployee(Long id, Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        List<SalaryPayment> salaryPayments;
        if (year == null) {
            salaryPayments = salaryPaymentRepository.findByEmployeeId(id);
        } else if (month == null) {
            salaryPayments = salaryPaymentRepository.findByEmployeeIdAndYear(id, year);
        } else {
            salaryPayments = salaryPaymentRepository.findByEmployeeIdAndYearAndMonth(id, year, month);
        }
        return salaryPayments.stream()
                .map(salaryPaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SalaryPaymentDto> getPaymentsByCompany(Long id, Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        List<SalaryPayment> salaryPayments;
        if (year == null) {
            salaryPayments = salaryPaymentRepository.findByCompanyId(id);
        } else if (month == null) {
            salaryPayments = salaryPaymentRepository.findByCompanyIdAndYear(id, year);
        } else {
            salaryPayments = salaryPaymentRepository.findByCompanyIdAndYearAndMonth(id, year, month);
        }
        return salaryPayments.stream()
                .map(salaryPaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SalaryPaymentDto> getPayments(Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        List<SalaryPayment> salaryPayments;
        if (year == null) {
            salaryPayments = salaryPaymentRepository.findAll();
        } else if (month == null) {
            salaryPayments = salaryPaymentRepository.findByYear(year);
        } else {
            salaryPayments = salaryPaymentRepository.findByYearAndMonth(year, month);
        }
        return salaryPayments.stream()
                .map(salaryPaymentMapper::toDto)
                .collect(Collectors.toList());
    }

    public Double getTotalPayments(Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        if (year == null) {
            return salaryPaymentRepository.sumSalary();
        } else if (month == null) {
            return salaryPaymentRepository.sumSalaryByYear(year);
        }
        return salaryPaymentRepository.sumSalaryByYearAndMonth(year, month);
    }

    public Double getAveragePayments(Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        if (year == null) {
            return salaryPaymentRepository.avgSalary();
        } else if (month == null) {
            return salaryPaymentRepository.avgSalaryByYear(year);
        }
        return salaryPaymentRepository.avgSalaryByYearAndMonth(year, month);
    }

    public Double getTotalPaymentsByEmployee(Long id, Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        if (year == null) {
            return salaryPaymentRepository.sumSalaryByEmployee(id);
        } else if (month == null) {
            return salaryPaymentRepository.sumSalaryByEmployeeAndYear(id, year);
        }
        return salaryPaymentRepository.sumSalaryByEmployeeAndYearAndMonth(id, year, month);
    }

    public Double getTotalPaymentsByCompany(Long id, Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        if (year == null) {
            return salaryPaymentRepository.sumSalaryByCompany(id);
        } else if (month == null) {
            return salaryPaymentRepository.sumSalaryByCompanyAndYear(id, year);
        }
        return salaryPaymentRepository.sumSalaryByCompanyAndYearAndMonth(id, year, month);
    }

    public Double getAveragePaymentsByEmployee(Long id, Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        if (year == null) {
            return salaryPaymentRepository.avgSalaryByEmployee(id);
        } else if (month == null) {
            return salaryPaymentRepository.avgSalaryByEmployeeAndYear(id, year);
        }
        return salaryPaymentRepository.avgSalaryByEmployeeAndYearAndMonth(id, year, month);
    }

    public Double getAveragePaymentsByCompany(Long id, Integer year, Month month) {
        if (month != null && year == null) {
            throw new IllegalArgumentException("Year must be provided if month is provided");
        }
        if (year == null) {
            return salaryPaymentRepository.avgSalaryByCompany(id);
        } else if (month == null) {
            return salaryPaymentRepository.avgSalaryByCompanyAndYear(id, year);
        }
        return salaryPaymentRepository.avgSalaryByCompanyAndYearAndMonth(id, year, month);
    }

    private SalaryPayment createSalaryPayment(Employee employee, Integer year, Month month) {
        SalaryPayment salaryPayment = new SalaryPayment();
        salaryPayment.setEmployee(employee);
        salaryPayment.setYear(year);
        salaryPayment.setMonth(month);
        salaryPayment.setAmount(employee.getPosition().getSalary());
        salaryPayment.setPaymentDate(LocalDate.now());
        return salaryPayment;
    }

    private boolean hasPaymentThisMonth(Employee employee, Integer year, Month month) {
        return !salaryPaymentRepository.findByEmployeeIdAndYearAndMonth(employee.getId(), year, month).isEmpty();
    }

    public String getComplexSalaryTrendsAnalysis(Integer year, Month month) {
        long start = System.currentTimeMillis();
        return makeVeryLongAndDeepAnalysis(year, month) + " made in " + (System.currentTimeMillis() - start) + " ms";
    }

    private String makeVeryLongAndDeepAnalysis(Integer year, Month month) {
        return salaryAnalysisCachedRepository.findByYearAndMonth(year, month).orElseGet(
                () -> {
                    log.info("Emulation of the very long analysis for year = {} and month = {}", year, month);
                    try {
                        Thread.sleep(5000); // Emulation of the very long analysis
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String analysisResult = "Very long and deep analysis";
                    if (year != null) {
                        analysisResult += " for year = " + year;
                    }
                    if (month != null) {
                        analysisResult += " and month = " + month;
                    }
                    salaryAnalysisCachedRepository.cacheAnalysisResult(year, month, analysisResult);
                    return analysisResult;
                }
        );
    }
}