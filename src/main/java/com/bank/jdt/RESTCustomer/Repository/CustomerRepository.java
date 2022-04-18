package com.bank.jdt.RESTCustomer.Repository;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    Customer findByIdentityCard(long identityCard);

    Customer getByUsername(String username);
}
