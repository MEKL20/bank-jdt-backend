package com.bank.jdt.RESTCustomer.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "customer_seq")
    @ApiModelProperty(hidden = true)
    private long id;
    @Column(name = "identity_card")
    private long identityCard;
    private String name;
    private String username;
    private String password;
    private String phone;
    private String email;
    @Lob
    private String address;
    @Temporal(TemporalType.DATE)
    private Date datebirth;
    @ApiModelProperty(hidden = true)
    private Date created_at;


    public Customer() {
    }

    public Customer(String name, String password, String phone, String email, String address, Date datebirth) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.datebirth = datebirth;
    }
}
