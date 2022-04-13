package com.bank.jdt.RESTCustomer.Entity;

import lombok.Getter;
import lombok.Setter;
import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private long identity_card;
    private String name;
    private String password;
    private String phone;
    private String email;
    @Lob
    private String address;
    @Temporal(TemporalType.DATE)
    private Date datebirth;
    @Temporal(TemporalType.TIME)
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
