package com.nox.JavaBootCampAdv.repository;

import com.nox.JavaBootCampAdv.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e join Position p on e.position.id = p.id WHERE p.company.id = :companyId")
    List<Employee> findByCompanyId(Long companyId);

    List<Employee> findByPositionId(Long id);
}
