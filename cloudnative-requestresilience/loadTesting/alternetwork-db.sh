#!/bin/bash
if [ $1 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi
echo "$action 5 connections"
kubectl exec mysql-57bdb878f5-dhlck -- route $1 -host 10.36.4.11 reject
kubectl exec mysql-57bdb878f5-dhlck -- route $1 -host 10.36.3.11 reject
kubectl exec mysql-57bdb878f5-dhlck -- route $1 -host 10.36.5.9 reject
kubectl exec mysql-57bdb878f5-dhlck -- route $1 -host 10.36.1.13 reject