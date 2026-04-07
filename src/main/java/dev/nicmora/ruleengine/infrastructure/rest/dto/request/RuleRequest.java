package dev.nicmora.ruleengine.infrastructure.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import dev.nicmora.ruleengine.domain.model.Condition;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleRequest {

    private String description;
    private String type;
    private Integer priority;
    private List<Condition> conditions;
    private String result;
    private String resultType;
    private Boolean enabled;

}

