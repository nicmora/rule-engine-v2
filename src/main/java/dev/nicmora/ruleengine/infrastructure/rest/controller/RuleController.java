package dev.nicmora.ruleengine.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.nicmora.ruleengine.application.usecase.*;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.infrastructure.rest.dto.request.RuleRequest;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.RuleResponse;
import dev.nicmora.ruleengine.infrastructure.rest.mapper.RuleMapper;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/rules")
@RequiredArgsConstructor
public class RuleController {

    private final CreateRule createRule;
    private final GetRule getRule;
    private final GetRules getRules;
    private final UpdateRule updateRule;
    private final DeleteRule deleteRule;

    private final RuleMapper ruleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RuleResponse> getById(@PathVariable UUID id) {
        log.info("GET /api/v1/rules/{} - Fetching rule by ID", id);

        Rule rule = getRule.execute(id);
        return ResponseEntity.ok(ruleMapper.toResponse(rule));
    }

    @GetMapping
    public ResponseEntity<List<RuleResponse>> getAll() {
        log.info("GET /api/v1/rules - Fetching all rules");

        List<Rule> rules = getRules.execute();
        return ResponseEntity.ok(ruleMapper.toResponse(rules));
    }

    @PostMapping
    public ResponseEntity<RuleResponse> create(@RequestBody RuleRequest request) {
        log.info("POST /api/v1/rules - Creating new rule with request: {}", request);

        Rule rule = ruleMapper.toModel(request);
        Rule createdRule = createRule.execute(rule);
        return new ResponseEntity<>(ruleMapper.toResponse(createdRule), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleResponse> update(@PathVariable UUID id, @RequestBody RuleRequest request) {
        log.info("PUT /api/v1/rules/{} - Updating rule with ID: {} and request: {}", id, id, request);

        Rule rule = ruleMapper.toModel(request);
        Rule updatedRule = updateRule.execute(id, rule);
        return ResponseEntity.ok(ruleMapper.toResponse(updatedRule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("DELETE /api/v1/rules/{} - Deleting rule with ID: {}", id, id);

        deleteRule.execute(id);
        return ResponseEntity.noContent().build();
    }

}

