package com.krishna.hadoop.mapreduce;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import org.apache.hadoop.io.Text;

/**
 * @author-- KrishnaMohan
 * StockRecordParser --- parses records  as  given in the below format(csv file)
 * -----------------------------------------------------------------------------
 * Date,Open,High,Low,Close,Volume,Adj_Close
 * 08-02-2013,474,478.81,468.25,474.98,22597100,474.98
 * -----------------------------------------------------------------------------
 * Date is stored as YYYY-MM String,Volume in long and others in double
 * List of Validations (minimal validations)
 * invalid date will return null, isNumeric validation included before converting to double
 * isHeader validation will be used by the mapper program
 */

public class StockRecordParser {

	private String YYYYMM;
	private long  volume;
	private double openPrice, highPrice,lowPrice ,closePrice,adjClosePrice;
	private static final String delimiter= ","; 
	private SimpleDateFormat sdfDDMMYYY =new SimpleDateFormat("dd-MM-yyyy"); 
	private SimpleDateFormat sdfYYYYMM =new SimpleDateFormat("yyyy-MM"); 
	public StockRecordParser()
	{

	}
	public void parse(Text record)
	{
		parse(record.toString());

	}
	private void parse(String recordString) {
		// TODO Auto-generated method stub
		// StringTokenizer csvTokenizer = new StringTokenizer(string , delimiter);
		String stockArray [] = recordString.split(delimiter);
		if (stockArray.length == 7 )
		{
			YYYYMM =getDateStr(stockArray[0]);
			openPrice = isNumeric(stockArray[1]) ?Double.parseDouble(stockArray[1]) :0;
			highPrice = isNumeric(stockArray[2]) ?Double.parseDouble(stockArray[2]) :0;
			lowPrice =  isNumeric(stockArray[3]) ?Double.parseDouble(stockArray[3]) :0;
			closePrice= isNumeric(stockArray[4]) ?Double.parseDouble(stockArray[4]) :0;
			adjClosePrice =isNumeric(stockArray[6]) ?Double.parseDouble(stockArray[6]) :0;
			volume =isNumeric(stockArray[5]) ?Long.parseLong(stockArray[5]) :0;
		}
	}
	public boolean isHeader(Text recordValue) {

		// TODO Auto-generated method stub
		System.out.println(recordValue.toString().substring(0, 4));
		return recordValue.toString().substring(0, 4).equalsIgnoreCase("DATE");

	}

	private String getDateStr(String baseDate) {
		// TODO Auto-generated method stub
		try {

			return sdfYYYYMM.format((sdfDDMMYYY.parse(baseDate)));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public  boolean isNumeric(String inputData) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(inputData, pos);
		return inputData.length() == pos.getIndex();
	}

	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}


	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getAdjClosePrice() {
		return adjClosePrice;
	}
	public void setAdjClosePrice(double adjClosePrice) {
		this.adjClosePrice = adjClosePrice;
	}
	public String getYYYYMM() {
		return YYYYMM;
	}
	public void setYYYYMM(String yYYYMM) {
		YYYYMM = yYYYMM;
	}

}
