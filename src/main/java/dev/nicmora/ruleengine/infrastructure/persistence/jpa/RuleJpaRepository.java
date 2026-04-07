package dev.nicmora.ruleengine.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.nicmora.ruleengine.infrastructure.persistence.entity.RuleEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface RuleJpaRepository extends JpaRepository<RuleEntity, UUID> {

    List<RuleEntity> findByTypeAndEnabled(String type, Boolean enabled);

}

