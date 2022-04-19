package com.bank.jdt.RESTReporting.Controller;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import com.bank.jdt.RESTReporting.Service.ReportingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/reporting")
public class ReportingController {
    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService){
        this.reportingService=reportingService;
    }

    @GetMapping("/{username}")
    public List<Reporting> getReporting(@PathVariable String username){
        return reportingService.getReporting(username);
    }
}
