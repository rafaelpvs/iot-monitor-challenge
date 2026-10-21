# Consultar dispositivo

Por padrão, o serviço responde em `http://localhost:8080`.

```bash
DEVICE_SERVICE_URL=http://localhost:8080
DEVICE_ID=2c387d40-3e63-4757-936a-e98964976280
```

## Requisição

`GET /devices/{id}`

```bash
curl --fail-with-body \
  --request GET \
  --url "$DEVICE_SERVICE_URL/devices/$DEVICE_ID"
```

## Resposta

```json
{
  "id": "2c387d40-3e63-4757-936a-e98964976280",
  "name": "Boiler A Sensor",
  "type": "TEMPERATURE",
  "location": "Factory 01",
  "active": true,
  "minimumLimit": 20.00,
  "maximumLimit": 80.00,
  "createdAt": "2026-10-20T18:00:00Z",
  "updatedAt": "2026-10-20T18:00:00Z"
}
```

Um identificador UUID sem dispositivo correspondente retorna `404 Not Found`.
