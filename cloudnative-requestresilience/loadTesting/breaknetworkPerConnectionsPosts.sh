#!/bin/bash
if [ $1 -gt 0 ]; then
    echo "breaking all connections from 10.16.2.82"
    kubectl exec posts-7785bcf45-9tfj4 -- route $2 -host 10.16.2.82 reject
    kubectl exec posts-7785bcf45-bsn8g -- route $2 -host 10.16.2.82 reject
    kubectl exec posts-7785bcf45-w5xzs -- route $2 -host 10.16.2.82 reject
    kubectl exec posts-7785bcf45-wtbv8 -- route $2 -host 10.16.2.82 reject
fi
if [ $1 -gt 1 ]; then
    echo "breaking all connections from 10.16.11.29"
    kubectl exec posts-7785bcf45-9tfj4 -- route $2 -host 10.16.11.29 reject
    kubectl exec posts-7785bcf45-bsn8g -- route $2 -host 10.16.11.29 reject
    kubectl exec posts-7785bcf45-w5xzs -- route $2 -host 10.16.11.29 reject
    kubectl exec posts-7785bcf45-wtbv8 -- route $2 -host 10.16.11.29 reject
fi
if [ $1 -gt 2 ]; then
    echo "breaking all connections from 10.16.12.22"
    kubectl exec posts-7785bcf45-9tfj4 -- route $2 -host 10.16.12.22 reject
    kubectl exec posts-7785bcf45-bsn8g -- route $2 -host 10.16.12.22 reject
    kubectl exec posts-7785bcf45-w5xzs -- route $2 -host 10.16.12.22 reject
    kubectl exec posts-7785bcf45-wtbv8 -- route $2 -host 10.16.12.22 reject
fi
if [ $1 -gt 3 ]; then
    echo "breaking all connections from 10.16.1.67"
    kubectl exec posts-7785bcf45-9tfj4 -- route $2 -host 10.16.1.67 reject
    kubectl exec posts-7785bcf45-bsn8g -- route $2 -host 10.16.1.67 reject
    kubectl exec posts-7785bcf45-w5xzs -- route $2 -host 10.16.1.67 reject
    kubectl exec posts-7785bcf45-wtbv8 -- route $2 -host 10.16.1.67 reject
fi
if [ $1 -gt 4 ]; then
    echo "breaking all connections from 10.16.2.83"
    kubectl exec posts-7785bcf45-9tfj4 -- route $2 -host 10.16.2.83 reject
    kubectl exec posts-7785bcf45-bsn8g -- route $2 -host 10.16.2.83 reject
    kubectl exec posts-7785bcf45-w5xzs -- route $2 -host 10.16.2.83 reject
    kubectl exec posts-7785bcf45-wtbv8 -- route $2 -host 10.16.2.83 reject
fi