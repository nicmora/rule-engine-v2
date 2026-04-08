package dev.nicmora.ruleengine.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import dev.nicmora.ruleengine.domain.model.Condition;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "rules", schema = "ruleengine")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String description;

    private String type;

    private Integer priority;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Condition> conditions;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> result;

    @Column(name = "result_type")
    private String resultType;

    private Boolean enabled;

}

