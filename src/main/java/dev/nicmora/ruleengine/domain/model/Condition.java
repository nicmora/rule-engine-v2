package dev.nicmora.ruleengine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition {

    private String field;
    private String value;
    private Operator operator;
    private OperatorType operatorType;
    private String otherField;

}

