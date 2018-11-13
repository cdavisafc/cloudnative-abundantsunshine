#!/bin/bash


kubectl delete deploy/posts
kubectl delete deploy/connections
kubectl delete deploy/connections-posts

if [ "$1" = "all" ]; then
	kubectl delete deploy/mysql
	kubectl delete deploy/kafka
	kubectl delete deploy/zookeeper
else
	echo "mysql, redis and other services may still be running"
fi