CREATE TABLE IF NOT EXISTS notifications
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    notification_id VARCHAR(100) NOT NULL,
    reservation_id VARCHAR(100) NOT NULL,
    recipient VARCHAR(150) NOT NULL,

    event_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,

    occurred_at TIMESTAMPTZ NOT NULL,
    trace_id VARCHAR(100) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);


CREATE INDEX idx_notifications_reservation_id ON notifications(reservation_id);
CREATE INDEX idx_notifications_recipient ON notifications(recipient);
CREATE INDEX idx_notifications_event_type ON notifications(event_type);
CREATE INDEX idx_notifications_status ON notifications(status);
CREATE INDEX idx_notifications_trace_id ON notifications(trace_id);
