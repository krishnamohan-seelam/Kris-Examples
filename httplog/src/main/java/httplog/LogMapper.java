package httplog;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;

import com.krishna.hadoop.LogWritable;
import com.krishna.hadoop.HttpLogParser;
public class LogMapper extends Mapper<LongWritable, Text, Text, LogWritable> {
	
	HttpLogParser hlParser  = new HttpLogParser();
	enum RecordTypeCounter{BAD}
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LogWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		  boolean validRecord = hlParser.parse(value);
		  LogWritable logValue = new LogWritable();
		  Text logKey = new Text();
		   if (validRecord)
		   {
			   logKey.set(hlParser.getUserIP());
			   logValue.setUserIP(new Text(hlParser.getUserIP()));
			   logValue.setRequest(new Text(hlParser.getRequest()));
			   logValue.setTimestamp(new LongWritable(hlParser.getTimestamp()));
			   logValue.setResponse(new IntWritable(hlParser.getResponsebytes()));
			   logValue.setStatus(new IntWritable(hlParser.getStatus()));
			   context.write(logKey, logValue);
		   }
		   else
		   {
			  context.getCounter(RecordTypeCounter.BAD).increment(1); 
		   }
		  
		
	}
	
	

}
