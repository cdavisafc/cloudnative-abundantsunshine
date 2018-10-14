#!/bin/bash
kubectl apply -f spring-cloud-config-server-deployment-kubernetes.yaml
kubectl apply -f redis-deployment.yaml
kubectl apply -f mysql-deployment.yaml
# after creating mysql must create the cookbook database

