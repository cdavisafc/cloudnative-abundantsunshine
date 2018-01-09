docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.2-SNAPSHOT.jar \
-t cdavisafc/cloudnative-appconfig-connectionposts .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.2-SNAPSHOT.jar \
-t cdavisafc/cloudnative-appconfig-connections .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.2-SNAPSHOT.jar \
-t cdavisafc/cloudnative-appconfig-posts .
#
docker tag cdavisafc/cloudnative-appconfig-connectionposts cdavisafc/cloudnative-appconfig-connectionposts:0.0.2
docker tag cdavisafc/cloudnative-appconfig-connections cdavisafc/cloudnative-appconfig-connections:0.0.2
docker tag cdavisafc/cloudnative-appconfig-posts cdavisafc/cloudnative-appconfig-posts:0.0.2
#
docker push cdavisafc/cloudnative-appconfig-connectionposts:0.0.2
docker push cdavisafc/cloudnative-appconfig-connections:0.0.2
docker push cdavisafc/cloudnative-appconfig-posts:0.0.2