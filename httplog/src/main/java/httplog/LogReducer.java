package httplog;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.krishna.hadoop.LogWritable;

public class LogReducer extends Reducer<Text , LogWritable , Text, LongWritable> {
	
	private LongWritable reducerValue =new LongWritable();

	@Override
	protected void reduce(Text logKey, Iterable<LogWritable> logValues,
			   Context context) throws IOException, InterruptedException 
	{
		   long sum_of_Response=0L;
		   for(LogWritable logValue:logValues)
		   {
			   sum_of_Response += logValue.getResponse().get();
			   
		   }
		   reducerValue.set(sum_of_Response);
		   context.write(logKey, reducerValue);
		
		   
	}
	

}
