package com.bank.jdt.RESTReporting.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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
    private int amount;
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
    private long createdAt;

    public Reporting(){}

    public Reporting(long customerId, String activity, int amount, String accountType, long accountSaving, long accountDeposit, long accountDestination){
        this.customerId=customerId;
        this.activity=activity;
        this.amount=amount;
        this.accountType=accountType;
        this.accountSaving=accountSaving;
        this.accountDeposit=accountDeposit;
        this.accountDestination=accountDestination;
    }
}
