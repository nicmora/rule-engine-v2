package dev.nicmora.ruleengine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

    private UUID id;
    private String description;
    private String type;
    private Integer priority;
    private List<Condition> conditions;
    private Map<String, Object> result;
    private String resultType;
    private Boolean enabled;

}

