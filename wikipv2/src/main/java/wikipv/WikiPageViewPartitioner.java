package wikipv;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

import com.krishna.hadoop.TextTime;
import com.krishna.hadoop.TimeInt;
import com.krishna.hadoop.TimeText;





public class WikiPageViewPartitioner  extends Partitioner<TimeText, IntWritable>  {

	@Override
	public int getPartition(TimeText key, IntWritable value, int numberOfPartitions) {
		// TODO Auto-generated method stub
		return Math.abs( (int )(  (key.getTime() ^ key.getTime() >>> 32)  % numberOfPartitions  )) ;
	}

	 private long getHashCode(TextTime key) {
		// TODO Auto-generated method stub
		 
		return getHashCode(key.getstrVal());
	}

	static long getHashCode(String strValue)
	   {
		   long hashValue = 1125899906842597L; // prime
		   int length = strValue.length();
		   for (int offset =0 ; offset<length;offset++)
		   {
			   hashValue = 31*hashValue + strValue.charAt(offset);
		   }
		   
		   return hashValue;
	   }
	
}
