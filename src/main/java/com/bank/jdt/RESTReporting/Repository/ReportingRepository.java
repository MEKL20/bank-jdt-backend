package com.bank.jdt.RESTReporting.Repository;

import com.bank.jdt.RESTReporting.Entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingRepository extends JpaRepository<Reporting, Long> {
}
