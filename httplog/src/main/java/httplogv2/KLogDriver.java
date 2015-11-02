package httplogv2;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.krishna.hadoop.LogWritable;

import httplog.LogMapper;
import httplog.LogReducer;

/*
hadoop jar logmr.jar httplog.LogDriver -libjars ${LIBJARS}  hdfs:///user/cloudera/http_log/access_log_Jul95 /user/cloudera/http_log_output

*/




public class KLogDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		if(args.length !=2)
		{
			 System.err.printf("Usage: %s [generic options] <input> <output>\n",
			          getClass().getSimpleName());
			 ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		Job ldJob = Job.getInstance(getConf(), "httplog");
		ldJob.setJarByClass(getClass());
		
		ldJob.setInputFormatClass(KLogInputFormat.class);
		FileInputFormat.addInputPath(ldJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(ldJob, new Path(args[1]));
		
		ldJob.setMapperClass(KLogMapper.class);
		ldJob.setMapOutputKeyClass(Text.class);
		ldJob.setMapOutputValueClass(LogWritable.class);
		ldJob.setReducerClass(LogReducer.class);
		ldJob.setOutputKeyClass(Text.class);
		ldJob.setOutputValueClass(LongWritable.class);
		
		
		
		return ldJob.waitForCompletion(true) ?0 :-1;
		
	}

	
	
	public static void main(String args[]) throws Exception
	{
		 int exitCode = ToolRunner.run(new KLogDriver(), args);
		 System.exit(exitCode);
	}
}
