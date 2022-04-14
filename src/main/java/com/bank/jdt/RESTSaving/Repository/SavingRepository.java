package com.bank.jdt.RESTSaving.Repository;

import com.bank.jdt.RESTSaving.Entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
//    @Query(value = "select account_saving,balance,is_active from saving where customer_id = :id", nativeQuery = true)
//    Saving findByCustomerId(@Param("id") long id);

//    @Query(value = "select account_saving,balance,is_active from saving where account_saving = :id", nativeQuery = true)
//    Saving findByAccountSaving(@Param("id") long id);

    Saving findByCustomerId(long id);
    Saving findByAccountSaving(long accountSaving);
}
