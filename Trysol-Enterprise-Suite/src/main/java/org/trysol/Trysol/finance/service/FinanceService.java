package org.trysol.Trysol.finance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.trysol.Trysol.finance.Repository.FinanceRepository;
import org.trysol.Trysol.finance.entity.FinanceRecord;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;


    public FinanceRecord saveRecord(FinanceRecord record) {
        return financeRepository.save(record);
    }

    public List<FinanceRecord> getAllRecords() {
        return financeRepository.findAll();
    }

    public FinanceRecord getById(Long id) {
        return financeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public void delete(Long id) {
        financeRepository.deleteById(id);
    }


    }


