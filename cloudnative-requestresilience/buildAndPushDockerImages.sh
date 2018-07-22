docker build --build-arg \
jar_file=cloudnative-connectionposts/target/cloudnative-connectionposts-0.0.1-SNAPSHOT.jar \
-t cdavisafc/cloudnative-requestresilience-connectionposts:0.0.3 .
# only connections posts has changed for this step
#
docker push cdavisafc/cloudnative-requestresilience-connectionposts:0.0.3
