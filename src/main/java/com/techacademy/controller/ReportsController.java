package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("reports")
public class ReportsController {

   /*private final ReportsService reportsService;

   @Autowired
   public ReportsController(ReportsService reportsService) {
       this.reportsService = reportsService;
   }*/

 // 従業員一覧画面
    @GetMapping
    public String list(Model model) {

        //model.addAttribute("listSize", employeeService.findAll().size());
        //model.addAttribute("employeeList", employeeService.findAll());

        return "reports/list";
    }
}
