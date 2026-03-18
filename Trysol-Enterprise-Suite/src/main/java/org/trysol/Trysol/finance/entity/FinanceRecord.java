package org.trysol.Trysol.finance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FinanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer serialNo;
    private String gstMonth;
    private String companyName;
    private String gstNumber;
    private String candidateName;
    private LocalDate doj;
    private String location;
    private Integer workedDays;
    private Integer totalDays;
    private String employeeWorkingDays;
    private Integer leaves;
    private String poNumber;
    private String days;
    private Double lessInvoice;
    private String trysolBillNumber;
    private String invoiceStatus;
    private Integer  daysRemaining;
    private LocalDate  trysolBillDate;
    private Double billAmountExclTax;
    private Double cgst;
    private Double sgst;
    private Double igst;
    private Double billAmountInclTax;
    private String address;
    private String paymentStatus;
    private String empId;
    private Double salaryPaid;
    private Double margin;


    @PrePersist
    @PreUpdate
    public void calculateFields() {

        // Total Days
        if (workedDays != null && leaves != null) {
            this.totalDays = workedDays + leaves;
        }

        // GST Calculation (9% + 9%)
        if (billAmountExclTax != null) {
            this.cgst = billAmountExclTax * 0.09;
            this.sgst = billAmountExclTax * 0.09;
            this.igst = 0.0;

            this.billAmountInclTax = billAmountExclTax + cgst + sgst;
        }

        // Margin
        if (billAmountExclTax != null && salaryPaid != null) {
            this.margin = billAmountExclTax - salaryPaid;


        }
    }

}
