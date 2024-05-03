kafka-topics --create --topic payments --bootstrap-server broker1:29092 --replication-factor 3
echo "topic payments was create"


kafka-topics --create --topic reservations-payments --bootstrap-server broker1:29092 --replication-factor 3
echo "topic reservations-payments was create"