package com.example.finance_dashboard.service;

import com.example.finance_dashboard.model.FinancialRecord;
import com.example.finance_dashboard.model.User;
import com.example.finance_dashboard.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordServiceImpl {

    @Autowired
    private FinancialRecordRepository recordRepository;

    public FinancialRecord save(FinancialRecord record, User createdBy) {
        record.setCreatedBy(createdBy);
        record.setDeleted(false);
        return recordRepository.save(record);
    }

    public List<FinancialRecord> findAll() {
        return recordRepository.findByDeletedFalseOrderByDateDesc();
    }

    public FinancialRecord findById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public FinancialRecord update(Long id, FinancialRecord updated) {
        FinancialRecord existing = findById(id);
        existing.setAmount(updated.getAmount());
        existing.setType(updated.getType());
        existing.setCategory(updated.getCategory());
        existing.setDate(updated.getDate());
        existing.setNotes(updated.getNotes());
        return recordRepository.save(existing);
    }

    public void softDelete(Long id) {
        FinancialRecord record = findById(id);
        record.setDeleted(true);
        recordRepository.save(record);
    }

    public List<FinancialRecord> filterByType(String type) {
        return recordRepository.findByTypeAndDeletedFalse(type);
    }

    public List<FinancialRecord> filterByDateRange(LocalDate start, LocalDate end) {
        return recordRepository.findByDateBetweenAndDeletedFalse(start, end);
    }

    public List<FinancialRecord> filterByCategory(String category) {
        return recordRepository.findByCategoryAndDeletedFalse(category);
    }
}