package com.techacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Reports;
import com.techacademy.service.ReportsService;

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



 }
