package com.bank.jdt.RESTDeposit.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "deposit_seq")
    @ApiModelProperty(hidden = true)
    private long id;
    @ManyToOne
    private Customer customer;
    @Column(name = "account_deposit")
    private long accountDeposit;
    private long balance;
    private int period;
    @Column(name = "is_active")
    private boolean isActive;
    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;
    @ApiModelProperty(hidden = true)
    @Column(name = "expired_at")
    private Date expiredAt;


    public Deposit(long id, long customerId, long accountDeposit, long balance, int period, boolean isActive, Date createdAt, Date expiredAt) {
        this.id = id;
        this.customerId = customerId;
        this.accountDeposit = accountDeposit;
        this.balance = balance;
        this.period = period;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

}
