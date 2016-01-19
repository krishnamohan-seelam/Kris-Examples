package airlines;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class AirlineData {
	
	 
	 private boolean isHeader;
	 public static final String BLANKS = " ";
	 String[] rowData;
	 String[] headerData;
	
	 private String year, month, dayofmonth, dayofweek, deptime, crsdeptime, arrtime, crsarrtime, uniquecarrier, flightnum,
			        tailnum, actualelapsedtime, crselapsedtime, airtime, arrdelay, depdelay, origin, dest, distance, taxiin,
			        taxiout, cancelled, cancellationcode, diverted, carrierdelay, weatherdelay, nasdelay, securitydelay,
			        lateaircraftdelay;
	 private String dateStr,deptimeStr;
	 public  AirlineData(boolean isHeader, String[] rowData)
		{
			setAirLineData(isHeader,rowData);
			
		}
	 
	 public static AirlineData  getAirlineData( boolean isHeader ,String[] rowData)
	 {
		
		 return new AirlineData(isHeader,rowData);
	 }
	 

	 public  void setAirLineData(boolean isHeader, String[] rowData) {
		// TODO Auto-generated method stub
		this.isHeader= isHeader;
		this.rowData=rowData;
		year = getRowData(rowData[0]);
		month = getRowData( rowData[1]);
		dayofmonth =getRowData( rowData[2]); 
		dayofweek = getRowData( rowData[3]); 
		deptime = getRowData( rowData[4]); 
		crsdeptime = getRowData( rowData[5]); 
		arrtime = getRowData( rowData[6]);	
		crsarrtime =  getRowData( rowData[7]);
		uniquecarrier = (rowData[8] == null) ? BLANKS : rowData[8];
		flightnum = (rowData[9] == null) ? BLANKS : rowData[9];
		tailnum = (rowData[10] == null) ? BLANKS : rowData[10];
		actualelapsedtime = getRowData( rowData[11]);
		crselapsedtime = getRowData( rowData[12]);
		airtime = (rowData[13] == null) ? BLANKS : rowData[13];
		arrdelay = getRowData( rowData[14]);
		depdelay = getRowData( rowData[15]);
		origin = (rowData[16] == null) ? BLANKS : rowData[16];
		dest = (rowData[17] == null) ? BLANKS : rowData[17];
		distance = (rowData[18] == null) ? BLANKS : rowData[18];
		taxiin = (rowData[19] == null) ? BLANKS : rowData[19];
		taxiout = (rowData[20] == null) ? BLANKS : rowData[20];
		cancelled = (rowData[21] == null) ? BLANKS : rowData[21];
		cancellationcode = (rowData[22] == null) ? BLANKS : rowData[22];
		diverted = (rowData[23] == null) ? BLANKS : rowData[23];
		carrierdelay = (rowData[24] == null) ? BLANKS : rowData[24];
		weatherdelay = (rowData[25] == null) ? BLANKS : rowData[25];
		nasdelay = (rowData[26] == null) ? BLANKS : rowData[26];
		securitydelay = (rowData[27] == null) ? BLANKS : rowData[27];
		lateaircraftdelay = (rowData[28] == null) ? BLANKS : rowData[28];
    	dateStr=(this.isHeader==true ) ? BLANKS : String.format("%02d/%02d/%s", Integer.parseInt(month),Integer.parseInt(dayofmonth),year);
    	deptimeStr=(this.isHeader==true )? BLANKS :String.format("%s%s",dateStr,deptime);
    	
	}
	 public boolean isHeader() {
		return isHeader;
	}
	 
	 public void setHeaderData(String[] rowData)
	 {
		 if(this.isHeader &&  rowData!=null ) headerData =rowData;
	 }
	

	 
	public String[] getRowData() {
		return rowData;
	}

    
	 public String getYear()
	 {
		 return year ;
	 }
	
	public String getMonth() {
		return month;
	}

	public String getDayofmonth() {
		return dayofmonth;
	}

	public String getDayofweek() {
		return dayofweek;
	}

	public String getDeptime() {
		return deptime;
	}

	public String getCrsdeptime() {
		return crsdeptime;
	}

	public String getArrtime() {
		return arrtime;
	}

	public String getCrsarrtime() {
		return crsarrtime;
	}

	public String getUniquecarrier() {
		return uniquecarrier;
	}

	public String getFlightnum() {
		return flightnum;
	}

	public String getTailnum() {
		return tailnum;
	}

	public String getActualelapsedtime() {
		return actualelapsedtime;
	}

	public String getCrselapsedtime() {
		return crselapsedtime;
	}

	public String getAirtime() {
		return airtime;
	}

	public String getArrdelay() {
		return arrdelay;
	}

	public String getDepdelay() {
		return depdelay;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDest() {
		return dest;
	}

	public String getDistance() {
		return distance;
	}

	public String getTaxiin() {
		return taxiin;
	}

	public String getTaxiout() {
		return taxiout;
	}

	public String getCancelled() {
		return cancelled;
	}

	public String getCancellationcode() {
		return cancellationcode;
	}

	public String getDiverted() {
		return diverted;
	}

	public String getCarrierdelay() {
		return carrierdelay;
	}

	public String getWeatherdelay() {
		return weatherdelay;
	}

	public String getNasdelay() {
		return nasdelay;
	}

	public String getSecuritydelay() {
		return securitydelay;
	}

	public String getLateaircraftdelay() {
		return lateaircraftdelay;
	}

	public String getDateStr() {
		return dateStr;
	}

	public String getDeptimeStr() {
		return deptimeStr;
	}
	
	private String getRowData(String input)
	{
		
		if(this.isHeader)
		{
			return (input!=null && !input.isEmpty ()) ? input :BLANKS;
			
		}
		if (input!=null && !input.isEmpty ())  
		{
			  return  isNumeric(input) ? input: BLANKS;
		}
		return BLANKS;
			
	}
	
	public  boolean isNumeric(String inputData) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(inputData, pos);
		return inputData.length() == pos.getIndex();
	}
    
	 
	
	 
}
