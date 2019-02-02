#!/bin/bash


kubectl delete deploy/posts
kubectl delete deploy/connections
kubectl delete deploy/connectionsposts

if [ "$1" = "all" ]; then
	kubectl delete deploy/mysql
	kubectl delete deploy/redis
else
	echo "mysql, redis and other services may still be running"
fi