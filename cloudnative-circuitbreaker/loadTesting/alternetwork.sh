#!/bin/bash
if [ $2 == "add" ]; then
    action="breaking"
else
    action="fixing"
fi
if [ $1 -gt 0 ]; then
    echo "$action 5 connections"
    kubectl exec posts-7785bcf45-9hnnq -- route $2 -host 10.16.10.89 reject
    kubectl exec posts-7785bcf45-9hnnq -- route $2 -host 10.16.9.110 reject
    kubectl exec posts-7785bcf45-9hnnq -- route $2 -host 10.16.1.76 reject
    kubectl exec posts-7785bcf45-9hnnq -- route $2 -host 10.16.11.48 reject
    kubectl exec posts-7785bcf45-9hnnq -- route $2 -host 10.16.2.95 reject
fi
if [ $1 -gt 1 ]; then
    echo "$action another 5 connections"
    kubectl exec posts-7785bcf45-kj2mm -- route $2 -host 10.16.10.89 reject
    kubectl exec posts-7785bcf45-kj2mm -- route $2 -host 10.16.9.110 reject
    kubectl exec posts-7785bcf45-kj2mm -- route $2 -host 10.16.1.76 reject
    kubectl exec posts-7785bcf45-kj2mm -- route $2 -host 10.16.11.48 reject
    kubectl exec posts-7785bcf45-kj2mm -- route $2 -host 10.16.2.95 reject
fi
if [ $1 -gt 2 ]; then
    echo "$action another 5 connections"
    kubectl exec posts-7785bcf45-xvhqg -- route $2 -host 10.16.10.89 reject
    kubectl exec posts-7785bcf45-xvhqg -- route $2 -host 10.16.9.110 reject
    kubectl exec posts-7785bcf45-xvhqg -- route $2 -host 10.16.1.76 reject
    kubectl exec posts-7785bcf45-xvhqg -- route $2 -host 10.16.11.48 reject
    kubectl exec posts-7785bcf45-xvhqg -- route $2 -host 10.16.2.95 reject
fi
if [ $1 -gt 3 ]; then
    echo "$action another 5 connections"
    kubectl exec posts-7785bcf45-zbvw6 -- route $2 -host 10.16.10.89 reject
    kubectl exec posts-7785bcf45-zbvw6 -- route $2 -host 10.16.9.110 reject
    kubectl exec posts-7785bcf45-zbvw6 -- route $2 -host 10.16.1.76 reject
    kubectl exec posts-7785bcf45-zbvw6 -- route $2 -host 10.16.11.48 reject
    kubectl exec posts-7785bcf45-zbvw6 -- route $2 -host 10.16.2.95 reject
fi