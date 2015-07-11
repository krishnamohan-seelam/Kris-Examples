package com.krishna.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;


/**
 * @author KrishnaMohan
 * The below reducer will consume all the top n lists from mappers and generate
 * the final top n list.
 *
 */
public class TopNReducer extends Reducer<NullWritable, Text, Text ,DoubleWritable > {

	 private int nlist = 5;
	 private SortedMap<Double,String> finalMap = new TreeMap<Double,String>();
	 private static Logger THE_LOGGER = Logger.getLogger(TopNReducer.class);
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
	         this.nlist= context.getConfiguration().getInt("nlist", 5);
	          
	}
	@Override
	protected void reduce(NullWritable key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		 for(Text eachValue:values)
		 {
			  String valueStr = eachValue.toString();
			  String valueArray[] = valueStr.split("\t");
			  String yyyyMM = valueArray[0];
			  double averagePrice = Double.parseDouble(valueArray[1]);
			  THE_LOGGER.info("yyyyMM" +yyyyMM);
			  THE_LOGGER.info("value" +averagePrice);
			  finalMap.put(new Double(averagePrice),yyyyMM);
			 
			 if(finalMap.size() > nlist) {
				 finalMap.remove(finalMap.firstKey()); }
			  
		 }
		 
		  List<Double> keys = new ArrayList<Double>(finalMap.keySet());
		  for (int itr=keys.size()-1;itr>=0;itr--)
		  {
			  context.write(new Text(finalMap.get(keys.get(itr))), new DoubleWritable(keys.get(itr)));
		  }
		 
	}
	
	
 
	
}
