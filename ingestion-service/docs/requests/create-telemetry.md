# Enviar telemetria

Ao executar o projeto pelo Docker Compose, o serviço responde em
`http://localhost:8082`.

```bash
INGESTION_SERVICE_URL=http://localhost:8082
```

## Requisição

`POST /telemetry`

```bash
curl --fail-with-body \
  --request POST \
  --url "$INGESTION_SERVICE_URL/telemetry" \
  --header 'Content-Type: application/json' \
  --data '{
    "deviceId": "2c387d40-3e63-4757-936a-e98964976280",
    "temperature": 95.5,
    "energyConsumption": 120.8,
    "timestamp": "2026-10-17T15:30:00Z"
  }'
```

## Campos

| Campo | Tipo | Descrição |
| --- | --- | --- |
| `deviceId` | string | Identificador do dispositivo que produziu a leitura |
| `temperature` | decimal | Temperatura registrada pelo dispositivo |
| `energyConsumption` | decimal | Consumo de energia registrado pelo dispositivo |
| `timestamp` | string | Momento em que a leitura ocorreu, no formato ISO 8601 e com fuso horário |

O campo `timestamp` representa o instante da medição no dispositivo, não o
momento em que o servidor recebeu a requisição.

## Resposta

Quando a telemetria é aceita para processamento, o endpoint retorna
`202 Accepted` sem corpo:

```text
HTTP/1.1 202 Accepted
Content-Length: 0
```

O código `202 Accepted` informa que a leitura foi aceita para processamento
assíncrono. Ele não significa que a telemetria já foi persistida ou analisada
pelos serviços consumidores.
