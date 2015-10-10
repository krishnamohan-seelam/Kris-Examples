package wikipv;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import com.krishna.hadoop.TextTime;
import com.krishna.hadoop.TimeInt;
import com.krishna.hadoop.TimeText;
import com.krishna.hadoop.WikiPageViewParser;

public class WikiPageViewMapper extends Mapper<LongWritable, Text, TimeText, IntWritable> {
	
	
     private WikiPageViewParser wpvParser =  new WikiPageViewParser();
     private final TimeText ttKey = new TimeText();
     private final IntWritable iwValue = new IntWritable();
     enum WPCounter { HEADER }
	protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException
	{
		wpvParser.parse(value);
		String siteName =wpvParser.getSiteType();
		long timeStamp =wpvParser.getLongTime();
		int requestCount =wpvParser.getRequestCount();
		if(wpvParser.isValid(siteName))
		{
			
			ttKey.set(timeStamp, siteName);
			iwValue.set(requestCount);
			context.write(ttKey, iwValue);
		}
		else
		{
			context.getCounter(WPCounter.HEADER).increment(1);
		}
	}
}
