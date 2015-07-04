package com.krishna.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import org.apache.hadoop.io.LongWritable;


import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;
public class StockRecordMapperTest {
	
	@Test
	public void testMapperHeaderRecord() throws IOException,InterruptedException
	{
		
		 Text  textValue = new Text("Date,Open,High,Low,Close,Volume,Adj Close");
		  new MapDriver<LongWritable,Text,Text,VolumePricePair>()
		  .withMapper(new StockRecordMapper())
		  .withInput(new LongWritable(0),textValue)
		  .runTest();
		
		}
	
	@Test
	public void testMapperRecord() throws IOException,InterruptedException
	{
		
		 Text  textValue = new Text("08-02-2013,474,478.81,468.25,474.98,22597100,474.98");
		  new MapDriver<LongWritable,Text,Text,VolumePricePair>()
		  .withMapper(new StockRecordMapper())
		  .withInput(new LongWritable(0),textValue)
		  .withOutput(new Text("2013-02") ,new VolumePricePair(22597100,474.98));
		
		}


}
