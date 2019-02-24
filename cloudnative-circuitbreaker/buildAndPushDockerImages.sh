docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-posts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-circuitbreaker-posts:0.0.2 .
docker build --build-arg \
jar_file=cloudnative-posts/target/cloudnative-connections-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-circuitbreaker-connections .
#
docker push cdavisafc/cloudnative-circuitbreaker-posts:0.0.2
docker push cdavisafc/cloudnative-circuitbreaker-connections