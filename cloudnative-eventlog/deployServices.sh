#!/bin/bash
kubectl apply -f mysql-deployment.yaml
# after creating mysql must create the cookbook database
kubectl apply -f zookeeper-deployment.yaml
kubectl apply -f kafka-deployment.yaml

