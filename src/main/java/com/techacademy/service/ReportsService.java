package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techacademy.entity.Reports;
import com.techacademy.repository.ReportsRepository;

@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    // 日報一覧表示処理
    public List<Reports> findAll() {
        return reportsRepository.findAllByOrderByReportDateDesc();
    }

    public Reports findById(Integer id) {
        return reportsRepository.findById(id).orElse(null);
    }

    public Reports save(Reports report) {
        return reportsRepository.save(report);
    }

    public void delete(Integer id) {
        Reports report = findById(id);
        if(report != null) {
            report.setDeleteFlg(true);
            reportsRepository.save(report);
        }
    }

}
