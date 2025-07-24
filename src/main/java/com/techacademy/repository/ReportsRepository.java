package com.techacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.techacademy.entity.Reports;

public interface ReportsRepository extends JpaRepository<Reports,Integer>  {
    List<Reports>findAllByOrderByReportDateDesc();
}
