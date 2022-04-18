package com.bank.jdt.RESTDeposit.Repository;

import com.bank.jdt.RESTDeposit.Entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findDepositByCustomerId(long customerId);

    Optional<Deposit> findDepositByAccountDeposit(long accountDeposit);

    Deposit getDepositByAccountDeposit(long accountDeposit);

    List<Deposit> findDepositsByCustomerId(long customerId);

    @Query(value = "SELECT * FROM deposit WHERE expired_at < :expiredAt AND is_active = 1",nativeQuery = true)
    List<Deposit>findExpiredDeposit(@Param("expiredAt") String expiredAt);


}
