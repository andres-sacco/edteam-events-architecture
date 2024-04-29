---
name: Payments
version: 0.0.1
summary: |
  Holds information about the payment that has been processed.
producers:
    - Payments Service
consumers:
    - Reservations Service
owners:
    - asacco
---

<Admonition>When firing this event make sure you set the `correlation-id` in the headers. Our schemas have standard metadata make sure you read and follow it.</Admonition>

### Details

This event is triggered when the payment has successfully been processed .

<NodeGraph title="Consumer / Producer Diagram" />
