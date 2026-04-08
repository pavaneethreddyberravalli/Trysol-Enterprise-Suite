package org.trysol.Trysol.finance.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto{
    private String invoiceNo;
    private String date;

    private String toName;
    private String toAddress;
    private String toGstin;

    private String fromName;
    private String fromAddress;
    private String fromGstin;

    private String employeeName;
    private String doj;
    private String duration;
    private String modeOfHiring;
    private String jobLocation;
    private String lut;
    private String hsn;

    private double amount;
}
