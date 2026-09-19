SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE category;
TRUNCATE TABLE product;
TRUNCATE TABLE tag;
TRUNCATE TABLE product_tag;
TRUNCATE TABLE intent_tag;
TRUNCATE TABLE intent;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE if not exists category (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE if not exists product (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sku VARCHAR(64) NOT NULL UNIQUE,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  category_id BIGINT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  base_discount_percent DECIMAL(5,2) DEFAULT 0.0,
  rating DECIMAL(2,1),
  image_url VARCHAR(512),
  CONSTRAINT fk_product_category
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE if not exists tag (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE if not exists product_tag (
  product_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  PRIMARY KEY (product_id, tag_id),
  CONSTRAINT fk_product_tag_product
    FOREIGN KEY (product_id) REFERENCES product(id),
  CONSTRAINT fk_product_tag_tag
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);

CREATE TABLE if not exists intent (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  display_name VARCHAR(150),
  description VARCHAR(500)
);

CREATE TABLE if not exists intent_tag (
  intent_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  weight DECIMAL(3,2) NOT NULL DEFAULT 1.0,
  PRIMARY KEY (intent_id, tag_id),
  CONSTRAINT fk_intent_tag_intent
    FOREIGN KEY (intent_id) REFERENCES intent(id),
  CONSTRAINT fk_intent_tag_tag
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);
