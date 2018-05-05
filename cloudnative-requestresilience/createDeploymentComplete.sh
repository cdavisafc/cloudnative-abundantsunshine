#!/bin/bash

kubectl apply -f spring-cloud-config-server-deployment-kubernetes.yaml
kubectl apply -f mysql-deployment.yaml
kubectl apply -f redis-deployment.yaml
kubectl apply -f cookbook-deployment-kubernetes-posts.yaml
kubectl apply -f cookbook-deployment-kubernetes-connections.yaml
kubectl apply -f cookbook-deployment-kubernetes-connectionposts.yaml