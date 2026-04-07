-- SCHEMA
CREATE SCHEMA IF NOT EXISTS ruleengine;

-- TABLES
CREATE TABLE IF NOT EXISTS ruleengine.rules (
    id UUID PRIMARY KEY,
    description VARCHAR(255),
    type VARCHAR(50),
    priority INTEGER,
    conditions JSONB,
    result JSONB,
    result_type VARCHAR(50),
    enabled BOOLEAN
);

