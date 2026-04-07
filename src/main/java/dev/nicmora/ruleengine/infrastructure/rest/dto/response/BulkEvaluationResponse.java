package dev.nicmora.ruleengine.infrastructure.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkEvaluationResponse {

    private Integer totalElements;
    private Map<String, Long> resultTypeCounts;

}


