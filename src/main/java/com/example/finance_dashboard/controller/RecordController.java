package com.example.finance_dashboard.controller;

import com.example.finance_dashboard.model.*;
import com.example.finance_dashboard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private FinancialRecordServiceImpl recordService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listRecords(@RequestParam(required = false) String type,
                              @RequestParam(required = false) String category,
                              Model model) {
        if (type != null && !type.isEmpty()) {
            model.addAttribute("records", recordService.filterByType(type));
        } else if (category != null && !category.isEmpty()) {
            model.addAttribute("records", recordService.filterByCategory(category));
        } else {
            model.addAttribute("records", recordService.findAll());
        }
        model.addAttribute("filterType", type);
        return "records/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public String newRecordForm(Model model) {
        model.addAttribute("record", new FinancialRecord());
        return "records/form";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public String saveRecord(@ModelAttribute FinancialRecord record,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        recordService.save(record, user);
        return "redirect:/records";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("record", recordService.findById(id));
        return "records/form";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public String updateRecord(@PathVariable Long id,
                               @ModelAttribute FinancialRecord record) {
        recordService.update(id, record);
        return "redirect:/records";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecord(@PathVariable Long id) {
        recordService.softDelete(id);
        return "redirect:/records";
    }
}