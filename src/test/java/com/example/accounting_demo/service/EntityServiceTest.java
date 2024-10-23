package com.example.accounting_demo.service;

import com.example.accounting_demo.common.repository.CyodaHttpRepository;
import com.example.accounting_demo.entity.BaseEntity;
import com.example.accounting_demo.common.util.JsonToEntityListParser;
import com.example.accounting_demo.entity.employee_expense.EmployeeExpense;
import com.example.accounting_demo.entity.expense_report.ExpenseReport;
import com.example.accounting_demo.entity.expense_report_job.ExpenseReportJob;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntityServiceTest {


    @Autowired
    private EntityService entityService;

    @Autowired
    private CyodaHttpRepository cyodaHttpRepository;

    @Autowired
    private JsonToEntityListParser jsonToEntityListParser;

    @BeforeAll
    void setUpBeforeClass() throws Exception {
        cyodaHttpRepository.deleteAllEntitiesByModel("employee_expense");
        cyodaHttpRepository.deleteAllEntitiesByModel("expense_report");
        cyodaHttpRepository.deleteAllEntitiesByModel("expense_report_job");

        cyodaHttpRepository.deleteEntityModel("employee_expense");
        cyodaHttpRepository.deleteEntityModel("expense_report");
        cyodaHttpRepository.deleteEntityModel("expense_report_job");

        Thread.sleep(1000);

        ObjectMapper objectMapper = new ObjectMapper();
        File employeeExpenseFile = new ClassPathResource("entity/employee_expense/employee_expense.json").getFile();
        EmployeeExpense employeeExpense = objectMapper.readValue(employeeExpenseFile, EmployeeExpense.class);
        cyodaHttpRepository.saveEntityModel(Collections.singletonList(employeeExpense));
        cyodaHttpRepository.lockEntityModel(Collections.singletonList(employeeExpense));

        File expenseReportFile = new ClassPathResource("entity/expense_report/expense_report.json").getFile();
        ExpenseReport expenseReport = objectMapper.readValue(expenseReportFile, ExpenseReport.class);
        cyodaHttpRepository.saveEntityModel(Collections.singletonList(expenseReport));
        cyodaHttpRepository.lockEntityModel(Collections.singletonList(expenseReport));

        File expenseReportJobFile = new ClassPathResource("entity/expense_report_job/expense_report_job.json").getFile();
        ExpenseReportJob expenseReportJob = objectMapper.readValue(expenseReportJobFile, ExpenseReportJob.class);
        cyodaHttpRepository.saveEntityModel(Collections.singletonList(expenseReportJob));
        cyodaHttpRepository.lockEntityModel(Collections.singletonList(expenseReportJob));
    }


    @Test
    public void saveExpenseReportRequestTest() throws Exception {
        Thread.sleep(2000);
        //submit a job to ingest data and save a report
        ExpenseReportJob reportJob = new ExpenseReportJob();
        reportJob.setDate(String.valueOf(LocalDate.now()));
        reportJob.setRequestIds(Collections.singletonList(UUID.randomUUID()));
        reportJob.setFinishedRequestIds(Collections.singletonList(UUID.randomUUID()));
        reportJob.setReportId(UUID.fromString("a02e184a-92eb-11ef-b7d3-ca6fd3f80374"));
        reportJob = (ExpenseReportJob) entityService.addItem(reportJob);
        Thread.sleep(20000);
        //we expect the report job entity to be updated with a report id
        reportJob = (ExpenseReportJob) entityService.getItem(reportJob.getId());
        ExpenseReport report = (ExpenseReport) entityService.getItem(reportJob.getReportId());
        //the report should be available
        Assertions.assertNotNull(report);
    }
//
}