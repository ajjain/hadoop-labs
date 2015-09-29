cd $CI_HOME
mvn install -DskipTests
# $1 represents the date field in MM-DD-YYYY format
java -jar ./ci-client/target/ci-client-1.0.jar $1 ~/data 1000000
hdfs dfs -put ~/data/$1.data /user/ajjain/input
hdfs dfs -ls /user/ajjain/input

