package in.ajjain.ci.dbupload;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import in.ajjain.ci.common.hbase.HBaseTableCFNames;
import in.ajjain.ci.dbupload.mr.EventSummaryMR.EventSummaryMapper;
import in.ajjain.ci.dbupload.mr.EventSummaryMR.EventSummaryReducer;
import in.ajjain.ci.dbupload.mr.GeoLoginSummaryMR.GeoLoginSummaryMapper;
import in.ajjain.ci.dbupload.mr.GeoLoginSummaryMR.GeoLoginSummaryReducer;
import in.ajjain.ci.dbupload.mr.PageViewByUsersMR;
import in.ajjain.ci.dbupload.mr.PageViewByUsersMR.PageViewByUsersMapper;
import in.ajjain.ci.dbupload.mr.PageViewByUsersMR.PageViewByUsersReducer;

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
		
		switch (args[0]) {
		case "event_summary":
			job.setMapperClass(EventSummaryMapper.class);
			TableMapReduceUtil.initTableReducerJob(
					HBaseTableCFNames.TAB_EVENT_SUMMARY,
					EventSummaryReducer.class,
					job);
			break;
		case "page_view_by_user":
			job.setMapperClass(PageViewByUsersMapper.class);
			TableMapReduceUtil.initTableReducerJob(
					HBaseTableCFNames.TAB_USERS_BY_PAGE,
					PageViewByUsersReducer.class,
					job);
			break;
		case "geo_login_summary":
			job.setMapperClass(GeoLoginSummaryMapper.class);
			TableMapReduceUtil.initTableReducerJob(
					HBaseTableCFNames.TAB_EVENT_SUMMARY,
					GeoLoginSummaryReducer.class,
					job);
			break;

		default:
			break;
		}
		
		job.setNumReduceTasks(4);
		FileInputFormat.addInputPath(job, new Path(args[1]));
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

