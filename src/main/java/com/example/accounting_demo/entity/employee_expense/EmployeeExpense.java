package com.example.accounting_demo.entity.employee_expense;

import com.example.accounting_demo.common.repository.CyodaEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeExpense extends CyodaEntity {

    private String employeeName;
    private String department;
    private String position;
    private String expenseDate;
    private String expenseCategory;
    private String expenseDescription;
    private BigDecimal amount;
    private String projectName;
    private String quarter;
    private int year;
}
