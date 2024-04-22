package com.example.accounting_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AccountingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingDemoApplication.class, args);
    }
}
