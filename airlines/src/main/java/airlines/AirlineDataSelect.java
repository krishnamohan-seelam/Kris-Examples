package airlines;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/***
 * hadoop jar airlines.jar airlines.AirlineDataSelect -libjars ${LIBJARS}  hdfs:///user/cloudera/airlines /user/cloudera/airlines_out
 *****************************************  output format*******************************************************************************************************************
 * Flight_date,Day_of_the_week,Departure_time_,Arrival_time,Origin,Destination,Total_flight_distance,Actual_flight_time,Scheduled_flight_time,Departure_delay,Arrival_delay*
 *****************************************  output format******************************************************************************************************************* 
 * @author krishna
 * This below mapreduce program  is an example of map only job. 
 * This job parses flight data extracted from http://stat-computing.org/dataexpo/2009/the-data.html 
 * Raw data obtained from source has 29 fields , the below program  extracts  only 11 fields and writes to output file in HDFS
 */
public class AirlineDataSelect extends Configured implements Tool {

	enum ADSCounter { HEADER ,INVALID }
	
	public static class AirlineDataMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
		 
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			
			
			AbstractParserTemplate  abp   = new AirlineParser();
			abp.parseData(value, ",");
			String rowData[] =abp.getRowData();
			boolean headerFlag = abp.isHeader(rowData);
			AirlineData aldRef = AirlineData.getAirlineData(headerFlag, rowData);
			if (!headerFlag)
			{
				writeData( aldRef,"," ,context);
			}
			
			else
			{
				context.getCounter(ADSCounter.HEADER).increment(1);
			}
		}

		private void writeData(AirlineData aldRef,String separator ,Mapper<LongWritable, Text, NullWritable, Text>.Context context) throws IOException, InterruptedException
		{
		          
			    StringBuilder strBuilder =  new StringBuilder();
			    Text value  = new Text();
			    if(aldRef.rowData!=null && aldRef.rowData.length >0)
			    {
			    	strBuilder.append(aldRef.getDateStr().trim());
			    	strBuilder.append(separator).append(aldRef.getDayofweek());
			    	strBuilder.append(separator).append(aldRef.getDeptime());
			    	strBuilder.append(separator).append(aldRef.getArrtime());
			    	strBuilder.append(separator).append(aldRef.getOrigin());
			    	strBuilder.append(separator).append(aldRef.getDest());
			    	strBuilder.append(separator).append(aldRef.getDistance());
			    	strBuilder.append(separator).append(aldRef.getActualelapsedtime());
			    	strBuilder.append(separator).append(aldRef.getCrselapsedtime());
			    	strBuilder.append(separator).append(aldRef.getDepdelay());
			    	strBuilder.append(separator).append(aldRef.getArrdelay());
			    	value.set(strBuilder.toString());
			    	context.write(NullWritable.get(), value);
			    }
			    else
			    {
			    	context.getCounter(ADSCounter.INVALID).increment(1);
			    }
			
		}
		
		
	}
	
	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length!=2)
		{
			 System.err.printf("Usage: %s [generic options] <input> <output>\n",
			          getClass().getSimpleName());
			 ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		Job  adsJOB = Job.getInstance(getConf(), "airline data select");
		adsJOB.setJarByClass(getClass());
		
		
		FileInputFormat.addInputPath(adsJOB, new Path(args[0]));
		FileOutputFormat.setOutputPath(adsJOB, new Path(args[1]));
		
		adsJOB.setOutputFormatClass(TextOutputFormat.class);
		adsJOB.setMapperClass(AirlineDataMapper.class);
		adsJOB.setOutputKeyClass(NullWritable.class);
		adsJOB.setOutputValueClass(Text.class);
		adsJOB.setNumReduceTasks(0); 
		return adsJOB.waitForCompletion(true) ?0 :-1;
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 int exitCode = ToolRunner.run(new AirlineDataSelect(), args);
		 System.exit(exitCode);

	}

}
