package com.bank.jdt.RESTReporting.Entity;

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
    @SequenceGenerator(name = "seq", sequenceName = "customer_seq")
    @ApiModelProperty(hidden = true)
    private long id;
    @Column(name = "customer_id")
    private long customerId;
    private String activity;
    private long balance;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "account_saving")
    private long accountSaving;
    @Column(name = "account_deposit")
    private long accountDeposit;
    @Column(name = "account_destination")
    private long accountDestination;
    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    public Reporting(){}

    public Reporting(long customerId, String activity, long balance, String accountType, long accountSaving, long accountDeposit, long accountDestination){
        this.customerId=customerId;
        this.activity=activity;
        this.balance=balance;
        this.accountType=accountType;
        this.accountSaving=accountSaving;
        this.accountDeposit=accountDeposit;
        this.accountDestination=accountDestination;
    }
}
