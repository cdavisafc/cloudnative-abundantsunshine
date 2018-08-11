#!/bin/bash
if [ $1 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi

echo "$action connections between posts and mysql"
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.3.25 reject
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.4.21 reject
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.16.22 reject
kubectl exec mysql-79bd966484-5kl7j -- route $1 -host 10.32.8.32 reject