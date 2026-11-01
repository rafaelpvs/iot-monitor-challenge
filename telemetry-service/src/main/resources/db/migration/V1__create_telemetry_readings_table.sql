CREATE TABLE telemetry_readings (
    event_id UUID PRIMARY KEY,
    device_id UUID NOT NULL,
    temperature NUMERIC(19, 4) NOT NULL,
    energy_consumption NUMERIC(19, 4) NOT NULL,
    event_timestamp TIMESTAMPTZ NOT NULL,
    received_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_telemetry_readings_device_timestamp
    ON telemetry_readings (device_id, event_timestamp DESC);
