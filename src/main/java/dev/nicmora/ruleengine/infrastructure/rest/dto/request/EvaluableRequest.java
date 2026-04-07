package dev.nicmora.ruleengine.infrastructure.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluableRequest {

    private UUID id;
    private Map<String, String> fields;
    private String processType;
    private String productType;

}

