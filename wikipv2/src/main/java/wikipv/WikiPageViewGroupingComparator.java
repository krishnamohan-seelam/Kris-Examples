package wikipv;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import com.krishna.hadoop.TextTime;
import com.krishna.hadoop.TimeText;

public class WikiPageViewGroupingComparator extends WritableComparator {

	protected WikiPageViewGroupingComparator()
	{
		super(TimeText.class,true);
		
	}
	
	@Override
	public int compare(WritableComparable thisWC,WritableComparable otherWC )
	{
		   TimeText thisTT = (TimeText) thisWC;
		   TimeText otherTT = (TimeText) otherWC;

	    	if(thisTT.getTime() ==  otherTT.getTime()) 
	    	{ 
	    		return 0;
	    	}
	    	else {
	    		return thisTT.getTime() < otherTT.getTime() ? -1 : 1;
	    	}
		   
		    		
		   
		
		
	}
	
}
