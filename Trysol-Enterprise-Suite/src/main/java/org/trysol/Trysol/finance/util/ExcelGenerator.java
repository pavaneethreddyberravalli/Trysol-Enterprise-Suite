package org.trysol.Trysol.finance.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.trysol.Trysol.finance.entity.FinanceRecord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelGenerator {

        public static ByteArrayInputStream generateExcel(List<FinanceRecord> list) {

            try (Workbook workbook = new XSSFWorkbook();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                Sheet sheet = workbook.createSheet("Finance Data");

                // Header Row
                Row header = sheet.createRow(0);

                String[] columns = {
                        "ID", "Serial No", "GST Month", "Company Name", "GST Number",
                        "Candidate Name", "DOJ", "Location", "Worked Days", "Total Days",
                        "Employee Working Days", "Leaves", "PO Number", "Days",
                        "Less Invoice", "Trysol Bill Number", "Invoice Status", "Days Remaining",
                        "Trysol Bill Date", "Bill Amount Excl Tax", "CGST", "SGST", "IGST",
                        "Bill Amount Incl Tax", "Address", "Payment Status", "Emp ID",
                        "Salary Paid", "Margin"
                };

                for (int i = 0; i < columns.length; i++) {
                    header.createCell(i).setCellValue(columns[i]);
                }

                // Data Rows
                int rowIdx = 1;
                for (FinanceRecord r : list) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue(r.getId() != null ? r.getId() : 0);
                    row.createCell(1).setCellValue(r.getSerialNo() != null ? r.getSerialNo() : 0);
                    row.createCell(2).setCellValue(r.getGstMonth() != null ? r.getGstMonth() : "");
                    row.createCell(3).setCellValue(r.getCompanyName() != null ? r.getCompanyName() : "");
                    row.createCell(4).setCellValue(r.getGstNumber() != null ? r.getGstNumber() : "");
                    row.createCell(5).setCellValue(r.getCandidateName() != null ? r.getCandidateName() : "");
                    row.createCell(6).setCellValue(r.getDoj() != null ? r.getDoj().toString() : "");
                    row.createCell(7).setCellValue(r.getLocation() != null ? r.getLocation() : "");
                    row.createCell(8).setCellValue(r.getWorkedDays() != null ? r.getWorkedDays() : 0);
                    row.createCell(9).setCellValue(r.getTotalDays() != null ? r.getTotalDays() : 0);
                    row.createCell(10).setCellValue(r.getEmployeeWorkingDays() != null ? r.getEmployeeWorkingDays() : "");
                    row.createCell(11).setCellValue(r.getLeaves() != null ? r.getLeaves() : 0);
                    row.createCell(12).setCellValue(r.getPoNumber() != null ? r.getPoNumber() : "");
                    row.createCell(13).setCellValue(r.getDays() != null ? r.getDays() : "");
                    row.createCell(14).setCellValue(r.getLessInvoice() != null ? r.getLessInvoice() : 0);
                    row.createCell(15).setCellValue(r.getTrysolBillNumber() != null ? r.getTrysolBillNumber() : "");
                    row.createCell(16).setCellValue(r.getInvoiceStatus() != null ? r.getInvoiceStatus() : "");
                    row.createCell(17).setCellValue(r.getDaysRemaining() != null ? r.getDaysRemaining() : 0);
                    row.createCell(18).setCellValue(r.getTrysolBillDate() != null ? r.getTrysolBillDate().toString() : "");
                    row.createCell(19).setCellValue(r.getBillAmountExclTax() != null ? r.getBillAmountExclTax() : 0);
                    row.createCell(20).setCellValue(r.getCgst() != null ? r.getCgst() : 0);
                    row.createCell(21).setCellValue(r.getSgst() != null ? r.getSgst() : 0);
                    row.createCell(22).setCellValue(r.getIgst() != null ? r.getIgst() : 0);
                    row.createCell(23).setCellValue(r.getBillAmountInclTax() != null ? r.getBillAmountInclTax() : 0);
                    row.createCell(24).setCellValue(r.getAddress() != null ? r.getAddress() : "");
                    row.createCell(25).setCellValue(r.getPaymentStatus() != null ? r.getPaymentStatus() : "");
                    row.createCell(26).setCellValue(r.getEmpId() != null ? r.getEmpId() : "");
                    row.createCell(27).setCellValue(r.getSalaryPaid() != null ? r.getSalaryPaid() : 0);
                    row.createCell(28).setCellValue(r.getMargin() != null ? r.getMargin() : 0);
                }

                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());

            } catch (Exception e) {
                throw new RuntimeException("Failed to generate Excel: " + e.getMessage());
            }
        }
    }

