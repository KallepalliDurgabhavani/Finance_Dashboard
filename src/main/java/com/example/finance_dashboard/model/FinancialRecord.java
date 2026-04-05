package com.example.finance_dashboard.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotBlank
    @Column(nullable = false)
    private String type; // INCOME or EXPENSE

    @NotBlank
    private String category; // e.g. Salary, Food, Rent

    @NotNull
    private LocalDate date;

    private String notes;

    private boolean deleted = false; // soft delete

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public FinancialRecord(Long id, @NotNull @DecimalMin("0.01") BigDecimal amount, @NotBlank String type,
			@NotBlank String category, @NotNull LocalDate date, String notes, boolean deleted, User createdBy) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.category = category;
		this.date = date;
		this.notes = notes;
		this.deleted = deleted;
		this.createdBy = createdBy;
	}

	public FinancialRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    
}