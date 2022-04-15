package com.bank.jdt.RESTReporting.Service;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import com.bank.jdt.RESTReporting.Repository.ReportingRepository;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReportingService {
    private final ReportingRepository reportingRepository;
    private final SavingRepository savingRepository;

    public ReportingService(ReportingRepository reportingRepository, SavingRepository savingRepository){
        this.reportingRepository=reportingRepository;
        this.savingRepository=savingRepository;
    }

    public Reporting getReporting(String username){
        return reportingRepository.findByUsername(username);
    }

    public Reporting reportingWithdraw(Saving saving){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(saving.getCustomerId());
        reporting.setActivity("Withdraw");
        reporting.setBalance(saving.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(saving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return reportingRepository.save(reporting);
    }

    public Reporting reportingTopup(Saving saving){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(saving.getCustomerId());
        reporting.setActivity("TopUp");
        reporting.setBalance(saving.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(saving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return reportingRepository.save(reporting);
    }

    public Reporting reportingTransfer(String username, Saving saving){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(saving.getCustomerId());
        reporting.setActivity("Transfer");
        reporting.setBalance(saving.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(savingRepository.findByUsername(username).getAccountSaving());
        reporting.setAccountDestination(saving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return reportingRepository.save(reporting);
    }

}
