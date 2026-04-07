package dev.nicmora.ruleengine.infrastructure.rest.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import dev.nicmora.ruleengine.domain.exception.BulkEvaluationException;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.BulkEvaluationResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EvaluableBulkMapper {

    private final ObjectMapper objectMapper;

    public List<Evaluable> toModel(MultipartFile file, String processType, String productType) {
        try {
            log.debug("Parsing bulk evaluation file: {}", file.getOriginalFilename());

            List<Map<String, String>> fieldsArray = objectMapper.readValue(
                    file.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class,
                            objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class))
            );

            return fieldsArray.stream()
                    .map(fields -> Evaluable.builder()
                            .id(UUID.randomUUID())
                            .fields(fields)
                            .processType(processType)
                            .productType(productType)
                            .build())
                    .toList();

        } catch (IOException e) {
            log.error("Error parsing bulk evaluation file: {}", file.getOriginalFilename(), e);
            throw new BulkEvaluationException(
                    "Error al procesar el archivo JSON: " + e.getMessage(),
                    -1,
                    file.getOriginalFilename()
            );
        }
    }

    public BulkEvaluationResponse toResponse(List<Evaluable> evaluables, List<Rule> matchedRules) {
        log.debug("Creating bulk evaluation response for {} elements", evaluables.size());

        Map<String, Long> resultTypeCounts = matchedRules.stream()
                .collect(Collectors.groupingBy(
                        rule -> rule.getResultType() != null ? rule.getResultType() : "UNKNOWN",
                        Collectors.counting()
                ));

        return BulkEvaluationResponse.builder()
                .totalElements(evaluables.size())
                .resultTypeCounts(resultTypeCounts)
                .build();
    }
}


