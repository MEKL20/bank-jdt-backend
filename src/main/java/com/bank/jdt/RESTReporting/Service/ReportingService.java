package com.bank.jdt.RESTReporting.Service;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import com.bank.jdt.RESTReporting.Repository.ReportingRepository;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReportingService {
    private final ReportingRepository reportingRepository;

    public ReportingService(ReportingRepository reportingRepository){
        this.reportingRepository=reportingRepository;
    }

    public List<Reporting> getReporting(String username){
        return reportingRepository.findByUsername(username);
    }

    public void reportingWithdraw(Saving accountSaving, Saving transaction){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(accountSaving.getCustomerId());
        reporting.setActivity("Withdraw");
        reporting.setBalance(transaction.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(accountSaving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

    public void reportingTopup(Saving accountSaving, Saving transaction){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(accountSaving.getCustomerId());
        reporting.setActivity("TopUp");
        reporting.setBalance(transaction.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(accountSaving.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

    public void reportingTransfer(Saving accountSaving, Saving transaction){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(accountSaving.getCustomerId());
        reporting.setActivity("Transfer");
        reporting.setBalance(transaction.getBalance());
        reporting.setAccountType("Saving");
        reporting.setAccountSaving(accountSaving.getAccountSaving());
        reporting.setAccountDestination(transaction.getAccountSaving());
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

}
