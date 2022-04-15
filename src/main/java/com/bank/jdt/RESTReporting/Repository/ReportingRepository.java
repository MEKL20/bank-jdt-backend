package com.bank.jdt.RESTReporting.Repository;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingRepository extends JpaRepository<Reporting, Long> {
    @Query(value =
            "SELECT " +
                    "id,customer_id," +
                    "activity," +
                    "amount," +
                    "account_type," +
                    "account_saving," +
                    "account_deposit," +
                    "account_destination," +
                    "created_at " +
            "FROM reporting " +
                    "WHERE customer_id = (SELECT id FROM customer WHERE username = ?1)",
            nativeQuery = true
    )
    Reporting findByUsername(String username);
}
