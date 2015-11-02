package wikipv;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import com.krishna.hadoop.TimeText;

public class WikiPageViewComparator extends WritableComparator {
	
	protected WikiPageViewComparator()
	{
		super(TimeText.class,true);
	}
	
	@Override
	public int compare(WritableComparable thisWC,WritableComparable otherWC )
	{
		   TimeText thisTT = (TimeText) thisWC;
		   TimeText otherTT = (TimeText) otherWC;
		   int comparsion = thisTT.getstrVal().compareTo(otherTT.getstrVal()) ;
		    
		  
		    	if(thisTT.getTime() ==  otherTT.getTime()) 
		    	{ 
		    		return comparsion;
		    	}
		    	else {
		    		return thisTT.getTime() < otherTT.getTime() ? -1 : 1;
		    	}
		    	 
		    	
		   
		    
		    	
		   
		    		
	
		
		
	}

}
