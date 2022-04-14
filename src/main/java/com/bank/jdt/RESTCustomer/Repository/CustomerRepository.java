package com.bank.jdt.RESTCustomer.Repository;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUsername(String username);
    Customer findByIdentityCard(long identityCard);
}
