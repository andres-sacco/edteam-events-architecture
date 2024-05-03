kafka-topics --create --topic payments --bootstrap-server broker1:29092 --replication-factor 3
echo "topic payments was create"


kafka-topics --create --topic reservation-transactions --bootstrap-server broker1:29092 --replication-factor 3
echo "topic reservation-transactions was create"