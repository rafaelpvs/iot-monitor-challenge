# Consultar histórico de telemetria

Por padrão, o serviço responde em `http://localhost:8083`.

```bash
TELEMETRY_SERVICE_URL=http://localhost:8083
DEVICE_ID=2c387d40-3e63-4757-936a-e98964976280
```

## Requisição

`GET /telemetry/{deviceId}`

```bash
curl --fail-with-body \
  --get \
  --url "$TELEMETRY_SERVICE_URL/telemetry/$DEVICE_ID" \
  --data-urlencode 'start=2026-11-01T00:00:00Z' \
  --data-urlencode 'end=2026-11-02T00:00:00Z' \
  --data-urlencode 'page=0' \
  --data-urlencode 'size=20'
```

## Parâmetros

| Parâmetro | Obrigatório | Padrão | Restrições |
| --- | --- | --- | --- |
| `deviceId` | Sim | — | UUID do dispositivo |
| `start` | Sim | — | Início inclusivo no formato ISO 8601 |
| `end` | Sim | — | Fim inclusivo no formato ISO 8601; deve ser maior ou igual a `start` |
| `page` | Não | `0` | Maior ou igual a zero |
| `size` | Não | `20` | Entre `1` e `100` |

Os resultados são ordenados do evento mais recente para o mais antigo.

## Resposta

```json
{
  "content": [
    {
      "eventId": "24dce7a5-8761-4c5f-bde5-23f30e39f78c",
      "deviceId": "2c387d40-3e63-4757-936a-e98964976280",
      "temperature": 95.5000,
      "energyConsumption": 120.8000,
      "timestamp": "2026-11-01T15:30:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1,
  "first": true,
  "last": true
}
```

Quando não houver leituras no período, `content` será uma lista vazia. Períodos
invertidos, parâmetros inválidos e paginação fora dos limites retornam
`400 Bad Request`.
