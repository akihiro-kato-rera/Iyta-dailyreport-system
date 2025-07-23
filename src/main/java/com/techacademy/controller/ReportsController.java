package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;




@Controller
@RequestMapping("reports")
public class ReportsController {

  /* private final ReportsService reportsService;

   @Autowired
   public ReportsController(ReportsService reportsService) {
       this.reportsService = reportsService;
   }
*/
 // 日報一覧画面
    @GetMapping
    public String list(Model model) {

        //model.addAttribute("listSize", reportsService.findAll().size());
       //model.addAttribute("reportsList", reportsService.findAll());

        return "reports/list";
    }



 }
