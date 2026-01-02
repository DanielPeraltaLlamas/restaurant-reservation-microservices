CREATE TABLE IF NOT EXISTS reservations
(
       id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
       restaurant_id BIGINT NOT NULL,
       availability_slot_id BIGINT NOT NULL,
       customer_name VARCHAR(100) NOT NULL,
       customer_email VARCHAR(100) NOT NULL,
       customer_phone VARCHAR(20),
       party_size INT NOT NULL CHECK (party_size > 0),
       special_requests TEXT,
       status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
       scheduled_at TIMESTAMPTZ NOT NULL,
       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
       updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
       cancelled_at TIMESTAMPTZ,
       confirmed_at TIMESTAMPTZ

);

CREATE INDEX idx_reservations_restaurant_id ON reservations(restaurant_id);
CREATE INDEX idx_reservations_availability_slot_id ON reservations(availability_slot_id);
CREATE INDEX idx_reservations_customer_email ON reservations(customer_email);
CREATE INDEX idx_reservations_scheduled_at ON reservations(scheduled_at);
CREATE INDEX idx_reservations_status ON reservations(status);