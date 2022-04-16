package com.bank.jdt.RESTDeposit.Controller;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import com.bank.jdt.RESTDeposit.Service.DepositService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Deposit")
public class DepositController {
    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/add")
    public Deposit addDeposit(@RequestBody Deposit deposit) {
        return depositService.addDeposit(deposit);
    }

    @PutMapping("/withdraw/{id}")
    public Deposit withdrawDeposit(@PathVariable("id") long id) {
        return depositService.withdrawDeposit(id);
    }

    @GetMapping("/get/{id}")
    public Optional<Deposit> getDeposit(@PathVariable("id") long id) {
        return depositService.getDeposit(id);
    }

    @GetMapping("/get")
    public List<Deposit> getDeposits() {
        return depositService.getDeposits();
    }

    @GetMapping("/getByCustomerId/{customer_id}")
    public Optional<Deposit> getDepositByCustomerId(@PathVariable("customer_id") long customerId) {
        return depositService.getDepositByCustomerId(customerId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDeposit(@PathVariable("id") long id) {
        depositService.deleteDeposit(id);
        return "Customer with id :" + id + " has been deleted";
    }

}
