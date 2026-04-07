package dev.nicmora.ruleengine.domain.model;

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
public class Evaluable {

    private UUID id;
    private Map<String, String> fields;
    private String processType;
    private String productType;

}

