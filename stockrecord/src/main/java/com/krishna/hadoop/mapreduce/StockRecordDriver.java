package com.krishna.hadoop.mapreduce;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
/*
 * create jar file ---stockrecord.jar
 * upload input data to HDFS -/user/cloudera/input-data/stock-data
 * hadoop jar <jarfile>  <driver-program> <input path> <output path>  
 * hadoop jar stockrecord.jar com.krishna.hadoop.mapreduce.StockRecordDriver /user/cloudera/input-data/stock-data /user/cloudera/output/stockrecord
 * 
 * Sample input - Apple.csv 
 * Sample output- part-r-00000
 * 
 * test cases --- src/test/java
 */

public class StockRecordDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		if(args.length !=2)
		{
			 System.err.printf("Usage: %s [generic options] <input> <output>\n",
			          getClass().getSimpleName());
			 ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		
		Job srJob = Job.getInstance(getConf(), "Stock Record Average");
		srJob.setJarByClass(getClass());
		FileInputFormat.addInputPath(srJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(srJob,new Path(args[1]));
		
	
		srJob.setMapperClass(StockRecordMapper.class);
		srJob.setMapOutputKeyClass(Text.class);
		srJob.setMapOutputValueClass(VolumePricePair.class);
		
	
		
		srJob.setReducerClass(StockRecordReducer.class);
		
		srJob.setOutputKeyClass(Text.class);
		srJob.setOutputValueClass(DoubleWritable.class);
		
		
		return srJob.waitForCompletion(true) ?0:-1;
	}
	public static void main(String args[]) throws Exception
	{
		int exitCode =ToolRunner.run(new StockRecordDriver(), args);
		System.exit(exitCode);
	}

		

}
