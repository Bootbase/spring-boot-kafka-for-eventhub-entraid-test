# Kafka Event Hub Connectivity Test

Test Kafka producer/consumer connectivity to Azure Event Hub with Entra ID Service Principal (Client ID & Secret) authentication.

## Prerequisites

- Java 8
- Maven 3.x
- Docker (optional)

## Setup

1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edit `.env` with your Azure credentials:
   ```
   AZURE_TENANT_ID=your-tenant-id
   AZURE_CLIENT_ID=your-client-id
   AZURE_CLIENT_SECRET=your-client-secret
   EVENT_HUB_NAMESPACE_ENDPOINT=your-namespace.servicebus.windows.net
   EVENT_HUB_TOPIC_NAME=your-topic-name

   ```

## Run without Docker

```bash
./run.sh [mode]
```

### Run Modes

| Mode | Description |
|------|-------------|
| `both` | (default) Run producer and consumer together |
| `producer` | Send a message and exit |
| `consumer` | Listen for messages only |

## Run with Docker

```bash
# Default mode (both)
docker compose up --build

# Specific mode
APP_MODE=producer docker compose up --build
APP_MODE=consumer docker compose up --build
```

## Expected Output

On success, you'll see the producer send and consumer receive a test message:

```
========================================
PRODUCER: Sending message to topic [your-topic]
Message: Hello from Spring Boot Kafka - 1234567890
========================================

========================================
PRODUCER: Message sent successfully!
Topic: your-topic
Partition: 0
Offset: 123
========================================

========================================
CONSUMER: Message received from topic [your-topic]
Message: Hello from Spring Boot Kafka - 1234567890
Partition: 0
Offset: 123
========================================
```
