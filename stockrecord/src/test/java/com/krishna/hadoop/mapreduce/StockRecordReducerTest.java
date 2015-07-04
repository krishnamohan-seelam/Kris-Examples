package com.krishna.hadoop.mapreduce;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

public class StockRecordReducerTest {

	
	 /*  output without rounding to two decimals -471.41837012439095*/
	@Test
	public void testReducer()throws IOException,InterruptedException
	{
		Text textKey = new Text("2013-02");
		VolumePricePair vpPair1 = new VolumePricePair(22597100 ,474.98);
		VolumePricePair vpPair2 = new VolumePricePair(25163600 ,468.22);
		List<VolumePricePair> inputValues = Arrays.asList(vpPair1,vpPair2);
		new ReduceDriver<Text,VolumePricePair,Text,DoubleWritable> ()
		.withReducer(new StockRecordReducer())
		.withInput(textKey,inputValues)
		.withOutput(textKey, new DoubleWritable(471.42))
		.runTest();
		}
	
}
