docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.3-SNAPSHOT.jar \
-t cdavisafc/cloudnative-applifecycle-connectionposts .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.3-SNAPSHOT.jar \
-t cdavisafc/cloudnative-applifecycle-connections .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.3-SNAPSHOT.jar \
-t cdavisafc/cloudnative-applifecycle-posts .
#
docker tag cdavisafc/cloudnative-applifecycle-connectionposts cdavisafc/cloudnative-applifecycle-connectionposts:0.0.3
docker tag cdavisafc/cloudnative-applifecycle-connections cdavisafc/cloudnative-applifecycle-connections:0.0.3
docker tag cdavisafc/cloudnative-applifecycle-posts cdavisafc/cloudnative-applifecycle-posts:0.0.3
#
docker push cdavisafc/cloudnative-applifecycle-connectionposts:0.0.3
docker push cdavisafc/cloudnative-applifecycle-connections:0.0.3
docker push cdavisafc/cloudnative-applifecycle-posts:0.0.3