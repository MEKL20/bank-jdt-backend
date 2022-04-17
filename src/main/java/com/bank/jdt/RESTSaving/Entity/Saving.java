package com.bank.jdt.RESTSaving.Entity;

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
public class Saving implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "saving_seq")
    @ApiModelProperty(hidden = true)
    private long id;
    @OneToOne
    private Customer customer;
    @Column(name = "account_saving")
    private long accountSaving;
    private long balance;
    @Column(name = "is_active")
    private boolean isActive;
    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    public Saving(){}

    public Saving(Customer customer, long accountSaving, int balance, boolean isActive){
        this.customer=customer;
        this.accountSaving=accountSaving;
        this.balance=balance;
        this.isActive=isActive;
    }

}
