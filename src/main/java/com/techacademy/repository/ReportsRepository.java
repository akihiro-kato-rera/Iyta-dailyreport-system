package com.techacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Reports;

public interface ReportsRepository extends JpaRepository<Reports,Integer>  {
    List<Reports>findAllByOrderByReportDateDesc();

    List<Reports> findByEmployee(Employee employee);
    boolean existsByEmployeeAndReportDateAndDeleteFlgFalse(Employee employee, LocalDate reportDate);

    boolean existsByEmployeeAndReportDateAndIdNotAndDeleteFlgFalse(Employee employee, LocalDate reportDate,
            Integer Id);

    List<Reports> findByDeleteFlgFalse();

    List<Reports> findByEmployeeAndDeleteFlgFalse(Employee employeee);
}
