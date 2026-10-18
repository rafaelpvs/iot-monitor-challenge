# Device Service — requisições cURL

Por padrão, o serviço responde em `http://localhost:8080`.

Defina a URL-base para reutilizá-la nos exemplos:

```bash
DEVICE_SERVICE_URL=http://localhost:8080
```

## Cadastrar dispositivo

`POST /devices`

```bash
curl --fail-with-body \
  --request POST \
  --url "$DEVICE_SERVICE_URL/devices" \
  --header 'Content-Type: application/json' \
  --data '{
    "name": "Boiler A Sensor",
    "type": "TEMPERATURE",
    "location": "Factory 01",
    "active": true,
    "minimumLimit": 20.00,
    "maximumLimit": 80.00
  }'
```

### Campos

| Campo | Tipo | Obrigatório | Restrições |
| --- | --- | --- | --- |
| `name` | string | Sim | Não pode estar vazio; máximo de 150 caracteres |
| `type` | string | Sim | Não pode estar vazio; máximo de 50 caracteres |
| `location` | string | Não | Máximo de 255 caracteres |
| `active` | boolean | Sim | `true` ou `false` |
| `minimumLimit` | decimal | Não | Até 8 dígitos inteiros e 2 casas decimais |
| `maximumLimit` | decimal | Não | Até 8 dígitos inteiros e 2 casas decimais; não pode ser menor que `minimumLimit` |

Formato de resposta esperado após a persistência do dispositivo:

```json
{
  "id": "2c387d40-3e63-4757-936a-e98964976280",
  "name": "Boiler A Sensor"
}
```

## Exemplos de validação

### Nome obrigatório

```bash
curl --fail-with-body \
  --request POST \
  --url "$DEVICE_SERVICE_URL/devices" \
  --header 'Content-Type: application/json' \
  --data '{
    "name": "",
    "type": "TEMPERATURE",
    "active": true
  }'
```

### Intervalo de limites inválido

```bash
curl --fail-with-body \
  --request POST \
  --url "$DEVICE_SERVICE_URL/devices" \
  --header 'Content-Type: application/json' \
  --data '{
    "name": "Boiler A Sensor",
    "type": "TEMPERATURE",
    "active": true,
    "minimumLimit": 80.00,
    "maximumLimit": 20.00
  }'
```

## Resposta de erro de validação

Requisições que não atendem às validações retornam HTTP `400 Bad Request`:

```json
{
  "timestamp": "2026-10-18T18:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Request validation failed",
  "path": "/devices",
  "violations": [
    {
      "field": "name",
      "message": "must not be blank"
    }
  ]
}
```

Quando o corpo estiver ausente ou contiver JSON malformado, a resposta terá a
mensagem `Request body is missing or malformed` e a lista `violations` vazia.
