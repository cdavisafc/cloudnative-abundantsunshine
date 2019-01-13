docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-troubleshooting-connectionposts .
#
docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-troubleshooting-connections .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-troubleshooting-posts .
#
docker push cdavisafc/cloudnative-troubleshooting-connectionposts
docker push cdavisafc/cloudnative-troubleshooting-connections
docker push cdavisafc/cloudnative-troubleshooting-posts