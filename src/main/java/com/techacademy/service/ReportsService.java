package com.techacademy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Reports;
import com.techacademy.repository.ReportsRepository;

@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    // 日報一覧表示処理
    public List<Reports> getReportsList(Employee loginUser) {
        if(loginUser.getRole() == Employee.Role.ADMIN) {
            return reportsRepository.findByDeleteFlgFalse()  ;

        }else {
        return reportsRepository.findByEmployeeAndDeleteFlgFalse(loginUser);
    }
    }

    public Reports findById(Integer id) {
        return reportsRepository.findById(id).orElse(null);
    }

    // 日報保存
    @Transactional
    public ErrorKinds save(Reports reports) {
        try {
        LocalDateTime now = LocalDateTime.now() ;

        if(reports.getId() == null) {
            reports.setCreatedAt(now);
        }
        reports.setUpdatedAt(now);

        reportsRepository.save(reports);

        return ErrorKinds.SUCCESS;
        }catch(DataIntegrityViolationException e) {
            return ErrorKinds.DUPLICATE_EXCEPTION_ERROR;
        }catch(Exception e) {
            return ErrorKinds.UNKNOWN_ERROR;
        }
    }

    public void delete(Integer id) {
        Reports reports = findById(id);
        if(reports != null) {
            reports.setDeleteFlg(true);
            reportsRepository.save(reports);
        }
    }

 // 日報更新
    @Transactional
    public ErrorKinds update(Reports reports,Integer id) {
        Reports dbReports =findById(id);

        if(dbReports == null) {
            return ErrorKinds.NOTFOUND_ERROR;
        }


        dbReports.setTitle(reports.getTitle());
        dbReports.setReportDate(reports.getReportDate());
        dbReports.setContent(reports.getContent());

        dbReports.setUpdatedAt(LocalDateTime.now());

        reportsRepository.save(dbReports);
        return ErrorKinds.SUCCESS;

        }

 // 日報削除
    @Transactional
    public ErrorKinds delete(Integer id, UserDetail userDetail) {

        // 自分を削除しようとした場合はエラーメッセージを表示
        /*if (code.equals(userDetail.getEmployee().getCode())) {
            return ErrorKinds.LOGINCHECK_ERROR;
        }*/
        Reports reports = findById(id);
        LocalDateTime now = LocalDateTime.now();
        reports.setUpdatedAt(now);
        reports.setDeleteFlg(true);

        return ErrorKinds.SUCCESS;
    }
    public List<Reports> findByEmployee(Employee employee){
        return reportsRepository.findByEmployee(employee);
    }
    public boolean isDuplicateReport(Employee employee,LocalDate reportDate) {
        return reportsRepository.existsByEmployeeAndReportDateAndDeleteFlgFalse(employee,reportDate);

    }
    public boolean isDuplicateReportOnUpdate(Employee targetEmployee,LocalDate reportDate,Integer currentReportId) {
        return reportsRepository.existsByEmployeeAndReportDateAndIdNotAndDeleteFlgFalse(targetEmployee,reportDate,currentReportId);
    }
}
