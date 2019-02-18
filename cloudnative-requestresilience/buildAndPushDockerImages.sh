docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-connectionposts:0.0.1 .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-connections:0.0.1 .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-posts:0.0.1 .
#
docker push cdavisafc/cloudnative-requestresilience-connectionposts:0.0.1
docker push cdavisafc/cloudnative-requestresilience-connections:0.0.1
docker push cdavisafc/cloudnative-requestresilience-posts:0.0.1