package in.ajjain.ci.dbupload.mr;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import in.ajjain.ci.common.hbase.HBaseTableCFNames;

public class EventSummaryMR {

	private static final String fields = "hour,page,cnt_page_views";

	public static class EventSummaryMapper extends Mapper<Object, Text, Text, Text>{
		/** The out key text. */
		private Text outKeyText = new Text();

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String[] values = value.toString().split(",");
			outKeyText.set(values[0]);
			context.write(outKeyText, value);
		}
	}
	
	public static class EventSummaryReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {

		@Override
		protected void reduce(Text key, Iterable<Text> values,
				Reducer<Text, Text, ImmutableBytesWritable, Mutation>.Context context)
						throws IOException, InterruptedException {
			Put put = new Put(Bytes.toBytes(key.toString()));
			for(Text value : values){
				String[] fieldvalues = value.toString().split(",");
				put.add(Bytes.toBytes(HBaseTableCFNames.CF_PAGE_VIEW), Bytes.toBytes(fieldvalues[1]), Bytes.toBytes(fieldvalues[2]));
			}
			context.write(null, put);
		}
		
	}
}
