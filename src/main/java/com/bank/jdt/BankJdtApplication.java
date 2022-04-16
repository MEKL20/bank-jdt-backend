package com.bank.jdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankJdtApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankJdtApplication.class, args);
        System.out.println("Springboot sudah berjalan...");
    }

}
