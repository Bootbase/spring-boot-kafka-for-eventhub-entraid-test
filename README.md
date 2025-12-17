# Kafka Event Hub Connectivity Test

Test Kafka producer/consumer connectivity to Azure Event Hub with Entra ID authentication.

> **Note**: Requires Java 21

## Setup

1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edit `.env` with your Azure credentials:
   ```bash
   AZURE_TENANT_ID=your-tenant-id
   AZURE_CLIENT_ID=your-client-id
   AZURE_CLIENT_SECRET=your-client-secret
   EVENT_HUB_NAMESPACE_ENDPOINT=your-namespace.servicebus.windows.net
   EVENT_HUB_TOPIC_NAME=your-topic-name
   EVENT_HUB_CONSUMER_GROUP=$Default
   ```

## Build & Run

```bash
./run.sh [mode]
```

### Run Modes

| Mode | Description |
|------|-------------|
| `both` | (default) Run producer and consumer together |
| `producer` | Send a message and exit |
| `consumer` | Listen for messages only |

### Testing Consumer with Pre-existing Messages

To test that the consumer can read messages sent before it started:

```bash
# Terminal 1: Send a message
./run.sh producer

# Terminal 2: Start consumer (reads from earliest offset)
./run.sh consumer
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
