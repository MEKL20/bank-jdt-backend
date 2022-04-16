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
        reporting.setSource(accountSaving.getAccountSaving());
        reporting.setDestination(2);
        reporting.setAmount(transaction.getBalance());
        reporting.setBalance(accountSaving.getBalance());
        reporting.setActivity("Withdraw");
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

    public void reportingTopup(Saving accountSaving, Saving transaction){
        Reporting reporting = new Reporting();
        reporting.setCustomerId(accountSaving.getCustomerId());
        reporting.setSource(1);
        reporting.setDestination(accountSaving.getAccountSaving());
        reporting.setAmount(transaction.getBalance());
        reporting.setBalance(accountSaving.getBalance());
        reporting.setActivity("TopUp");
        reporting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reporting);
    }

    public void reportingTransfer(Saving source, long amount, Saving destination){
        Reporting reportingSource = new Reporting();
        reportingSource.setCustomerId(source.getCustomerId());
        reportingSource.setSource(source.getAccountSaving());
        reportingSource.setDestination(destination.getAccountSaving());
        reportingSource.setAmount(amount);
        reportingSource.setBalance(source.getBalance());
        reportingSource.setActivity("Transfer");
        reportingSource.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reportingSource);

        Reporting reportingDestination = new Reporting();
        reportingDestination.setCustomerId(destination.getCustomerId());
        reportingDestination.setSource(source.getAccountSaving());
        reportingDestination.setDestination(destination.getAccountSaving());
        reportingDestination.setAmount(amount);
        reportingDestination.setBalance(destination.getBalance());
        reportingDestination.setActivity("Transfer");
        reportingDestination.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        reportingRepository.save(reportingDestination);
    }

}
