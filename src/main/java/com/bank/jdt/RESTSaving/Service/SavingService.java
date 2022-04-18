package com.bank.jdt.RESTSaving.Service;

import com.bank.jdt.RESTReporting.Service.ReportingService;
import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SavingService {
    private final SavingRepository savingRepository;
    private final ReportingService reportingService;

    public SavingService(SavingRepository savingRepository, ReportingService reportingService){
        this.savingRepository = savingRepository;
        this.reportingService = reportingService;
    }

    public Saving addSaving(Customer customer){
        Saving saving=new Saving();
        saving.setCustomer(customer);
        saving.setActive(true);
        while (true){
            long randomNum = (long) (Math.random()*Math.pow(10,10));
            if (savingRepository.findByAccountSaving(randomNum) == null){
                saving.setAccountSaving(randomNum);
                break;
            }
        }
        saving.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return savingRepository.save(saving);
    }

    public Saving getSaving(String username){
        return savingRepository.findByUsername(username);
    }

    public ResponseEntity savingWithdraw(String username, Saving saving){
        JSONObject res= new JSONObject();
        JSONObject result = new JSONObject();
        try{
            if (saving.getBalance() < 10000){
                res.put("message","Minimum Withdraw 10,000");
                return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByUsername(username);
            if ((accountSaving.getBalance() - saving.getBalance()) < 0){
                res.put("message","Balance Is Not Enough");
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            accountSaving.setBalance(accountSaving.getBalance() - saving.getBalance());
            savingRepository.save(accountSaving);
            reportingService.reportingWithdraw(accountSaving, saving);
            result.put("username", accountSaving.getCustomer().getUsername());
            result.put("accountSaving", accountSaving.getAccountSaving());
            result.put("balance", accountSaving.getBalance());
            res.put("message","Withdraw "+saving.getBalance()+" Success");
            res.put("result", result);
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception err){
            res.put("message",err.getMessage());
            return new ResponseEntity(res, HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity savingTopup(String username, Saving saving){
        JSONObject res= new JSONObject();
        JSONObject result = new JSONObject();
        try {
            if (saving.getBalance() < 10000){
                res.put("message","Minimum TopUp 10,000");
                return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByUsername(username);
            accountSaving.setBalance(accountSaving.getBalance() + saving.getBalance());
            savingRepository.save(accountSaving);
            reportingService.reportingTopup(accountSaving, saving);
            result.put("username", accountSaving.getCustomer().getUsername());
            result.put("accountSaving", accountSaving.getAccountSaving());
            result.put("balance", accountSaving.getBalance());
            res.put("message","TopUp "+saving.getBalance()+" Success");
            res.put("result", result);
            return new ResponseEntity(res,HttpStatus.OK);
        }catch (Exception err){
            res.put("message",err.getMessage());
            return new ResponseEntity(res, HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity savingTransfer(String username, Saving saving){
        JSONObject res= new JSONObject();
        JSONObject result = new JSONObject();
        try {
            if (saving.getBalance() < 10000){
                res.put("message","Minimum Transfer 10,000");
                return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
            }
            Saving source = savingRepository.findByUsername(username);
            Saving destination = savingRepository.findByAccountSaving(saving.getAccountSaving());
            if (destination == null) {
                res.put("message","Account Saving Destination Not Found");
                return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
            }
            if ((source.getBalance() - saving.getBalance()) < 0) {
                res.put("message","Balance Is Not Enough");
                return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
            }
            source.setBalance(source.getBalance() - saving.getBalance());
            savingRepository.save(source);
            destination.setBalance(destination.getBalance() + saving.getBalance());
            savingRepository.save(destination);
            reportingService.reportingTransfer(source, saving.getBalance(), destination);
            result.put("username", source.getCustomer().getUsername());
            result.put("accountSaving", source.getAccountSaving());
            result.put("balance", source.getBalance());
            res.put("message","Transfer "+saving.getBalance()+"To "+destination.getAccountSaving()+" Success");
            res.put("result", result);
            return new ResponseEntity(res, HttpStatus.OK);
        }catch (Exception err){
            res.put("message",err.getMessage());
            return new ResponseEntity(res, HttpStatus.BAD_GATEWAY);
        }
    }

}
