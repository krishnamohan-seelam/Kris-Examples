package com.krishna.hadoop;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



/**
 * @author KrishnaMohan
 * The below mapper creates top 5 list  and passes the output to the TopNReducer
 * single reducer key NullWritable ensures that all mappers output will be used by single reducer.
 *
 */
public class TopNMapper extends Mapper<Text, DoubleWritable, NullWritable, Text> {
	
	private int nlist = 5;
	
	private SortedMap<Double, String> sortMap =  new TreeMap<Double,String>();
	@Override
	protected void setup(Context context) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub
		this.nlist = context.getConfiguration().getInt("nlist", nlist);
	}
	@Override
	protected void map(Text key, DoubleWritable value,
			Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String yyyyMM = key.toString();
		double averagePrice = value.get();
		String compositeValue = yyyyMM +"\t" + averagePrice;
		sortMap.put(new Double(averagePrice), compositeValue);
		if (sortMap.size() > nlist)  {sortMap.remove(sortMap.firstKey());}
		
	}
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		for(String eachValue :sortMap.values())
		{
			context.write(NullWritable.get(),new Text(eachValue));
		}
	}
	
	
	

}
