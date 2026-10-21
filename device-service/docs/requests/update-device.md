# Atualizar dispositivo

Por padrão, o serviço responde em `http://localhost:8080`.

```bash
DEVICE_SERVICE_URL=http://localhost:8080
DEVICE_ID=2c387d40-3e63-4757-936a-e98964976280
```

## Requisição

`PUT /devices/{id}`

O estado ativo não é alterado por este endpoint. Use `PATCH /devices/{id}/status`
para ativar ou desativar o dispositivo.

```bash
curl --fail-with-body \
  --request PUT \
  --url "$DEVICE_SERVICE_URL/devices/$DEVICE_ID" \
  --header 'Content-Type: application/json' \
  --data '{
    "name": "Boiler A Sensor",
    "type": "TEMPERATURE",
    "location": "Factory 02",
    "minimumLimit": 15.00,
    "maximumLimit": 85.00
  }'
```

## Campos

| Campo | Tipo | Obrigatório | Restrições |
| --- | --- | --- | --- |
| `name` | string | Sim | Não pode estar vazio; máximo de 150 caracteres |
| `type` | string | Sim | Não pode estar vazio; máximo de 50 caracteres |
| `location` | string | Não | Máximo de 255 caracteres |
| `minimumLimit` | decimal | Não | Até 8 dígitos inteiros e 2 casas decimais |
| `maximumLimit` | decimal | Não | Até 8 dígitos inteiros e 2 casas decimais; não pode ser menor que `minimumLimit` |

## Resposta

O endpoint retorna `200 OK` com o dispositivo atualizado. Um identificador UUID
sem dispositivo correspondente retorna `404 Not Found`.
