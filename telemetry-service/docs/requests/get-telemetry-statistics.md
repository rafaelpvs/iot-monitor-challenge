# Consultar estatísticas de telemetria

## Requisição

`GET /telemetry/{deviceId}/statistics`

```bash
curl --fail-with-body \
  --get \
  --url "http://localhost:8083/telemetry/$DEVICE_ID/statistics" \
  --data-urlencode 'start=2026-11-01T00:00:00Z' \
  --data-urlencode 'end=2026-11-02T00:00:00Z'
```

`start` e `end` são obrigatórios, inclusivos e devem estar no formato ISO 8601.

## Resposta

```json
{
  "temperature": {
    "average": 30.5000,
    "minimum": 20.0000,
    "maximum": 41.0000
  },
  "energyConsumption": {
    "average": 110.0000,
    "minimum": 100.0000,
    "maximum": 120.0000
  }
}
```

Se não houver leituras no período, todos os agregados serão `null`. Um período
em que `start` seja posterior a `end` retorna `400 Bad Request`.
