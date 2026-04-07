package dev.nicmora.ruleengine.infrastructure.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import dev.nicmora.ruleengine.domain.model.Condition;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleResponse {

    private UUID id;
    private String description;
    private String type;
    private Integer priority;
    private List<Condition> conditions;
    private String result;
    private String resultType;
    private Boolean enabled;

}

