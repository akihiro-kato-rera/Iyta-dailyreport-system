package com.techacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Reports;
import com.techacademy.service.ReportsService;
import com.techacademy.service.UserDetail;

import jakarta.validation.Valid;

@Controller
@RequestMapping("reports")
public class ReportsController {

   private final ReportsService reportsService;

   @Autowired
   public ReportsController(ReportsService reportsService) {
       this.reportsService = reportsService;
   }

 // 日報一覧画面
    @GetMapping
    public String list(Model model) {

        List<Reports> reportsList = reportsService.findAll();
        model.addAttribute("listSize", reportsList.size());
        model.addAttribute("reportsList", reportsList);

        return "reports/list";
    }

    // 日報詳細画面
    @GetMapping(value = "/{id}/")
    public String detail(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("reports", reportsService.findById(id));
        return "reports/detail";
    }

    // 日報新規登録画面
    @GetMapping(value = "/add")
    public String create(@ModelAttribute Reports reports,Model model,@AuthenticationPrincipal UserDetail userDetail) {

        String loginUserName = userDetail.getEmployee().getName();
        model.addAttribute("loginUserName",loginUserName);

        return "reports/new";
    }

    //日報新規登録処理
    @PostMapping(value = "/add")
    public String add(@ModelAttribute @Valid Reports reports, BindingResult res,
                                      @AuthenticationPrincipal UserDetail userDetail,Model model) {

            String loginUserName = userDetail.getEmployee().getName();
            model.addAttribute("Reports",reports);
            model.addAttribute("loginUserName",loginUserName);

            if(res.hasErrors()) {
            return "reports/new";
    }
            if(reportsService.isDuplicateReport(userDetail.getEmployee(),reports.getReportDate())) {
                model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DATECHECK_ERROR),ErrorMessage.getErrorValue(ErrorKinds.DATECHECK_ERROR));
                return "reports/new";
            }

            reports.setEmployee(userDetail.getEmployee());

            ErrorKinds result = reportsService.save(reports);

            if (ErrorMessage.contains(result)) {

                model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
                return "reports/new";
            }


        return "redirect:/reports";
}



    //　日報更新画面
    @GetMapping(value = "/{id}/update")
    public String edit(@PathVariable("id") Integer id,Model model,@AuthenticationPrincipal UserDetail userDetail) {
        if(id != null) {

        String loginUserName = userDetail.getEmployee().getName();
        model.addAttribute("loginUserName",loginUserName);
        model.addAttribute("reports",reportsService.findById(id));
        model.addAttribute("id",id);

        return "reports/update";
    }else {
        return "reports/update";
        }
    }

    // 日報更新処理
    @PostMapping(value = "/{id}/update")
    public String update(@PathVariable("id") Integer id,@Validated Reports reports, BindingResult res, Model model,@AuthenticationPrincipal UserDetail userDetail) {


        String loginUserName = userDetail.getEmployee().getName();
        model.addAttribute("loginUserName",loginUserName);
        model.addAttribute("reports",reports);

        // 入力チェック
        if (res.hasErrors()) {
            return "reports/update";
        }

        if(reportsService.isDuplicateReportOnUpdate(reports.getEmployee(), reports.getReportDate(), id)){
            model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DATECHECK_ERROR),ErrorMessage.getErrorValue(ErrorKinds.DATECHECK_ERROR));
            return "reports/update";
        }
            ErrorKinds result = reportsService.update(reports,id);

            if (ErrorMessage.contains(result)) {
                model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
                model.addAttribute("loginUserName",userDetail.getEmployee().getName());
                model.addAttribute("reports",reports);
                return "reports/update";
            }
        return "redirect:/reports";
    }

    //日報削除処理
    @PostMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") Integer id, @AuthenticationPrincipal UserDetail userDetail, Model model) {

        ErrorKinds result = reportsService.delete(id, userDetail);

        if (ErrorMessage.contains(result)) {
            model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
            model.addAttribute("reports", reportsService.findById(id));
            return detail(id, model);
        }

        return "redirect:/reports";
    }


}