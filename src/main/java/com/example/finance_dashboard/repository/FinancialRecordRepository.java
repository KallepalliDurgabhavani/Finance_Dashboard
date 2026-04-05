package com.example.finance_dashboard.repository;



import com.example.finance_dashboard.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    List<FinancialRecord> findByDeletedFalseOrderByDateDesc();

    List<FinancialRecord> findByTypeAndDeletedFalse(String type);

    List<FinancialRecord> findByCategoryAndDeletedFalse(String category);

    List<FinancialRecord> findByDateBetweenAndDeletedFalse(LocalDate start, LocalDate end);

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = :type AND r.deleted = false")
    BigDecimal sumByType(String type);

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r WHERE r.deleted = false GROUP BY r.category")
    List<Object[]> sumByCategory();

    @Query("SELECT MONTH(r.date), SUM(r.amount) FROM FinancialRecord r WHERE r.type = :type AND r.deleted = false GROUP BY MONTH(r.date)")
    List<Object[]> monthlyTotals(String type);
}