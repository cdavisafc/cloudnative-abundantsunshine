#!/bin/bash
curl -X POST -H "Content-Type:application/json" --data '{"name":"Cornelia","username":"cdavisafc"}' localhost:8081/users
curl -X POST -H "Content-Type:application/json" --data '{"name":"Max","username":"madmax"}' localhost:8081/users
curl -X POST -H "Content-Type:application/json" --data '{"name":"Glen","username":"gmaxdavis"}' localhost:8081/users

sleep 5

curl -X POST -H "Content-Type:application/json" --data '{"follower":"madmax","followed":"cdavisafc"}' localhost:8081/connections
curl -X POST -H "Content-Type:application/json" --data '{"follower":"cdavisafc","followed":"madmax"}' localhost:8081/connections
curl -X POST -H "Content-Type:application/json" --data '{"follower":"cdavisafc","followed":"gmaxdavis"}' localhost:8081/connections

sleep 5

curl -X POST -H "Content-Type:application/json" --data '{"username":"madmax","title":"Chicken Pho","body":"This is my attempt to recreate what I ate in Vietnam..."}' localhost:8082/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"cdavisafc","title":"Whole Orange Cake","body":"That'\''s right, you blend up whole oranges, rind and all..."}' localhost:8082/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"cdavisafc","title":"German Dumplings (Kloesse)","body":"Russet potatoes, flour (gluten free!) and more..."}' localhost:8082/posts
curl -X POST -H "Content-Type:application/json" --data '{"username":"gmaxdavis","title":"French Press Lattes","body":"We'\''ve figured out how to make these dairy free, but just as good!..."}' localhost:8082/posts
