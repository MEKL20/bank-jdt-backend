package com.bank.jdt.RESTDeposit.Controller;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import com.bank.jdt.RESTDeposit.Service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/Deposit")
public class DepositController {
    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/{username}/add")
    public ResponseEntity<String> addDeposit(@PathVariable("username") String username, @RequestBody Deposit deposit) {
        return depositService.addDeposit(username, deposit);
    }

    @PutMapping("/{account_deposit}/withdraw")
    public ResponseEntity<String> withdrawDeposit(@PathVariable("account_deposit") long accountDeposit) {
        return depositService.withdrawDeposit(accountDeposit);
    }

    @GetMapping("/{account_deposit}/get")
    public Optional<Deposit> getDeposit(@PathVariable("account_deposit") long accountDeposit) {
        return depositService.getDeposit(accountDeposit);
    }

    @GetMapping("/get")
    public List<Deposit> getDeposits() {
        return depositService.getDeposits();
    }

    @GetMapping("/{username}/getByUsername")
    public List<Deposit> getDepositsByUsername(@PathVariable("username") String username) {
        return depositService.getDepositsByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDeposit(@PathVariable("id") long id) {
        depositService.deleteDeposit(id);
        return "Customer with id :" + id + " has been deleted";
    }

}
