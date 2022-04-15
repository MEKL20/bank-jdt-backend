package com.bank.jdt.RESTSaving.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Saving implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "customer_seq")
    @ApiModelProperty(hidden = true)
    private long id;
    @Column(name = "customer_id")
    private long customerId;
    @Column(name = "account_saving")
    private long accountSaving;
    private int balance;
    @Column(name = "is_active")
    private boolean isActive;
    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    public Saving(){}

    public Saving(long customerId, long accountSaving, int balance, boolean isActive){
        this.customerId=customerId;
        this.accountSaving=accountSaving;
        this.balance=balance;
        this.isActive=isActive;
    }

}
