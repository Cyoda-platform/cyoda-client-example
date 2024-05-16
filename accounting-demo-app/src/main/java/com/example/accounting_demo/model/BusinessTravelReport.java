package com.example.accounting_demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BusinessTravelReport {

    @Id
    @JsonIgnore
    private UUID id;
    private UUID employeeId;
    private String city;
    private Timestamp departureDate;
    private String totalAmount;

    @Override
    public String toString() {
        return "BusinessTravelReport{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", city='" + city + '\'' +
                ", departureDate=" + departureDate +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
