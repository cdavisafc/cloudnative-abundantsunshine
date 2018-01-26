#!/bin/bash


kubectl delete deploy/posts
kubectl delete deploy/connections
kubectl delete deploy/connection-posts
kubectl delete svc/posts
kubectl delete svc/connections
kubectl delete svc/connection-posts

if [ "$1" = "all" ]; then
	kubectl delete deploy/mysql
	kubectl delete deploy/redis
	kubectl delete deploy/sccs
	kubectl delete svc/mysql
	kubectl delete svc/redis
	kubectl delete svc/sccs
else
	echo "mysql, redis and other services may still be running"
fi