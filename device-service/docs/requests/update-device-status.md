# Atualizar status do dispositivo

Por padrão, o serviço responde em `http://localhost:8080`.

```bash
DEVICE_SERVICE_URL=http://localhost:8080
DEVICE_ID=2c387d40-3e63-4757-936a-e98964976280
```

## Requisição

`PATCH /devices/{id}/status`

```bash
curl --fail-with-body \
  --request PATCH \
  --url "$DEVICE_SERVICE_URL/devices/$DEVICE_ID/status" \
  --header 'Content-Type: application/json' \
  --data '{
    "active": false
  }'
```

O campo `active` é obrigatório e aceita `true` ou `false`.

## Resposta

O endpoint retorna `200 OK` com o dispositivo atualizado. Um identificador UUID
sem dispositivo correspondente retorna `404 Not Found`.
