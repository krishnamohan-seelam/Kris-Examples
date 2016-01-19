package airlines;

import static org.junit.Assert.*;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class AirlineParserTest {

	
	 protected Text testRow =null ;
	 protected String testYear ="1987";
	 protected void setUp()
	 {
		 testRow = new Text("1987,10,23,5,731,730,902,849,PS,1451,NA,91,79,NA,13,1,SAN,SFO,447,NA,NA,0,NA,0,NA,NA,NA,NA,NA");
		 
	 }
	@Test
	public void testParseRow() {
		
		if(testRow == null)
			testRow=new Text("1987,10,23,5,731,730,902,849,PS,1451,NA,91,79,NA,13,-1,SAN,SFO,447,NA,NA,0,NA,0,NA,NA,NA,NA,NA");
		
		AbstractParserTemplate  abp   = new AirlineParser();
		abp.parseData(testRow, ",");
		
		String array[] =abp.getRowData();
		boolean header =abp.isHeader(array);
		assertEquals(header ,false);
		
		AirlineData aldRef = AirlineData.getAirlineData(header, array);
		 //System.out.println(abp.rowParsed);
		assertEquals(aldRef.getYear(),"1987");
		assertEquals(aldRef.getDepdelay(),"-1");
		assertEquals(aldRef.getLateaircraftdelay(),"NA");
		assertEquals(aldRef.getDateStr(),"10/23/1987");
		assertEquals(aldRef.getDeptime(),"731");
		//fail("Not yet implemented");
	}

	
	@Test
	public void testHeaderRow() {
		
		
			testRow=new Text("Year,Month,DayofMonth,DayOfWeek,DepTime,CRSDepTime,ArrTime,CRSArrTime,UniqueCarrier,FlightNum,TailNum,ActualElapsedTime,CRSElapsedTime,AirTime,ArrDelay,DepDelay,Origin,Dest,Distance,TaxiIn,TaxiOut,Cancelled,CancellationCode,Diverted,CarrierDelay,WeatherDelay,NASDelay,SecurityDelay,LateAircraftDelay");
		AbstractParserTemplate  abp = new AirlineParser();
		abp.parseData(testRow, ",");
		String array[] =abp.getRowData();
		boolean header =abp.isHeader(array);
		assertEquals(header ,true);
		AirlineData aldRef = AirlineData.getAirlineData(header, array);
		 //System.out.println(abp.rowParsed);
		assertEquals(aldRef.getYear(),"Year");
		assertEquals(aldRef.getDayofmonth(),"DayofMonth");
		assertEquals(aldRef.getLateaircraftdelay(),"LateAircraftDelay");
		
		//fail("Not yet implemented");
	}
}
