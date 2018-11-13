#!/bin/bash
curl -X POST -H "Content-Type:application/json" --data '{"name":"Cornelia","username":"cdavisafc"}' $(minikube service connections-svc --format "{{.IP}}"):$(minikube service connections-svc --format "{{.Port}}")/users
curl -X POST -H "Content-Type:application/json" --data '{"name":"Max","username":"madmax"}' $(minikube service connections-svc --format "{{.IP}}"):$(minikube service connections-svc --format "{{.Port}}")/users
curl -X POST -H "Content-Type:application/json" --data '{"name":"Glen","username":"gmaxdavis"}' $(minikube service connections-svc --format "{{.IP}}"):$(minikube service connections-svc --format "{{.Port}}")/users

sleep 5

curl -X POST -H "Content-Type:application/json" --data '{"follower":"madmax","followed":"cdavisafc"}' $(minikube service connections-svc --format "{{.IP}}"):$(minikube service connections-svc --format "{{.Port}}")/connections
curl -X POST -H "Content-Type:application/json" --data '{"follower":"cdavisafc","followed":"madmax"}' $(minikube service connections-svc --format "{{.IP}}"):$(minikube service connections-svc --format "{{.Port}}")/connections
curl -X POST -H "Content-Type:application/json" --data '{"follower":"cdavisafc","followed":"gmaxdavis"}' $(minikube service connections-svc --format "{{.IP}}"):$(minikube service connections-svc --format "{{.Port}}")/connections

sleep 5

curl -X POST -H "Content-Type:application/json" --data '{"username":"madmax","title":"Max Title","body":"The body of Max first post"}' $(minikube service posts-svc --format "{{.IP}}"):$(minikube service posts-svc --format "{{.Port}}")/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"cdavisafc","title":"Cornelia Title","body":"The body of Cornelia first post"}' $(minikube service posts-svc --format "{{.IP}}"):$(minikube service posts-svc --format "{{.Port}}")/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"cdavisafc","title":"Cornelia Title2","body":"The body of Cornelia second post"}' $(minikube service posts-svc --format "{{.IP}}"):$(minikube service posts-svc --format "{{.Port}}")/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"gmaxdavis","title":"Glen Title","body":"The body of Glen first post"}' $(minikube service posts-svc --format "{{.IP}}"):$(minikube service posts-svc --format "{{.Port}}")/posts
