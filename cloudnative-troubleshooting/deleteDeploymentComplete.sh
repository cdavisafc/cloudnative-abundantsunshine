#!/bin/bash


kubectl delete deploy/posts
kubectl delete deploy/connections
kubectl delete deploy/connection-posts

if [ "$1" = "all" ]; then
	kubectl delete deploy/mysql
	kubectl delete deploy/redis
	kubectl delete deploy/sccs
else
	echo "mysql, redis and other services may still be running"
fi