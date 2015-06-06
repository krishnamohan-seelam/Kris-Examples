package com.krishna.hadoop.mapreduce;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MaxTemperatureDriver extends Configured implements Tool {

	
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
		
		
		Job mtJob = Job.getInstance(getConf(), "Max Temperature");
		mtJob.setJarByClass(getClass());
		FileInputFormat.addInputPath(mtJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(mtJob,new Path(args[1]));
		
		mtJob.setMapperClass(MaxTemperatureMapper.class);
		mtJob.setCombinerClass(MaxTemperatureReducer.class);
		mtJob.setReducerClass(MaxTemperatureReducer.class);
		
		mtJob.setOutputKeyClass(Text.class);
		mtJob.setOutputValueClass(IntWritable.class);
		
		return mtJob.waitForCompletion(true) ?0:-1;
	
	}
	public static void main(String args[]) throws Exception
	{
		int exitCode =ToolRunner.run(new MaxTemperatureDriver(), args);
		System.exit(exitCode);
	}

}
