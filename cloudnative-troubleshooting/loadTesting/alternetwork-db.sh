#!/bin/bash
if [ $1 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi

echo "$action connections between posts and mysql"
kubectl exec mysql-7496bdd68f-4wmdv -- route $1 -host 10.44.4.71 reject
kubectl exec mysql-7496bdd68f-4wmdv -- route $1 -host 10.44.2.64 reject