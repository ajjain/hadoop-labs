package in.ajjain.ci.etl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickInsightsETL.
 * 
 * @author ajjain
 */
public class ClickInsightsETL {

	/**
	 * The Class CIMapper.
	 */
	public static class CIMapper extends Mapper<Object, Text, Text, Text>{

		/** The Constant gson. */
		private static final Gson gson = new Gson();

		/** The timestamp field format. */
		private static String TIMESTAMP_FIELD_FORMAT = "MM/dd/yyyy HH";	

		/** The field sequence. */
		private static String FIELD_SEQUENCE = "timestamp,url,method,browser,email,fname,lname,geoname,latitude,longitude,sessionid";

		/** The Constant TS_FIELD_NAME. */
		private static final String TS_FIELD_NAME = "timestamp";

		/** The Constant COMMA_DELIMITER. */
		private static final Object COMMA_DELIMITER = ",";

		/** The out key text. */
		private Text outKeyText = new Text();

		/** The out result text. */
		private Text outResultText = new Text();

		/* (non-Javadoc)
		 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
		 */
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String input = value.toString();
			Type type = new TypeToken<Map<String,String>>(){}.getType();
			Map<String, String> map = gson.fromJson(input, type);
			String ts = map.get(TS_FIELD_NAME);
			String outKey = ts.substring(0, TIMESTAMP_FIELD_FORMAT.length());
			String result = format(map);
			outKeyText.set(outKey); 
			outResultText.set(result);
			context.write(outKeyText, outResultText);
		}

		/**
		 * Format.
		 *
		 * @param map the map
		 * @return the string
		 */
		private String format(Map<String, String> map) {
			StringBuilder builder = new StringBuilder();
			String[] fields = FIELD_SEQUENCE.split(",");
			for(String field : fields){
				builder.append(map.get(field)).append(COMMA_DELIMITER);
			}
			builder.deleteCharAt(builder.length()-1);
			return builder.toString();
		}
	}

	/**
	 * The Class HourPartitioner.
	 */
	public static class HourPartitioner extends Partitioner<Text, Text>{

		/* (non-Javadoc)
		 * @see org.apache.hadoop.mapreduce.Partitioner#getPartition(java.lang.Object, java.lang.Object, int)
		 */
		@Override
		public int getPartition(Text key, Text value, int numReduceTasks) {
			if(numReduceTasks == 0){
				return 0;
			}
			else{
				String keyStr = key.toString();
				String hhStr = keyStr.substring(keyStr.length()-2, keyStr.length());
				int partition = Integer.parseInt(hhStr) % numReduceTasks;
				return partition;
			}
		}

	}

	/**
	 * The Class CIReducer.
	 */
	public static class CIReducer extends Reducer<Text, Text, NullWritable, Text> {
		/** The out text. */
		private Text outText = new Text();
		
		/* (non-Javadoc)
		 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN, java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
		 */
		public void reduce(Text key, Iterable<Text> values,	Context context) 
				throws IOException, InterruptedException {
			for(Text value : values){
				outText.set(value);
				context.write(NullWritable.get(), outText);
			}
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Click Insights ETL");
		job.setJarByClass(ClickInsightsETL.class);
		job.setMapperClass(CIMapper.class);
		job.setPartitionerClass(HourPartitioner.class);
		job.setReducerClass(CIReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(4);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
