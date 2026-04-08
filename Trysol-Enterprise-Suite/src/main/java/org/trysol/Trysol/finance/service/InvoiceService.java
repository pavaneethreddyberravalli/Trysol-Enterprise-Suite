package org.trysol.Trysol.finance.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trysol.Trysol.finance.Repository.InvoiceRepository;
import org.trysol.Trysol.finance.entity.Invoice;
import org.trysol.Trysol.finance.exception.ExcelOperationException;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {


    @Autowired
    public InvoiceRepository invoiceRepository;

    private static final String FILE_PATH = "invoices.xlsx";


    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("₹#,##0.00"));

        return style;
    }

    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("dd-MM-yyyy"));

        return style;
    }

    public void appendInvoiceToExcel(Invoice invoice) {

        try {
            Workbook workbook;
            Sheet sheet;
            File file = new File(FILE_PATH);

            if (file.exists()) {

                // Open existing workbook
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
                Row headerRow = sheet.getRow(0);

                if (headerRow != null) {
                    Cell firstCell = headerRow.getCell(0);
                    //
                    if (firstCell == null || !"S.No".equalsIgnoreCase(firstCell.getStringCellValue())) {

                        Cell snCell = headerRow.createCell(0);
                        snCell.setCellValue("S.No");
                    }

                    // Apply header style
                    CellStyle headerStyle = createHeaderStyle(workbook);
                    for (Cell cell : headerRow) {
                        cell.setCellStyle(headerStyle);
                    }
                }


                // Apply header style again in case it was missing
                // Row headerRow = sheet.getRow(0);
                if (headerRow != null) {
                    CellStyle headerStyle = createHeaderStyle(workbook);
                    for (Cell cell : headerRow) {
                        cell.setCellStyle(headerStyle);
                    }
                }

            } else {
                // Create new workbook and sheet
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Invoices");

                // Create header row with style
                Row headerRow = sheet.createRow(0);
                Field[] fields = Invoice.class.getDeclaredFields();
                CellStyle headerStyle = createHeaderStyle(workbook);

                Cell snCell = headerRow.createCell(0);
                snCell.setCellValue("S.No");
                snCell.setCellStyle(headerStyle);

//
                for (int i = 0; i < fields.length; i++) {
                    Cell cell = headerRow.createCell(i + 1);
                    cell.setCellValue(fields[i].getName());
                    cell.setCellStyle(headerStyle);
                }
            }

            // Create styles for data
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);

            // Append new invoice row
            Field[] fields = Invoice.class.getDeclaredFields();
            int rowIdx = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowIdx);


            Cell snCell = row.createCell(0);
            snCell.setCellValue(rowIdx); // because row 0 is header
            snCell.setCellStyle(dataStyle);


            for (int colIdx = 0; colIdx < fields.length; colIdx++) {
                fields[colIdx].setAccessible(true);
                Object value = fields[colIdx].get(invoice);
//
                Cell cell = row.createCell(colIdx + 1);

                if (value == null) {
                    cell.setCellValue("");
                    cell.setCellStyle(dataStyle);

                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);

                    // Apply currency style for amount fields
                    String fieldName = fields[colIdx].getName();
                    if (fieldName.equalsIgnoreCase("billRate") ||
                            fieldName.equalsIgnoreCase("grandTotal")) {
                        cell.setCellStyle(currencyStyle);
                    } else {
                        cell.setCellStyle(dataStyle);
                    }

                } else if (value instanceof LocalDate) {
                    cell.setCellValue(java.sql.Date.valueOf((LocalDate) value));
                    cell.setCellStyle(dateStyle);

                } else {
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(dataStyle);
                }
            }
            // Auto-size columns
            for (int i = 0; i <= fields.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Apply auto-filter on header
            sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, fields.length - 1));
            // Write to file
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();
            workbook.close();

        } catch (Exception e) {
            throw new ExcelOperationException("Error while updating Excel", e);
        }
    }


    public ByteArrayInputStream getExcelStream() throws IOException {
        Path path = Path.of(FILE_PATH);
        if (!Files.exists(path)) {
            throw new ExcelOperationException("Excel file not found!");
        }
        return new ByteArrayInputStream(Files.readAllBytes(path));
    }
/// // get invoice ///
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
//////////////  single id ////////
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public boolean updateInvoiceInExcel(String invoiceNo, Invoice updatedInvoice) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return false;

            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            fis.close();

            Field[] fields = Invoice.class.getDeclaredFields();
            boolean found = false;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Read invoiceNo column (update index if needed)
                Cell invoiceCell = row.getCell(1); // 1 = invoiceNo
                if (invoiceCell != null &&
                        invoiceCell.getStringCellValue().trim().equalsIgnoreCase(invoiceNo.trim())) {

                    // Update all columns
                    for (int j = 0; j < fields.length; j++) {
                        fields[j].setAccessible(true);
                        Object value = fields[j].get(updatedInvoice);
                        row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
                                .setCellValue(value != null ? value.toString() : "");
                    }

                    found = true;
                    break;
                }
            }

            if (found) {
                FileOutputStream fos = new FileOutputStream(FILE_PATH);
                workbook.write(fos);
                fos.close();
            }

            workbook.close();
            return found;

        } catch (Exception e) {
            throw new ExcelOperationException("Error updating Excel", e);
        }
    }

    public boolean deleteInvoiceFromExcel(String invoiceNo) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return false;

            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            fis.close();

            boolean found = false;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                // invoiceNo column = 1
                Cell invoiceCell = row.getCell(1);
                if (invoiceCell != null &&
                        invoiceCell.getStringCellValue().trim().equalsIgnoreCase(invoiceNo.trim())) {

                    int rowIndex = row.getRowNum();
                    removeRow(sheet, rowIndex); // remove row helper
                    found = true;
                    break;
                }
            }
            if (found) {
                FileOutputStream fos = new FileOutputStream(FILE_PATH);
                workbook.write(fos);
                fos.close();
            }

            workbook.close();
            return found;

        } catch (Exception e) {
            throw new ExcelOperationException("Error deleting invoice from Excel", e);
        }
    }

    // Helper to remove a row and shift rows up
    private void removeRow(Sheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            Row removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) sheet.removeRow(removingRow);
        }
    }
}