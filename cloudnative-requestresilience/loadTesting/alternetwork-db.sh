#!/bin/bash
if [ $1 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi

echo "$action connections between posts and mysql"
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.3.21 reject
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.11.18 reject
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.15.19 reject
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.14.24 reject