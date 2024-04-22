package com.example.accounting_demo.processor;

import org.cyoda.cloud.api.event.BaseEvent;
import org.cyoda.cloud.api.event.DataPayload;
import org.cyoda.cloud.api.event.EntityProcessorCalculationRequest;
import org.cyoda.cloud.api.event.EntityProcessorCalculationResponse;
import org.springframework.stereotype.Component;

@Component
    public class CyodaCalculationMemberProcessor {

        public BaseEvent calculate(EntityProcessorCalculationRequest request) {
            EntityProcessorCalculationResponse response = new EntityProcessorCalculationResponse();

            response.setOwner(request.getOwner());
            response.setRequestId(request.getRequestId());
            response.setEntityId(request.getEntityId());

            DataPayload payload = new DataPayload();
            payload.setType("TreeNode");
            payload.setData(request.getPayload() != null ? request.getPayload().getData() : null);

            response.setPayload(payload);

            return response;
        }
    }
