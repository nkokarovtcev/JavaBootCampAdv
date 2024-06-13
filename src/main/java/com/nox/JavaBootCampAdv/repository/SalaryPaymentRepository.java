package com.nox.JavaBootCampAdv.repository;

import com.nox.JavaBootCampAdv.entity.SalaryPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface SalaryPaymentRepository extends JpaRepository<SalaryPayment, Long> {
    List<SalaryPayment> findByEmployeeId(Long employeeId);

    List<SalaryPayment> findByEmployeeIdAndYearAndMonth(Long employeeId, Integer year, Month month);

    List<SalaryPayment> findByEmployeeIdAndYear(Long employeeId, Integer year);

    List<SalaryPayment> findByYearAndMonth(Integer year, Month month);

    List<SalaryPayment> findByYear(Integer year);

    @Query(value = "SELECT SUM(amount) FROM salary_payments WHERE year = :year AND month = :month", nativeQuery = true)
    Double sumSalaryByYearAndMonth(Integer year, Month month);

    @Query(value = "SELECT SUM(amount) FROM salary_payments WHERE year = :year", nativeQuery = true)
    Double sumSalaryByYear(Integer year);

    @Query(value = "SELECT SUM(amount) FROM salary_payments", nativeQuery = true)
    Double sumSalary();

    @Query(value = "SELECT SUM(amount) FROM salary_payments WHERE employee_id = :employeeId AND year = :year AND month = :month", nativeQuery = true)
    Double sumSalaryByEmployeeAndYearAndMonth(Long employeeId, Integer year, Month month);

    @Query(value = "SELECT SUM(amount) FROM salary_payments WHERE employee_id = :employeeId AND year = :year", nativeQuery = true)
    Double sumSalaryByEmployeeAndYear(Long employeeId, Integer year);

    @Query(value = "SELECT SUM(amount) FROM salary_payments WHERE employee_id = :employeeId", nativeQuery = true)
    Double sumSalaryByEmployee(Long employeeId);

    @Query(value = "SELECT AVG(amount) FROM salary_payments WHERE year = :year AND month = :month", nativeQuery = true)
    Double avgSalaryByYearAndMonth(Integer year, Month month);

    @Query(value = "SELECT AVG(amount) FROM salary_payments WHERE year = :year", nativeQuery = true)
    Double avgSalaryByYear(Integer year);

    @Query(value = "SELECT AVG(amount) FROM salary_payments", nativeQuery = true)
    Double avgSalary();

    @Query(value = "SELECT AVG(amount) FROM salary_payments WHERE employee_id = :employeeId AND year = :year AND month = :month", nativeQuery = true)
    Double avgSalaryByEmployeeAndYearAndMonth(Long employeeId, Integer year, Month month);

    @Query(value = "SELECT AVG(amount) FROM salary_payments WHERE employee_id = :employeeId AND year = :year", nativeQuery = true)
    Double avgSalaryByEmployeeAndYear(Long employeeId, Integer year);

    @Query(value = "SELECT AVG(amount) FROM salary_payments WHERE employee_id = :employeeId", nativeQuery = true)
    Double avgSalaryByEmployee(Long employeeId);

    @Query(value = "SELECT SUM(amount) FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId AND sp.year = :year AND sp.month = :month", nativeQuery = true)
    Double sumSalaryByCompanyAndYearAndMonth(Long companyId, Integer year, Month month);

    @Query(value = "SELECT SUM(amount) FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId AND sp.year = :year", nativeQuery = true)
    Double sumSalaryByCompanyAndYear(Long companyId, Integer year);

    @Query(value = "SELECT SUM(amount) FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId", nativeQuery = true)
    Double sumSalaryByCompany(Long companyId);

    @Query(value = "SELECT AVG(amount) FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId AND sp.year = :year AND sp.month = :month", nativeQuery = true)
    Double avgSalaryByCompanyAndYearAndMonth(Long companyId, Integer year, Month month);

    @Query(value = "SELECT AVG(amount) FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId AND sp.year = :year", nativeQuery = true)
    Double avgSalaryByCompanyAndYear(Long companyId, Integer year);

    @Query(value = "SELECT AVG(amount) FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId", nativeQuery = true)
    Double avgSalaryByCompany(Long companyId);

    @Query(value = "SELECT sp.* FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId AND sp.year = :year AND sp.month = :month", nativeQuery = true)
    List<SalaryPayment> findByCompanyIdAndYearAndMonth(Long companyId, Integer year, Month month);

    @Query(value = "SELECT sp.* FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId AND sp.year = :year", nativeQuery = true)
    List<SalaryPayment> findByCompanyIdAndYear(Long companyId, Integer year);

    @Query(value = "SELECT sp.* FROM salary_payments sp JOIN employees e ON sp.employee_id = e.id JOIN positions p ON e.position_id = p.id WHERE p.company_id = :companyId", nativeQuery = true)
    List<SalaryPayment> findByCompanyId(Long companyId);
}