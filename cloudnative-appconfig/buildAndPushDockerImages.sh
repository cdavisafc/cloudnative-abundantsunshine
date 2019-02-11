docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-appconfig-connectionposts:0.0.1 .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-appconfig-connections:0.0.1 .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-appconfig-posts:0.0.1 .
#
docker push cdavisafc/cloudnative-appconfig-connectionposts:0.0.1
docker push cdavisafc/cloudnative-appconfig-connections:0.0.1
docker push cdavisafc/cloudnative-appconfig-posts:0.0.1
