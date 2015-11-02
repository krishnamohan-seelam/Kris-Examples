package httplogv2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;

import com.krishna.hadoop.LogWritable;
import com.krishna.hadoop.HttpLogParser;
public class KLogMapper extends Mapper<LongWritable, LogWritable, Text, LogWritable> {
	
	 private Text mapKey = new Text();
	enum RecordTypeCounter{BAD}
	@Override
	protected void map(LongWritable key, LogWritable value, Mapper<LongWritable, LogWritable, Text, LogWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		  mapKey.set(value.getUserIP());
		  context.write(mapKey, value);
		  
		
	}
	
	

}
