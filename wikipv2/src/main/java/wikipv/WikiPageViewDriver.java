package wikipv;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.krishna.hadoop.TextPair;
import com.krishna.hadoop.TextTime;
import com.krishna.hadoop.TimeInt;
import com.krishna.hadoop.TimeText;
import com.krishna.hadoop.TimeVolume;

/*
hadoop jar wikipv2.jar wikipv.WikiPageViewDriver -libjars ${LIBJARS}  hdfs:///user/cloudera/wiki_input/pageviews_by_second.tsv /user/cloudera/wiki_output

*/
public class WikiPageViewDriver extends Configured implements Tool {

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
		
		Job seJob = Job.getInstance(getConf(), "WikiPage");
		seJob.setJarByClass(getClass());
		
		
		FileInputFormat.addInputPath(seJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(seJob, new Path(args[1]));
		
		seJob.setMapperClass(WikiPageViewMapper.class);
		seJob.setMapOutputKeyClass(TimeText.class);
		seJob.setMapOutputValueClass(IntWritable.class);
		seJob.setReducerClass(WikiPageViewReducer.class);
		seJob.setOutputKeyClass(TextPair.class);
		seJob.setOutputValueClass(LongWritable.class);
		
		
		
		return seJob.waitForCompletion(true) ?0 :-1;
	}

	public static void main(String args[]) throws Exception
	{
		 int exitCode = ToolRunner.run(new WikiPageViewDriver(), args);
		 System.exit(exitCode);
	}
	
}
