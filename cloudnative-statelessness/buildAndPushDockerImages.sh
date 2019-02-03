docker build --build-arg \
jar_file=cloudnative-connections/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-statelessness-connections .
#
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-statelessness-posts .
#
docker push cdavisafc/cloudnative-statelessness-connections
docker push cdavisafc/cloudnative-statelessness-posts
# Two different versions of connectionsposts
#docker build --build-arg \
#jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
#-t cdavisafc/cloudnative-statelessness-connectionsposts-stateful .
#
#docker push cdavisafc/cloudnative-statelessness-connectionsposts-stateful
#
docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-statelessness-connectionsposts-stateless .
#
docker push cdavisafc/cloudnative-statelessness-connectionsposts-stateless
