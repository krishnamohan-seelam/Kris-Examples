package com.krishna.hadoop.mapreduce;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



/**
 * @author KrishnaMohan
 * StockRecordMapper class - uses stock record parser to map date(YYYY-MM),volume,adjusted closing price
 * 
 *
 */
public class StockRecordMapper extends Mapper<LongWritable, Text, Text, VolumePricePair> {

	private StockRecordParser srParser = new StockRecordParser();
	@Override
	protected void map(LongWritable key, Text textRecord,
			Context context) throws IOException, InterruptedException {

		if(!srParser.isHeader(textRecord))
		{
			srParser.parse(textRecord);

			Text keyText = new Text(srParser.getYYYYMM());
			VolumePricePair vpPair = new VolumePricePair(srParser.getVolume(),srParser.getAdjClosePrice());
			context.write(keyText, vpPair);

		}

	}


}
