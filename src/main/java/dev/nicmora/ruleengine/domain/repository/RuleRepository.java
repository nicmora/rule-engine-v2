package dev.nicmora.ruleengine.domain.repository;

import dev.nicmora.ruleengine.domain.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RuleRepository {

    Optional<Rule> findById(UUID id);
    List<Rule> findAll();
    List<Rule> findByTypeAndEnabled(String type, Boolean enabled);
    Rule save(Rule rule);
    void deleteById(UUID id);
    boolean existsById(UUID id);

}

