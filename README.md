# 🧾 Order Service - Kims Convenience Microservices

The **Order Service** is an **event-driven microservice** responsible for processing submitted orders from the Cart
Service. It listens to Kafka events, creates and manages order records, and orchestrates the downstream services
including Payment, Inventory, Order Tracking, and Notifications.

## 🔁 Responsibilities

- 📨 **Consume Kafka Events**: Listens to `order.submitted` events published by the Cart Service.
- 🧾 **Create Order**: Creates and persists the Order entity in its own database.
- 🤝 **Orchestrate Services**:
    - Initiates **Payment Instrument creation**.
    - Triggers **Inventory hold/reservation**.
    - Sends events to **Order Tracking** and **Notification** services.
- 🔄 Maintains order status lifecycle: `RECEIVED → PAYMENT_INITIATED → INVENTORY_RESERVED → CONFIRMED`

## ⚙️ Tech Stack

- Java 17
- Spring Boot (WebFlux, Kafka, JPA)
- MySQL (separate DB from other services)
- Kafka for asynchronous event handling
- Docker & Kubernetes (StatefulSet, ConfigMaps, PVC)
- Grafana + Loki for logging and observability

## 📦 Kafka Topics

| Topic Name            | Direction | Description                          |
|----------------------|-----------|--------------------------------------|
| `order.submitted`     | Inbound   | Consumed from Cart Service           |
| `payment.requested`   | Outbound  | Triggers Payment Service             |
| `inventory.requested` | Outbound  | Triggers Inventory Service           |
| `order.confirmed`     | Outbound  | Sent when order is fully confirmed   |
| `notification.events` | Outbound  | Sends notifications to Notification Service |

## 🏗️ Architecture Overview

```plaintext
                        +------------------------+
                        |     Cart Service       |
                        |   (Publishes Order)    |
                        +-----------+------------+
                                    |
                                Kafka Topic
                             'order.submitted'
                                    |
+----------------------+            ▼           +---------------------------+
| KafkaConsumerConfig  | --> [Order Service] -->|  Payment / Inventory /    |
| (Spring Kafka)       |                        |  Tracking / Notification  |
+----------------------+                        +---------------------------+

```

## 🛠️ Setup Instructions

### Clone the repository

git clone git@github.com:kims-convenience/order-service.git

### Deploy Loki-Grafana

$ docker compose -f docker-compose-observability.yml up -d

Access Grafana - http://localhost:3000/

### Deploy Kafka

$ kubectl apply -f kafka-kraft-deployment.yml

#### -- Accessing Kafka messages

$ kubectl exec -it <pod_name> -- /bin/bash

[# cd /opt/kafka/bin

[# ./kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic order.submitted \
--from-beginning

#### -- Accessing Kafka messages with metadata

$ kubectl exec -it <pod_name> -- /bin/bash

[# cd /opt/kafka/bin

[# ./kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic order.submitted \
--from-beginning \
--property print.key=true \
--property print.headers=true \
--property print.timestamp=true \
--property print.partition=true \
--property print.offset=true

### Deploy MySQL

$ kubectl apply -f mysql-secret.yml

$ kubectl apply -f mysql-configmap.yml

$ kubectl apply -f mysql-deployment.yml

#### -- Accessing MySQL DB

$ kubectl exec -it <container_id_or_name> -- /bin/bash

[# mysql -h kc-order-mysql-service -u root -p

[Enter password : password

[mysql> use order_db;

[mysql> show tables;

### Deploying Order

#### -- Create & Push Docker Image

$ mvn clean install

$ docker build -f Dockerfile -t anshikam/kims-convenience-order .

$ docker push anshikam/kims-convenience-order

#### -- Deploy K8 resources for Order

$ kubectl apply -f order-configmap.yml

$ kubectl apply -f order-deployment.yml

$ kubectl scale --replicas=0 deployment order-deployment

### Accessing pod & other K8 resources

$ kubectl get pods

$ kubectl logs <pod_name>

$ kubectl exec -it <pod_name> -- /bin/sh

$ kubectl scale --replicas=0 deployment order-deployment

$ kubectl get all

$ kubectl describe <resource_type> <resource_name>

$ kubectl delete <resource_type> <resource_name>
