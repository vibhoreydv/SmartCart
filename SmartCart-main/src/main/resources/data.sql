INSERT INTO tag (name) VALUES
  ('romantic'),
  ('formal'),
  ('flowers'),
  ('gifts'),
  ('birthday'),
  ('party'),
  ('gaming'),
  ('work-from-home'),
  ('diwali'),
  ('wedding'),
  ('anniversary');

INSERT INTO intent (name, display_name, description) VALUES
  ('proposal', 'Proposal', 'Gifts & outfits for proposing someone'),
  ('birthday', 'Birthday', 'Birthday gifts and party items'),
  ('office-party', 'Office Party', 'Semi-formal outfits and gifts for office parties'),
  ('date-night', 'Date Night', 'Outfits and accessories for a date'),
  ('housewarming', 'Housewarming', 'Gifts and decor for new homes'),
  ('festive-diwali', 'Festive Diwali', 'Diwali decor, sweets, and gifts'),
  ('gaming-setup', 'Gaming Setup', 'Consoles, accessories, and decor'),
  ('work-from-home', 'Work From Home', 'Desks, chairs, productivity items'),
  ('wedding', 'Wedding', 'Wedding outfits and gifts'),
  ('anniversary', 'Anniversary', 'Gifts and outfits for anniversaries');

-- proposal
INSERT INTO intent_tag (intent_id, tag_id, weight)
SELECT i.id, t.id, 1.0 FROM intent i, tag t
WHERE i.name = 'proposal' AND t.name IN ('romantic','formal','flowers','gifts');

-- birthday
INSERT INTO intent_tag (intent_id, tag_id, weight)
SELECT i.id, t.id, 1.0 FROM intent i, tag t
WHERE i.name = 'birthday' AND t.name IN ('birthday','party','gifts');

-- festive-diwali
INSERT INTO intent_tag (intent_id, tag_id, weight)
SELECT i.id, t.id, 1.0 FROM intent i, tag t
WHERE i.name = 'festive-diwali' AND t.name IN ('diwali','gifts');

-- gaming-setup
INSERT INTO intent_tag (intent_id, tag_id, weight)
SELECT i.id, t.id, 1.0 FROM intent i, tag t
WHERE i.name = 'gaming-setup' AND t.name IN ('gaming');
