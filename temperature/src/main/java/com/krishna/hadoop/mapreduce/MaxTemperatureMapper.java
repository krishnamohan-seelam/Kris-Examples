package com.krishna.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MaxTemperatureMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		
		String lineData = value.toString();
		String year = lineData.substring(15,19);
		String tempStr=lineData.substring(87,92);
		if(!ignoreTemperature(tempStr))
		{
		int airTemp =Integer.parseInt(tempStr);
		context.write(new Text(year), new IntWritable(airTemp));
		}
		
	}
	private boolean ignoreTemperature(String inputTemp)
	{
		
		return inputTemp.equals("+9999");
		
		
	}
	

}
