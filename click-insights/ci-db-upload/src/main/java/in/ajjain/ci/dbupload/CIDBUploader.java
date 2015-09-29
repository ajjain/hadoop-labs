package in.ajjain.ci.dbupload;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import in.ajjain.ci.common.hbase.HBaseTableCFNames;
import in.ajjain.ci.dbupload.mr.EventSummaryMR.EventSummaryMapper;
import in.ajjain.ci.dbupload.mr.EventSummaryMR.EventSummaryReducer;

/**
 * The Class CIDBUploader.
 * 
 * @author ajjain
 */
public class CIDBUploader {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		Configuration  config  = HBaseConfiguration.create();
		config.clear();
        config.set("hbase.zookeeper.quorum", "127.0.0.1");
        config.set("hbase.zookeeper.property.clientPort","2181");
        config.set("hbase.master", "127.0.0.1:16000");
		
        Job job = Job.getInstance(config, "Click Insights DB Uploader");
		job.setJarByClass(CIDBUploader.class);
		
		job.setMapperClass(EventSummaryMapper.class);
		
		TableMapReduceUtil.initTableReducerJob(
				HBaseTableCFNames.TAB_EVENT_SUMMARY,
				EventSummaryReducer.class,
				job);
		
		job.setNumReduceTasks(4);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
