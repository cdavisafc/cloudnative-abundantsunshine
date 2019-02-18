#!/bin/bash


kubectl delete deploy/posts
kubectl delete deploy/connections
kubectl delete deploy/connectionsposts
kubectl delete svc/posts-svc
kubectl delete svc/connections-svc
kubectl delete svc/connectionsposts-svc

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