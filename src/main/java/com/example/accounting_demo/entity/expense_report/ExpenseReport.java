package com.example.accounting_demo.entity.expense_report;

import com.example.accounting_demo.common.repository.CyodaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseReport extends CyodaEntity {
    private String report;
}
