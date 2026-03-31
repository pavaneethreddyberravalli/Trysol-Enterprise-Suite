package org.trysol.Trysol.finance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.trysol.Trysol.finance.entity.FinanceRecord;
import org.trysol.Trysol.finance.service.FinanceExportService;
import org.trysol.Trysol.finance.service.FinanceService;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class AuthFinanceController {

    private final FinanceService financeService;
    private final FinanceExportService service;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadExcel() {
        return service.downloadExcel();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FinanceRecord record) {
        return ResponseEntity.ok(financeService.saveRecord(record));
    }

}
