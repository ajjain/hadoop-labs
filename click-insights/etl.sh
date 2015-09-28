hadoop jar ./ci-etl/target/ci-etl-1.0.jar /user/ajjain/input/ /user/ajjain/output
hdfs dfs -cp /user/ajjain/output/* /user/ajjain/events/
