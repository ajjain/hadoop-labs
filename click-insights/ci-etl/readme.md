## [Click Insights ETL] Extract Transform Load Process
This module extracts data from filesystem and transforms JSON data into flat file and loads the data partitioned by timestamp.  

### MapReduce Job
Map Reduce job transforms JSON events into comma separated values. Also it splits events based on event date hour and store it back on HDFS.

#### Sample command to run MR Job
```
hadoop jar <PATH_TO_CI_ETL_JAR> <HDFS_INPUT> <HDFS_OUTPUT>
```
* [PATH_TO_CI_ETL_JAR] Path of ETL jar
* [HDFS_INPUT] HDFS Input Path
* [HDFS_OUTPUT] HDFS Output Path
 
Example command:
```
hadoop jar ~/RnD/hadoop-labs/click-insights/ci-etl/target/ci-etl-1.0.jar /user/ajjain/input/ /user/ajjain/output
```

