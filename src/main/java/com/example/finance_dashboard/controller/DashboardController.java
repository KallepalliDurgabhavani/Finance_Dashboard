package com.example.finance_dashboard.controller;

import com.example.finance_dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalIncome",    dashboardService.getTotalIncome());
        model.addAttribute("totalExpenses",  dashboardService.getTotalExpenses());
        model.addAttribute("netBalance",     dashboardService.getNetBalance());
        model.addAttribute("categoryTotals", dashboardService.getCategoryTotals());
        model.addAttribute("monthlyIncome",  dashboardService.getMonthlyTotals("INCOME"));
        model.addAttribute("monthlyExpense", dashboardService.getMonthlyTotals("EXPENSE"));
        return "dashboard";
    }
}