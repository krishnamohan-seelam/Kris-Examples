package airlines;

import org.apache.hadoop.io.Text;

public class AirlineParser extends AbstractParserTemplate {
	
	  private String[] rowData =null;
	  
	  private boolean rowParsed = false;
	  Text rawText =null;
	 
	
	
	public void  parseRow(String rawString,String delimiter)
	{     
		  if(!this.rowParsed)
		  {
		      this.rowData=rawString.toString().split(",");
		     
		  }
		 
	  }
	
	
	
	public  boolean isHeader(String[] rowData)
	{
		boolean header =true ;
		if(this.rowParsed)
		{
			 header = rowData[0].equalsIgnoreCase("Year");
			 
		}
		return header;
	}
	
	/*private void setParsedFlag(boolean rowParsed) {
		// TODO Auto-generated method stub
		 this.rowParsed = rowParsed ;
		
	}*/
	
	
	private void setRowData(String[] rowData) {
		// TODO Auto-generated method stub
		this.rowData=rowData;
		
	}
	
	

	public void setParsedFlag() {
		// TODO Auto-generated method stub
		this.rowParsed=true;
	} 
	
	public String[] getRowData() {
		// TODO Auto-generated method stub
		return this.rowData;
	}
	
	public void setRowData() {
		// TODO Auto-generated method stub
		setRowData(this.rowData);
	}



	



	



	
	
}
