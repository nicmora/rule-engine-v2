package dev.nicmora.ruleengine.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import dev.nicmora.ruleengine.application.usecase.EvaluateBulkElements;
import dev.nicmora.ruleengine.application.usecase.EvaluateElement;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.infrastructure.rest.dto.request.EvaluableRequest;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.BulkEvaluationResponse;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.EvaluableResponse;
import dev.nicmora.ruleengine.infrastructure.rest.mapper.EvaluableBulkMapper;
import dev.nicmora.ruleengine.infrastructure.rest.mapper.EvaluableMapper;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/evaluate")
@RequiredArgsConstructor
public class EvaluateController {

    private final EvaluateElement evaluateElement;
    private final EvaluateBulkElements evaluateBulkElements;

    private final EvaluableMapper evaluableMapper;
    private final EvaluableBulkMapper evaluableBulkMapper;

    @PostMapping
    public ResponseEntity<EvaluableResponse> evaluate(@RequestBody EvaluableRequest request) {
        log.info("POST /api/v1/evaluate - Evaluating element with request: {}", request);

        Evaluable evaluable = evaluableMapper.toModel(request);
        Rule matchedRule = evaluateElement.evaluateElementWithRules(evaluable);

        return ResponseEntity.ok(evaluableMapper.toResponse(evaluable, matchedRule));
    }

    @PostMapping(value = "/bulk", consumes = "multipart/form-data")
    public ResponseEntity<BulkEvaluationResponse> evaluateBulk(
            @RequestPart("file") MultipartFile file,
            @RequestParam("processType") String processType,
            @RequestParam("productType") String productType) {

        log.info("POST /api/v1/evaluate/bulk - Processing file: {}, processType: {}, productType: {}",
                file.getOriginalFilename(), processType, productType);

        List<Evaluable> evaluables = evaluableBulkMapper.toModel(file, processType, productType);
        List<Rule> matchedRules = evaluateBulkElements.evaluateBulk(evaluables);

        BulkEvaluationResponse response = evaluableBulkMapper.toResponse(evaluables, matchedRules);
        log.info("POST /api/v1/evaluate/bulk - Response: {}", response);

        return ResponseEntity.ok(response);
    }

}

