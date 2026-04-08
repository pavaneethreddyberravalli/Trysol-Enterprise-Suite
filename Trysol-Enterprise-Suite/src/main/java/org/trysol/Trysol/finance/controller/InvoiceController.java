package org.trysol.Trysol.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trysol.Trysol.finance.entity.Invoice;
import org.trysol.Trysol.finance.service.InvoiceService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/invoice")
public class InvoiceController {
    @Autowired
    public InvoiceService service;

    @PostMapping("/create-update")
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice) {
        service.saveInvoice(invoice);
        service.appendInvoiceToExcel(invoice); // append to Excel
        return ResponseEntity.ok("Invoice saved and Excel updated!");
    }
    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(service.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getInvoiceById(id));
    }
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        ByteArrayInputStream in = service.getExcelStream();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=invoices.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
    @PutMapping("/edit/{invoiceNo}")
    public ResponseEntity<String> editInvoice(
            @PathVariable("invoiceNo") String invoiceNo,
            @RequestBody Invoice invoice) {
        boolean updated = service.updateInvoiceInExcel(invoiceNo, invoice);
        if (updated) return ResponseEntity.ok("Invoice updated!");
        else return ResponseEntity.status(404).body("Invoice not found");
    }

    @DeleteMapping("/delete/{invoiceNo}")
    public ResponseEntity<String> deleteInvoice(@PathVariable ("invoiceNo") String invoiceNo) {
        boolean deleted = service.deleteInvoiceFromExcel(invoiceNo);
        if (deleted) return ResponseEntity.ok("Invoice deleted!");
        else return ResponseEntity.status(404).body("Invoice not found");

    }


}




