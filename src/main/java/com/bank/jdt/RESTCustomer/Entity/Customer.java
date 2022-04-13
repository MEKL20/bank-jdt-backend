package com.bank.jdt.RESTCustomer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }


}
