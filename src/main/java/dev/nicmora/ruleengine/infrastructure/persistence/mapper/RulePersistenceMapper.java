package dev.nicmora.ruleengine.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.infrastructure.persistence.entity.RuleEntity;

@Mapper(componentModel = "spring")
public interface RulePersistenceMapper {
    
    Rule toDomain(RuleEntity entity);
    
    RuleEntity toEntity(Rule domain);

}

