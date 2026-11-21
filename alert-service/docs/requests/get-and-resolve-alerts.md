# Consultar e resolver alertas

Por padrão, o serviço responde em `http://localhost:8084`.

## Pesquisar alertas

Os filtros `deviceId` e `status` são opcionais e podem ser combinados. Os
valores aceitos para `status` são `OPEN` e `RESOLVED`.

```bash
curl --fail-with-body \
  --get \
  --url 'http://localhost:8084/alerts' \
  --data-urlencode "deviceId=$DEVICE_ID" \
  --data-urlencode 'status=OPEN'
```

## Consultar por ID

```bash
curl --fail-with-body \
  --url "http://localhost:8084/alerts/$ALERT_ID"
```

## Resolver

```bash
curl --fail-with-body \
  --request PATCH \
  --url "http://localhost:8084/alerts/$ALERT_ID/resolve"
```

A resolução é idempotente: resolver novamente um alerta não altera seu
`resolvedAt` original. Consultas e resoluções de IDs inexistentes retornam
`404 Not Found`.

## Resposta

```json
{
  "id": "e56dd711-8eb9-4e7d-b8bd-e116e8016e23",
  "eventId": "24dce7a5-8761-4c5f-bde5-23f30e39f78c",
  "deviceId": "2c387d40-3e63-4757-936a-e98964976280",
  "type": "HIGH_TEMPERATURE",
  "recordedValue": 95.5000,
  "configuredLimit": 80.0000,
  "status": "OPEN",
  "timestamp": "2026-11-20T11:59:00Z",
  "createdAt": "2026-11-20T12:00:00Z",
  "resolvedAt": null
}
```
