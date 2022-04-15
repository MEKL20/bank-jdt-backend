package com.bank.jdt.RESTSaving.Repository;

import com.bank.jdt.RESTSaving.Entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
    Saving findByCustomerId(long id);
    Saving findByAccountSaving(long accountSaving);
//    @Query(value =
//            "SELECT customer_id,account_saving,balance,is_active FROM saving WHERE customer_id = (SELECT id FROM customer WHERE username = :username)",
//            nativeQuery = true
//    )
//    Saving findByUsername(@Param("username") String username);
}
