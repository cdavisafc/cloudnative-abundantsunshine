#!/bin/bash
if [ $1 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi

echo "$action connections between posts and mysql"
kubectl exec mysql-7496bdd68f-gkz58 -- route $1 -host 10.36.1.58 reject
kubectl exec mysql-7496bdd68f-gkz58 -- route $1 -host 10.36.3.43 reject