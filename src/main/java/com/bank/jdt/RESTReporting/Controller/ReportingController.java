package com.bank.jdt.RESTReporting.Controller;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import com.bank.jdt.RESTReporting.Service.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporting")
public class ReportingController {
    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService){
        this.reportingService=reportingService;
    }

    @GetMapping("/{username}")
    public Reporting getReporting(@PathVariable String username){
        return reportingService.getReporting(username);
    }
}
