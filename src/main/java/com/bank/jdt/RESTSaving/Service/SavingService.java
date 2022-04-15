package com.bank.jdt.RESTSaving.Service;

import com.bank.jdt.RESTReporting.Service.ReportingService;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SavingService {
    private final SavingRepository savingRepository;
    private final ReportingService reportingService;

    public SavingService(SavingRepository savingRepository, ReportingService reportingService){
        this.savingRepository = savingRepository;
        this.reportingService = reportingService;
    }

    public Saving getSaving(String username){
        return savingRepository.findByUsername(username);
    }

    public ResponseEntity<String> savingWithdraw(String username, Saving saving){
        try{
            if (saving.getBalance() < 10000){
                return new ResponseEntity<>("Minimum Withdraw 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByUsername(username);
            if ((accountSaving.getBalance() - saving.getBalance()) < 0){
                return new ResponseEntity<>("Balance Is Not Enough", HttpStatus.BAD_REQUEST);
            }
            accountSaving.setBalance(accountSaving.getBalance() - saving.getBalance());
            savingRepository.save(accountSaving);
            reportingService.reportingWithdraw(saving);
            return new ResponseEntity<>("Withdraw "+saving.getBalance()+" Success", HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<String> savingTopup(String username, Saving saving){
        try {
            if (saving.getBalance() < 10000){
                return new ResponseEntity<>("Minimum TopUp 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByUsername(username);
            accountSaving.setBalance(accountSaving.getBalance() + saving.getBalance());
            savingRepository.save(accountSaving);
            reportingService.reportingTopup(saving);
            return new ResponseEntity<>("TopUp Success",HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<String> savingTransfer(String username, Saving saving){
        try {
            if (saving.getBalance() < 10000){
                return new ResponseEntity<>("Minimum Transfer 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByUsername(username);
            Saving accountDestination = savingRepository.findByAccountSaving(saving.getAccountSaving());
            if (accountDestination == null) {
                return new ResponseEntity<>("Account Saving Destination Not Found", HttpStatus.BAD_REQUEST);
            }
            if ((accountSaving.getBalance() - saving.getBalance()) < 0) {
                return new ResponseEntity<>("Balance Is Not Enough", HttpStatus.BAD_REQUEST);
            }
            accountSaving.setBalance(accountSaving.getBalance() - saving.getBalance());
            accountDestination.setBalance(accountDestination.getBalance() + saving.getBalance());
            savingRepository.save(accountSaving);
            reportingService.reportingTransfer(username, saving);
            return new ResponseEntity<>("Transfer Success", HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
