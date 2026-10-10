# Multi-Channel Notification Gateway (Bridge Pattern)

## 📌 Project Overview
This project implements a Multi-Channel Notification Gateway using the **Bridge Design Pattern** in Java 17+[cite: 1]. The architecture decouples notification abstractions (types of notifications) from implementation details (message delivery providers), allowing both hierarchies to evolve independently[cite: 1].

---

## 📊 UML Class Diagram
The following class diagram illustrates the relationship between the abstraction, refined abstractions, the implementor interface, and concrete senders[cite: 1].

>![sdp33.drawio.png](../../%D0%97%D0%B0%D0%B3%D1%80%D1%83%D0%B7%D0%BA%D0%B8/sdp33.drawio.png)
*Key components identified on the diagram:*
* **Abstraction:** Base abstract class `Notification`[cite: 1].
* **Refined Abstraction:** `UrgentNotification`, `EncryptedNotification`, `ScheduledBatchNotification`, `MarketingNotification`[cite: 1].
* **Implementor:** Interface `MessageSender`[cite: 1].
* **Concrete Implementor:** `TelegramMessageSender`, `EmailMessageSender`, `SmsMessageSender`, `KafkaEventSender`, `PushNotificationSender`[cite: 1].
* **Bridge Relationship:** The `has-a` association between `Notification` and `MessageSender`[cite: 1].

---

## ❓ Individual Defense Answers

### 1. What are the two independent dimensions?
* **Abstraction Dimension (Notification Types):** Defines business logic and sending rules (e.g., urgent, encrypted, batch, marketing)[cite: 1].
* **Implementation Dimension (Delivery Providers):** Defines the technical channel and mechanism for sending messages (e.g., Telegram, Email, SMS, Kafka, Push)[cite: 1].

### 2. Where exactly is the Bridge?
The bridge is located inside the base abstract class `Notification`, which holds a reference to the `MessageSender` interface (`protected MessageSender sender`)[cite: 1]. This aggregation (`has-a` relationship) allows the abstraction to delegate delivery to the implementation at runtime.

### 3. Why is inheritance problematic here?
Using pure inheritance without the Bridge pattern would require creating a separate subclass for every combination of notification type and delivery channel (e.g., `UrgentTelegramNotification`, `EncryptedEmailNotification`, etc.). With $N$ notification types and $M$ providers, this leads to a combinatorial explosion of classes ($N \times M$), code duplication, and rigid coupling[cite: 1].

### 4. What happens when a new provider is added?
When adding a new provider (e.g., `PushNotificationSender`), we simply create a new class implementing the `MessageSender` interface. Existing notification classes (`Notification`) remain entirely unmodified, fully adhering to the Open/Closed Principle[cite: 1].

### 5. What happens when a new Notification type is added?
When adding a new notification type (e.g., `MarketingNotification`), we extend the `Notification` abstract class. Existing provider implementations (`MessageSender`) remain untouched and require no modifications[cite: 1].

### 6. Why is this Bridge rather than Adapter?
* **Adapter** is used to make existing, incompatible classes work together by wrapping them and changing their interface *after* they are written.
* **Bridge** is designed upfront to separate orthogonal dimensions (abstraction and implementation) so they can independently vary and scale throughout the design and development lifecycle[cite: 1].