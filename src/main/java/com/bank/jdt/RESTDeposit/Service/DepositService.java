package com.bank.jdt.RESTDeposit.Service;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import com.bank.jdt.RESTDeposit.Repository.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepositService {
    private final DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public Deposit addDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    public Optional<Deposit> withdrawDeposit(long id) {
        Optional<Deposit> deposit = depositRepository.findById(id);
        deposit.get().setBalance(0);
        deposit.get().setActive(false);

        return deposit;
    }

    public Optional<Deposit> getDeposit(long id) {
        return depositRepository.findById(id);
    }

    public List<Deposit> getDeposits(){
        return depositRepository.findAll();
    }

    public Optional<Deposit> getDepositByCustomerId(long customerId){
        return depositRepository.findDepositByCustomerId(customerId);
    }

    public void deleteDeposit(long id) {
        depositRepository.deleteById(id);
    }
}
