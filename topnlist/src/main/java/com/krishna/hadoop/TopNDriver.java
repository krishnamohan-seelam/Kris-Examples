package com.krishna.hadoop;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;


/**
 * @author KrishnaMohan
 * 
 * Each mapper will produce top nlist  and pass it to reducer. Single reducer will emit final top n list  using all the top n lists from mappers.
 * using single reducer key will ensure that all mappers will be consumed by same reducer.
 * hadoop jar topnlist.jar com.krishna.hadoop.TopNDriver 7 /user/cloudera/input-data/stock-data/stock-seqfile /user/cloudera/output/stockrecord/top7list
 * hadoop jar jarfile classname <input number> <input path> <output path>
 * input  file --- stock-seqfile (Sequence input format file)
 * output files---- output_top_5, output_top_7
 * 
 * Sequence file is created from output of stockrecord example
 */
public class TopNDriver extends Configured implements Tool {

	/**
	 * @param args
	 * @throws Exception
	 */
	
	private static Logger DRIVER_LOGGER = Logger.getLogger(TopNReducer.class);
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int exitCode = ToolRunner.run(new TopNDriver(), args);
		System.exit(exitCode);
	}

	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if (args.length != 3) {
			System.err.printf("Usage: %s [generic options] <input> <output>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;

		}
		int nlistVal = 5;
		try {
			nlistVal = Integer.parseInt(args[0]);
		} catch (NumberFormatException ne) {
			DRIVER_LOGGER.info(ne.getMessage());
			DRIVER_LOGGER.info("default nlist value will used.");
			nlistVal = 5;
		}
		Job tnJob = Job.getInstance(getConf(), "Top N List");
		tnJob.setJarByClass(getClass());
		tnJob.getConfiguration().setInt("nlist", nlistVal);
		tnJob.setInputFormatClass(SequenceFileInputFormat.class);
		
		tnJob.setMapperClass(TopNMapper.class);
		tnJob.setReducerClass(TopNReducer.class);
		tnJob.setNumReduceTasks(1);

		// map()'s output (K,V)
		tnJob.setMapOutputKeyClass(NullWritable.class);
		tnJob.setMapOutputValueClass(Text.class);

		// reduce()'s output (K,V)
		tnJob.setOutputKeyClass(Text.class);
		tnJob.setOutputValueClass(DoubleWritable.class);

		// args[1] = input directory
		// args[2] = output directory
		FileInputFormat.addInputPath(tnJob, new Path(args[1]));
		FileOutputFormat.setOutputPath(tnJob, new Path(args[2]));

		boolean status = tnJob.waitForCompletion(true);

		return status ? 0 : 1;
	}

}
