package com.bank.jdt.RESTReporting.Service;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import com.bank.jdt.RESTDeposit.Repository.DepositRepository;
import com.bank.jdt.RESTReporting.Entity.Reporting;
import com.bank.jdt.RESTReporting.Repository.ReportingRepository;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ReportingService {
    private final ReportingRepository reportingRepository;
    private final SavingRepository savingRepository;

    Timestamp now = new Timestamp(System.currentTimeMillis());

    public ReportingService(ReportingRepository reportingRepository, SavingRepository savingRepository) {
        this.reportingRepository = reportingRepository;
        this.savingRepository = savingRepository;
    }

    public List<Reporting> getReporting(String username) {
        return reportingRepository.findByUsername(username);
    }

    public void reportingWithdraw(Saving accountSaving, Saving transaction) {
        Reporting reporting = new Reporting();
        reporting.setCustomer(accountSaving.getCustomer());
        reporting.setSource(accountSaving.getAccountSaving());
        reporting.setDestination(2);
        reporting.setAmount(transaction.getBalance());
        reporting.setBalance(accountSaving.getBalance());
        reporting.setActivity("Withdraw");
        reporting.setCreatedAt(now);
        reportingRepository.save(reporting);
    }

    public void reportingTopup(Saving accountSaving, Saving transaction) {
        Reporting reporting = new Reporting();
        reporting.setCustomer(accountSaving.getCustomer());
        reporting.setSource(1);
        reporting.setDestination(accountSaving.getAccountSaving());
        reporting.setAmount(transaction.getBalance());
        reporting.setBalance(accountSaving.getBalance());
        reporting.setActivity("TopUp");
        reporting.setCreatedAt(now);
        reportingRepository.save(reporting);
    }

    public void reportingTransfer(Saving source, long amount, Saving destination) {
        Reporting reportingSource = new Reporting();
        reportingSource.setCustomer(source.getCustomer());
        reportingSource.setSource(source.getAccountSaving());
        reportingSource.setDestination(destination.getAccountSaving());
        reportingSource.setAmount(amount);
        reportingSource.setBalance(source.getBalance());
        reportingSource.setActivity("Transfer");
        reportingSource.setCreatedAt(now);
        reportingRepository.save(reportingSource);

        Reporting reportingDestination = new Reporting();
        reportingDestination.setCustomer(destination.getCustomer());
        reportingDestination.setSource(source.getAccountSaving());
        reportingDestination.setDestination(destination.getAccountSaving());
        reportingDestination.setAmount(amount);
        reportingDestination.setBalance(destination.getBalance());
        reportingDestination.setActivity("Transfer");
        reportingDestination.setCreatedAt(now);
        reportingRepository.save(reportingDestination);
    }

    public void reportingDepositIn(Long accountDeposit, long amount, Long accountSaving) {
        Reporting reportingDestination = new Reporting();
        Saving saving = savingRepository.findByAccountSaving(accountSaving);
        reportingDestination.setCustomer(saving.getCustomer());
        reportingDestination.setSource(accountDeposit);
        reportingDestination.setDestination(accountSaving);
        reportingDestination.setAmount(amount);
        reportingDestination.setBalance(saving.getBalance());
        reportingDestination.setActivity("Deposit In");
        reportingDestination.setCreatedAt(now);
        reportingRepository.save(reportingDestination);
    }

    public void reportingDepositOut(Long accountSaving, long amount, Long accountDeposit) {
        Reporting reportingDestination = new Reporting();
        Saving saving = savingRepository.findByAccountSaving(accountSaving);
        reportingDestination.setCustomer(saving.getCustomer());
        reportingDestination.setSource(accountSaving);
        reportingDestination.setDestination(accountDeposit);
        reportingDestination.setAmount(amount);
        reportingDestination.setBalance(saving.getBalance());
        reportingDestination.setActivity("Deposit Out");
        reportingDestination.setCreatedAt(now);
        reportingRepository.save(reportingDestination);
    }


}
