# Consultar a telemetria mais recente

## Requisição

`GET /telemetry/{deviceId}/latest`

```bash
curl --fail-with-body \
  --url "http://localhost:8083/telemetry/$DEVICE_ID/latest"
```

## Resposta

```json
{
  "eventId": "24dce7a5-8761-4c5f-bde5-23f30e39f78c",
  "deviceId": "2c387d40-3e63-4757-936a-e98964976280",
  "temperature": 95.5000,
  "energyConsumption": 120.8000,
  "timestamp": "2026-11-01T15:30:00Z"
}
```

A leitura é determinada pelo `timestamp` do evento, e não pelo horário em que
ela chegou ao serviço. Se não houver leituras para o dispositivo, a resposta é
`404 Not Found`.
