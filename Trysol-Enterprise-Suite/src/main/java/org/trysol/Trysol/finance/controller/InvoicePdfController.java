package org.trysol.Trysol.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trysol.Trysol.finance.dto.InvoiceDto;
import org.trysol.Trysol.finance.service.InvoicePdfService;

@RestController
@RequestMapping("/invoice")
public class InvoicePdfController {

    @Autowired
    private InvoicePdfService pdfService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody InvoiceDto dto) throws Exception {

        byte[] pdf = pdfService.generateInvoice(dto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

