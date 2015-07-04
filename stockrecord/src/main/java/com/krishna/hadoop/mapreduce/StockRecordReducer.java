package com.krishna.hadoop.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author KrishnaMohan
 * 
 */
public class StockRecordReducer extends
		Reducer<Text , VolumePricePair,Text, DoubleWritable> {

	@Override
	protected void reduce(Text key, Iterable<VolumePricePair> vpPairValues,
			Context context)
			throws IOException, InterruptedException {
		
		 double sigmaVolumePrice= 0;
	     long sigmaVolume=0;
		for(VolumePricePair vpPair :vpPairValues)
		{
			 
			sigmaVolume =  sigmaVolume + vpPair.getVolume().get() ;
			sigmaVolumePrice =sigmaVolumePrice + vpPair.getVolume().get() * vpPair.getPrice().get();
		}
		 double average = sigmaVolumePrice/sigmaVolume;
		 double roundAverage = Math.round(average *100)/100.0d;
		context.write(key,new DoubleWritable(roundAverage));
	}
	

}
