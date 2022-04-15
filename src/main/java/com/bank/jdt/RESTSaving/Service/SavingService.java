package com.bank.jdt.RESTSaving.Service;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import com.bank.jdt.RESTSaving.Entity.Saving;
import com.bank.jdt.RESTSaving.Repository.SavingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SavingService {
    private final SavingRepository savingRepository;

    public SavingService(SavingRepository savingRepository){
        this.savingRepository=savingRepository;
    }

    public Saving addSaving(Customer customer){
        Saving saving=new Saving();
        saving.setCustomerId(customer.getId());
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

    public Saving getSaving(long id){
        return savingRepository.findByCustomerId(id);
    }

    public ResponseEntity<String> savingWithdraw(long id, Saving saving){
        try{
            if (saving.getBalance() < 10000){
                return new ResponseEntity<>("Minimum Withdraw 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByCustomerId(id);
            if ((accountSaving.getBalance() - saving.getBalance()) < 0){
                return new ResponseEntity<>("Balance Is Not Enough", HttpStatus.BAD_REQUEST);
            }
            accountSaving.setBalance(accountSaving.getBalance() - saving.getBalance());
            savingRepository.save(accountSaving);
            return new ResponseEntity<>("Withdraw "+saving.getBalance()+" Success", HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<String> savingTopup(long id, Saving saving){
        try {
            if (saving.getBalance() < 10000){
                return new ResponseEntity<>("Minimum TopUp 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByCustomerId(id);
            accountSaving.setBalance(accountSaving.getBalance() + saving.getBalance());
            savingRepository.save(accountSaving);
            return new ResponseEntity<>("TopUp Success",HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    public ResponseEntity<String> savingTransfer(long id, Saving saving){
        try {
            if (saving.getBalance() < 10000){
                return new ResponseEntity<>("Minimum Transfer 10,000", HttpStatus.BAD_REQUEST);
            }
            Saving accountSaving = savingRepository.findByCustomerId(id);
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
            return new ResponseEntity<>("Transfer Success", HttpStatus.OK);
        }catch (Exception err){
            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
