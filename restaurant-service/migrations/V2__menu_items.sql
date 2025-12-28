
CREATE TABLE IF NOT EXISTS menu_items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    currency VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),
    CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id)
      REFERENCES restaurants(id)
  );

  CREATE INDEX IF NOT EXISTS idx_item_restaurant_id ON menu_items(restaurant_id);
  CREATE INDEX IF NOT EXISTS idx_item_price ON menu_items(price);