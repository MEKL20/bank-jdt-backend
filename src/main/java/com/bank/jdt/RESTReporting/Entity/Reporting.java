package com.bank.jdt.RESTReporting.Entity;

import com.bank.jdt.RESTCustomer.Entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Reporting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "reporting_seq")
    @ApiModelProperty(hidden = true)
    private long id;
    @ManyToOne
    private Customer customer;
    private long source;
    private long destination;
    private long amount;
    private long balance;
    private String activity;
    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    public Reporting(){}

    public Reporting(Customer customer, long source, long destination, long amount, long balance, String activity){
        this.customer=customer;
        this.source=source;
        this.destination=destination;
        this.amount=amount;
        this.balance=balance;
        this.activity=activity;
    }
}
