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
    "eventId": "24dce7a5-8761-4c5f-bde5-23f30e39f78c",
    "deviceId": "2c387d40-3e63-4757-936a-e98964976280",
    "temperature": 95.5,
    "energyConsumption": 120.8,
    "timestamp": "2026-10-17T15:30:00Z"
  }'
```

## Campos

| Campo | Tipo | Obrigatório | Restrições |
| --- | --- | --- | --- |
| `eventId` | UUID | Sim | Identificador único da leitura |
| `deviceId` | UUID | Sim | Deve identificar um dispositivo cadastrado e ativo |
| `temperature` | decimal | Sim | Valor entre `-100.0` e `1000.0` |
| `energyConsumption` | decimal | Sim | Valor maior ou igual a zero |
| `timestamp` | string | Sim | Momento da medição no formato ISO 8601 |

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

## Rejeições

- Requisições com campos ausentes, formatos inválidos ou valores fora das
  faixas aceitas retornam `400 Bad Request`.
- Um `deviceId` sem dispositivo correspondente retorna `404 Not Found`.
- Um dispositivo inativo retorna `422 Unprocessable Content`.
- Uma falha na publicação não retorna `202 Accepted`; o erro do Kafka é
  propagado pela aplicação.

Limites operacionais configurados no dispositivo não são usados para rejeitar
a leitura. Uma temperatura acima do limite ainda precisa ser publicada para
que o `alert-service` possa analisá-la.
