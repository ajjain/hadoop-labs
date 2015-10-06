./bin/init.sh
./bin/generate_data.sh 09-30-2015
head -10 ~/data/09-30-2015.data
./bin/etl.sh
hive -f ./bin/analytics.hive 
./bin/upload_to_hbase.sh