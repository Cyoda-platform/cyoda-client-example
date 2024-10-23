package com.example.accounting_demo.entity;

import com.example.accounting_demo.common.ai.AIAssistantService;
import com.example.accounting_demo.common.ingestion.DataIngestionService;

import com.example.accounting_demo.entity.expense_report.ExpenseReport;
import com.example.accounting_demo.entity.expense_report_job.ExpenseReportJob;
import com.example.accounting_demo.service.EntityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cyoda.cloud.api.event.BaseEvent;
import org.cyoda.cloud.api.event.EntityProcessorCalculationRequest;
import org.cyoda.cloud.api.event.EntityProcessorCalculationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Component
public class EntityWorkflow {

    private static final Logger logger = LoggerFactory.getLogger(EntityWorkflow.class);
    public static final String EXPENSE_REPORT_SCHEMA_ID = "45b5bfb0-9315-11ef-b7d3-ca6fd3f80374";
    public static final String DS_1_ID = "86c97f70-90d9-11ef-b47d-a6dbe11f1714";
    public static final String DS_2_ID = "aaba3e10-90d9-11ef-b47d-a6dbe11f1714";

    private final ObjectMapper objectMapper;
    private final DataIngestionService dataIngestionService;
    private final AIAssistantService aiAssistantService;
    private final EntityService entityService;

    public EntityWorkflow(ObjectMapper om, DataIngestionService dataIngestionService, AIAssistantService aiAssistantService, EntityService entityService) {
        this.objectMapper = om;
        this.dataIngestionService = dataIngestionService;
        this.aiAssistantService = aiAssistantService;
        this.entityService = entityService;
    }


    public BaseEvent calculate(EntityProcessorCalculationRequest request) throws IOException, InterruptedException {
        EntityProcessorCalculationResponse response = new EntityProcessorCalculationResponse();

        response.setOwner(request.getOwner());
        response.setRequestId(request.getRequestId());
        response.setEntityId(request.getEntityId());
        ExpenseReportJob expenseReportJob = objectMapper.treeToValue(request.getPayload().getData(), ExpenseReportJob.class);

        switch (request.getProcessorName()) {
            case "GenerateReport1":
                String question = "Generate a report to get total expenses by employee, by category, by project. Return only report without comments.";
                String report = aiAssistantService.chat(EXPENSE_REPORT_SCHEMA_ID, question);
                ExpenseReport expenseReport = new ExpenseReport();
                expenseReport.setReport(report);
                expenseReport = (ExpenseReport) entityService.addItem(expenseReport);
                expenseReportJob.setReportId(expenseReport.getId());
                request.getPayload().setData(objectMapper.valueToTree(expenseReportJob));
                response.setPayload(request.getPayload());
                break;
            case "IngestDataFromSystem1":
                String requestId1 = dataIngestionService.ingestData(DS_1_ID, "Fetch Accounting Data", new HashMap<>());
                expenseReportJob.setRequestIds(new ArrayList<>(List.of(UUID.fromString(requestId1.replaceAll("\"", "")))));
                request.getPayload().setData(objectMapper.valueToTree(expenseReportJob));
                response.setPayload(request.getPayload());
                break;
            case "IngestDataFromSystem2":
                String requestId2 = dataIngestionService.ingestData(DS_2_ID, "Fetch Accounting Data", new HashMap<>());
                response.setPayload(request.getPayload());
                expenseReportJob.getRequestIds().add(UUID.fromString(requestId2.replaceAll("\"", "")));
                request.getPayload().setData(objectMapper.valueToTree(expenseReportJob));
                response.setPayload(request.getPayload());
                break;
            case "SendReport":
                logger.info("Sending report");
                response.setPayload(request.getPayload());
                break;
            case "VerifyIngestionForSystem1":
                for (UUID reqId : expenseReportJob.getRequestIds()){
                    String resp = dataIngestionService.getDataSourceResult(String.valueOf(reqId));
                    //todo validate resp and add finished request
                }
                response.setPayload(request.getPayload());
                break;
            default:
                logger.info("No corresponding processor found");
                break;
        }

        return response;
    }

}