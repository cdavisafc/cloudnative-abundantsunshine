#!/bin/bash


kubectl delete deploy/posts
kubectl delete deploy/connections
kubectl delete deploy/connection-posts
kubectl delete svc/posts-svc
kubectl delete svc/connections-svc
kubectl delete svc/connection-posts-svc

if [ "$1" = "all" ]; then
	kubectl delete deploy/mysql
	kubectl delete deploy/redis
	kubectl delete deploy/sccs
	kubectl delete svc/mysql-svc
	kubectl delete svc/redis-svc
	kubectl delete svc/sccs-svc
else
	echo "mysql, redis and other services may still be running"
fi