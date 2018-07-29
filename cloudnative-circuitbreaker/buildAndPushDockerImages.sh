docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-connectionposts .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-connections .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-posts .
#
docker push cdavisafc/cloudnative-requestresilience-connectionposts
docker push cdavisafc/cloudnative-requestresilience-connections
docker push cdavisafc/cloudnative-requestresilience-posts