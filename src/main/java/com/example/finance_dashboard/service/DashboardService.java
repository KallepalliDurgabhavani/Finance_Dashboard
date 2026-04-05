package com.example.finance_dashboard.service;

import com.example.finance_dashboard.repository.FinancialRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private FinancialRecordRepository recordRepository;

    public BigDecimal getTotalIncome() {
        BigDecimal total = recordRepository.sumByType("INCOME");
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalExpenses() {
        BigDecimal total = recordRepository.sumByType("EXPENSE");
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getNetBalance() {
        return getTotalIncome().subtract(getTotalExpenses());
    }

    public Map<String, BigDecimal> getCategoryTotals() {
        List<Object[]> rows = recordRepository.sumByCategory();
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        for (Object[] row : rows) {
            result.put((String) row[0], (BigDecimal) row[1]);
        }
        return result;
    }

    public Map<Integer, BigDecimal> getMonthlyTotals(String type) {
        List<Object[]> rows = recordRepository.monthlyTotals(type);
        Map<Integer, BigDecimal> result = new TreeMap<>();
        for (Object[] row : rows) {
            result.put((Integer) row[0], (BigDecimal) row[1]);
        }
        return result;
    }
}