package com.krishna.hadoop.mapreduce;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;
 
/*
 * "0043011990999991950051518004+68750+023550FM-12+038299999V0203201N00261220001CN9999999N9-00111+99999999999"
 * "               Year                                                                    Temp              "
 */
public class MaxTemperatureTest {
	
	@Test
	public void testMapperRecord() throws IOException,InterruptedException
	{
		Text testValue = new Text("0043011990999991950051518004+68750+023550FM-12+038299999V0203201N00261220001CN9999999N9-00111+99999999999");
		new MapDriver<LongWritable,Text,Text,IntWritable>()
		.withMapper(new MaxTemperatureMapper())
		.withInput(new LongWritable(0),testValue)
		.withOutput(new Text("1950"), new IntWritable(-11)).runTest();
		
		
	}
	
	/*
	 * "0067011990999991950051507004+68750+023550FM-12+038299999V0203301N00671220001CN9999999N9+99991+99999999999"
	 * "               Year                                                                    Temp              "
	 */
	@Test
	public  void invalidTestMapperRecord() throws IOException,InterruptedException {
		Text testValue = new Text("0067011990999991950051507004+68750+023550FM-12+038299999V0203301N00671220001CN9999999N9+99991+99999999999");
		new MapDriver<LongWritable,Text,Text,IntWritable>()
		.withMapper(new MaxTemperatureMapper())
		.withInput(new LongWritable(0), testValue)
		.runTest();
		
	}
	
	@Test
	public void testMaxIntegerValues()throws IOException,InterruptedException
	{
		 Text textKey=new Text("1950");
		 List<IntWritable> inputValues=Arrays.asList(new IntWritable(5) ,new IntWritable(15));
		 new ReduceDriver<Text,IntWritable,Text,IntWritable>()
			.withReducer(new MaxTemperatureReducer())
			.withInput(textKey,inputValues)
			.withOutput(textKey, new IntWritable(15))
			.runTest();
		
	}

	
}
