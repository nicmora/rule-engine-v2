DELETE FROM ruleengine.rules
WHERE type = 'CUSTOMER';

INSERT INTO ruleengine.rules
(
    id,
    description,
    type,
    priority,
    conditions,
    result,
    result_type,
    enabled
)
VALUES
(
    gen_random_uuid(),
    'New Customer',
    'CUSTOMER',
    1,
    '[
        {
            "field": "yearsAsCustomer",
            "operator": "LESS_THAN",
            "operatorType": "VALUE",
            "value": "3"
        }
    ]',
    '{
        "injectFields": {
            "_category": "NEW_CUSTOMER"
        }
    }',
    'NEW_CUSTOMER',
    true
),
(
    gen_random_uuid(),
    'Lifetime Customer',
    'CUSTOMER',
    2,
    '[
      {
        "field": "yearsAsCustomer",
        "operator": "GREATER_THAN_OR_EQUAL_TO",
        "operatorType": "VALUE",
        "value": "20"
      }
    ]',
    '{
      "injectFields": {
        "_category": "LIFETIME_CUSTOMER"
      }
    }',
    'LIFETIME_CUSTOMER',
    true
),
(
    gen_random_uuid(),
    'Customer',
    'CUSTOMER',
    99,
    '[]',
    '{
      "injectFields": {
        "_category": "CUSTOMER"
      }
    }',
    'CUSTOMER',
    true
);
