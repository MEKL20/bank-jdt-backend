package com.bank.jdt.RESTReporting.Repository;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingRepository extends JpaRepository<Reporting, Long> {
    @Query(value =
            "SELECT " +
                    "id," +
                    "customer_id," +
                    "source," +
                    "destination," +
                    "amount," +
                    "balance," +
                    "activity," +
                    "created_at " +
            "FROM reporting " +
                    "WHERE customer_id = (SELECT id FROM customer WHERE username = ?1) " +
                    "ORDER BY created_at DESC",
            nativeQuery = true
    )
    List<Reporting> findByUsername(String username);
}
