package org.trysol.Trysol.finance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trysol.Trysol.Auth.exception.InvalidStateException;

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
    private String employeeState;
    private String companyState;
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
        if (companyState == null || employeeState == null) {
            throw new InvalidStateException("Company state and employee state must not be null");
        }

        if (billAmountExclTax != null) {
            double taxRate = 0.18; // 18% GST
            if (companyState != null && employeeState != null &&
                    companyState.equalsIgnoreCase(employeeState)) {
                // Intra-state: CGST + SGST
                this.cgst = billAmountExclTax * (taxRate / 2);
                this.sgst = billAmountExclTax * (taxRate / 2);
                this.igst = 0.0;
            } else {
                // Inter-state: IGST only
                this.cgst = 0.0;
                this.sgst = 0.0;
                this.igst = billAmountExclTax * taxRate;
            }
            this.billAmountInclTax = billAmountExclTax + cgst + sgst + igst;
        }
        // Margin
        if (billAmountExclTax != null && salaryPaid != null) {
            this.margin = billAmountExclTax - salaryPaid;
        }
    }

}
