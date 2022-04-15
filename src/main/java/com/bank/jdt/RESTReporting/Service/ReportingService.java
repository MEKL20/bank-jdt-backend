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

    public Reporting getReporting(String username){
        return reportingRepository.findByUsername(username);
    }

    public ReportingService(ReportingRepository reportingRepository, SavingRepository savingRepository){
        this.reportingRepository=reportingRepository;
        this.savingRepository=savingRepository;
    }

    public void reportingWithdraw(Saving saving){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(saving.getCustomerId());
        reporting.setActivity("Withdraw");
        reporting.setAmount(saving.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(saving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

    public void reportingTopup(Saving saving){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(saving.getCustomerId());
        reporting.setActivity("TopUp");
        reporting.setAmount(saving.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(saving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

    public void reportingTransfer(String username, Saving saving){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(saving.getCustomerId());
        reporting.setActivity("Transfer");
        reporting.setAmount(saving.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(savingRepository.findByUsername(username).getAccountSaving());
        reporting.setAccountDestination(saving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

}
