CREATE TABLE alerts (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL,
    device_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    recorded_value NUMERIC(19, 4) NOT NULL,
    configured_limit NUMERIC(19, 4) NOT NULL,
    status VARCHAR(20) NOT NULL,
    event_timestamp TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    resolved_at TIMESTAMPTZ,
    CONSTRAINT uk_alerts_event_type UNIQUE (event_id, type),
    CONSTRAINT ck_alerts_status CHECK (status IN ('OPEN', 'RESOLVED'))
);

CREATE INDEX idx_alerts_device_status_created_at
    ON alerts (device_id, status, created_at DESC);

CREATE INDEX idx_alerts_status_created_at
    ON alerts (status, created_at DESC);
