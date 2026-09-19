-- Query to find intent_tag relation
SELECT
    it.intent_id,
    it.tag_id,
    i.name,
    i.display_name
FROM smartcart.intent_tag it
JOIN smartcart.intent i
    ON i.id = it.intent_id
JOIN smartcart.tag t
    ON t.id = it.tag_id;


