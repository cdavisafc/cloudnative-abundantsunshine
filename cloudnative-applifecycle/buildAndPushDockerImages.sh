docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-applifecycle-connectionposts .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-applifecycle-connections .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-applifecycle-posts .
#
docker push cdavisafc/cloudnative-applifecycle-connectionposts
docker push cdavisafc/cloudnative-applifecycle-connections
docker push cdavisafc/cloudnative-applifecycle-posts