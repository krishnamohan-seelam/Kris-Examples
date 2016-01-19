package airlines;

import org.apache.hadoop.io.Text;

public abstract class AbstractParserTemplate {
	
	      
	        
	      public final void parseData(Text rawData,String delimiter)
	      {
	    	  
	    	  parseData(rawData.toString(),delimiter) ;
	    	  
	      }

		public final void parseData(String rawString ,String delimiter)
		{
			      
				  parseRow(rawString,delimiter); 
				  setRowData();
			      setParsedFlag();
			     
			
		   }

		
		
		
		
		
		public  abstract  void  parseRow(String rawString, String delimiter) ;
		public  abstract  void   setRowData();
		
		public  abstract void setParsedFlag() ;
		
		
			// TODO Auto-generated method stub
		public abstract String[] getRowData();	
		
		public abstract boolean isHeader(String[] rowData);
		

}
