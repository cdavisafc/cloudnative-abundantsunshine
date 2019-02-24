#!/bin/bash
if [ $1 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi

echo "$action connections between posts and mysql"
kubectl exec mysql-7496bdd68f-9vrgh -- route $1 -host 10.36.2.15 reject
kubectl exec mysql-7496bdd68f-9vrgh -- route $1 -host 10.36.1.30 reject
kubectl exec mysql-7496bdd68f-9vrgh -- route $1 -host 10.36.4.21 reject
kubectl exec mysql-7496bdd68f-9vrgh -- route $1 -host 10.36.3.18 reject