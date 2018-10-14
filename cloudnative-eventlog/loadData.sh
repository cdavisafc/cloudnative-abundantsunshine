#!/bin/bash
curl -X POST -H "Content-Type:application/json" --data '{"name":"Cornelia","username":"cdavisafc"}' localhost:8082/users
curl -X POST -H "Content-Type:application/json" --data '{"name":"Max","username":"madmax"}' localhost:8082/users
curl -X POST -H "Content-Type:application/json" --data '{"name":"Glen","username":"gmaxdavis"}' localhost:8082/users

curl -X POST -H "Content-Type:application/json" --data '{"follower":"madmax","followed":"cdavisafc"}' localhost:8082/connections
curl -X POST -H "Content-Type:application/json" --data '{"follower":"cdavisafc","followed":"madmax"}' localhost:8082/connections
curl -X POST -H "Content-Type:application/json" --data '{"follower":"cdavisafc","followed":"gmaxdavis"}' localhost:8082/connections

curl -X POST -H "Content-Type:application/json" --data '{"username":"madmax","title":"Max Title","body":"The body of Max first post"}' localhost:8081/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"cdavisafc","title":"Cornelia Title","body":"The body of Cornelia first post"}' localhost:8081/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"cdavisafc","title":"Cornelia Title2","body":"The body of Cornelia second post"}' localhost:8081/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"gmaxdavis","title":"Glen Title","body":"The body of Glen first post"}' localhost:8081/posts
