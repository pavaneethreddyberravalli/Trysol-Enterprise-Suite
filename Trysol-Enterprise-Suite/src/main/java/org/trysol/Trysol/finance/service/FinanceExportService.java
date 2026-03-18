package org.trysol.Trysol.finance.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.trysol.Trysol.finance.Repository.FinanceRepository;
import org.trysol.Trysol.finance.entity.FinanceRecord;
import org.trysol.Trysol.finance.util.ExcelGenerator;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class FinanceExportService {
    private final FinanceRepository repository;

    public FinanceExportService(FinanceRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<InputStreamResource> downloadExcel() {

        List<FinanceRecord> list = repository.findAll();

        ByteArrayInputStream in = ExcelGenerator.generateExcel(list);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=finance.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }
}
