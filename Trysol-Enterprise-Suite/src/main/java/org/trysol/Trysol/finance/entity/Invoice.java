package org.trysol.Trysol.finance.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNo;
    private LocalDate date;

    private String toName;
    private String toAddress;
    private String toGstin;

    private String fromName;
    private String fromAddress;
    private String fromGstin;


    private String itemName;
    private String description;
    private Double billRate;

    private LocalDate doj;
    private String duration;
    private String modeOfHiring;
    private String jobLocation;
    private String lut;
    private String hsn;


    private Double grandTotal;
    private String amountInWords;

    private String accountName;
    private String bankName;
    private String accountNumber;
    private String branch;
    private String ifscCode;
    private String panNumber;


}
