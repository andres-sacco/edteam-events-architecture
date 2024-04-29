---
name: ReservationTransaction
version: 0.0.1
summary: |
  Holds information about the reservation that has been changed.
producers:
    - Reservations Service
owners:
    - asacco
---

<Admonition>When firing this event make sure you set the `correlation-id` in the headers. Our schemas have standard metadata make sure you read and follow it.</Admonition>

### Details

This event is triggered when the reservations has successfully been updated.

<NodeGraph title="Consumer / Producer Diagram" />
