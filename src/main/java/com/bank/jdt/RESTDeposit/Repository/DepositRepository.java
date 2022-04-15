package com.bank.jdt.RESTDeposit.Repository;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findDepositByCustomerId(long customerId);
}
