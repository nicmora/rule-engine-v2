package dev.nicmora.ruleengine.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;
import dev.nicmora.ruleengine.infrastructure.persistence.entity.RuleEntity;
import dev.nicmora.ruleengine.infrastructure.persistence.jpa.RuleJpaRepository;
import dev.nicmora.ruleengine.infrastructure.persistence.mapper.RulePersistenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RulePostgresRepository implements RuleRepository {

    private final RuleJpaRepository ruleJpaRepository;
    private final RulePersistenceMapper rulePersistenceMapper;

    @Override
    public Optional<Rule> findById(UUID id) {
        return ruleJpaRepository.findById(id)
                .map(rulePersistenceMapper::toDomain);
    }

    @Override
    public List<Rule> findAll() {
        return ruleJpaRepository.findAll()
                .stream()
                .map(rulePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Rule> findByTypeAndEnabled(String type, Boolean enabled) {
        return ruleJpaRepository.findByTypeAndEnabled(type, enabled)
                .stream()
                .map(rulePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Rule save(Rule rule) {
        RuleEntity entity = rulePersistenceMapper.toEntity(rule);
        RuleEntity savedEntity = ruleJpaRepository.save(entity);
        return rulePersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        ruleJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return ruleJpaRepository.existsById(id);
    }

}

