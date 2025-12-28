CREATE TABLE IF NOT EXISTS availability_slots (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL,
    seats_available INT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),
    CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id)
      REFERENCES restaurants(id)
  );

  CREATE INDEX IF NOT EXISTS idx_slot_restaurant_id ON availability_slots(restaurant_id);
  CREATE INDEX IF NOT EXISTS idx_slot_date ON availability_slots(date);
  CREATE INDEX IF NOT EXISTS idx_slot_start_time ON availability_slots(start_time);