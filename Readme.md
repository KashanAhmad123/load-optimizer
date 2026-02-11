# SmartLoad Optimization API

## How to run

```bash
git clone https://github.com/KashanAhmad123/load-optimizer.git
cd load-optimizer
docker compose up --build
# → Service will be available at http://localhost:8080
```

## Health check

```bash
curl http://localhost:8080/actuator/health
# Expected: {"status":"UP"}
```

## Example request

```bash
curl -X POST http://localhost:8080/api/v1/load-optimizer/optimize \
  -H "Content-Type: application/json" \
  -d @sample-request.json
```

**Response:**

```json
{
  "truck_id": "truck-123",
  "selected_order_ids": ["ord-001", "ord-002"],
  "total_payout_cents": 430000,
  "total_weight_lbs": 30000,
  "total_volume_cuft": 2100,
  "utilization_weight_percent": 68.18,
  "utilization_volume_percent": 70.0
}
```

## API Specification

**Endpoint:** `POST /api/v1/load-optimizer/optimize`

**Request Body:**
```json
{
  "truck": {
    "id": "string",
    "max_weight_lbs": "integer",
    "max_volume_cuft": "integer"
  },
  "orders": [
    {
      "id": "string",
      "payout_cents": "long",
      "weight_lbs": "integer",
      "volume_cuft": "integer",
      "origin": "string",
      "destination": "string",
      "pickup_date": "YYYY-MM-DD",
      "delivery_date": "YYYY-MM-DD",
      "is_hazmat": "boolean"
    }
  ]
}
```

## Algorithm

- **Dynamic Programming** (n ≤ 20): Optimal solution
- **Greedy** (n > 20): Fast approximation
- **Performance:** < 800ms for 22 orders

## Constraints

- Weight & volume capacity
- Same origin/destination for all orders
- Hazmat isolation (no mixing)
- Valid time windows
- Money in integer cents

## Tech Stack

Java 17 • Spring Boot 3.2.1 • Maven • Docker