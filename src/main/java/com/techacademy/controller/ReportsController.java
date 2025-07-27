package com.techacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Reports;
import com.techacademy.service.ReportsService;
import com.techacademy.service.UserDetail;

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
 }
