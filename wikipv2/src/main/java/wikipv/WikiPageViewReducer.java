package wikipv;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.krishna.hadoop.TextPair;

import com.krishna.hadoop.TimeText;

public class WikiPageViewReducer extends Reducer<TimeText, IntWritable, TextPair, LongWritable> {

	private final TextPair reduceKey  = new TextPair();
    private static final SimpleDateFormat sdfYYYYMMDD =new SimpleDateFormat("yyyy-MM-dd"); 
    private final LongWritable reduceValue  = new LongWritable();
    
	@Override
	protected void reduce(TimeText key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		
		String siteName = key.getstrVal();
		String yymmdd = getDateAsString(key.getTime());
		Long totalRequests=0L;
		for(IntWritable value :values)
		{
			totalRequests += value.get();
			
		}
		
		reduceKey.set(new Text(siteName), new Text(yymmdd));
		reduceValue.set(totalRequests);
		context.write(reduceKey, reduceValue);
	}
	
	public static String getDateAsString(long timestamp) {

		return sdfYYYYMMDD.format(timestamp);
		
	}

}
