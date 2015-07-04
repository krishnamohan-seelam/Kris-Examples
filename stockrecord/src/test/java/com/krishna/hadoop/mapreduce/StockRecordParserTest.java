package com.krishna.hadoop.mapreduce;


import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Test;
public class StockRecordParserTest {
	
	StockRecordParser srp =new StockRecordParser();
	
	@Test
	public void testParser()
	{
	         Text testRecord = new Text("08-02-2013,474,478.81,468.25,474.98,22597100,474.98");
	         srp.parse(testRecord);
	         assertEquals( "2013-02" ,srp.getYYYYMM());
	         assertEquals(22597100 ,srp.getVolume());
	         assertEquals(474.98 ,srp.getAdjClosePrice(),0.01);
	}
}
