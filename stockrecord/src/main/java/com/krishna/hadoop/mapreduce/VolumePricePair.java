package com.krishna.hadoop.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;

public class VolumePricePair implements WritableComparable<VolumePricePair> {

	 private LongWritable volume;
	 private DoubleWritable price;
	 
	 
	 public VolumePricePair()
	 {
		 set(new LongWritable(0),new DoubleWritable(0));
	 }
	 public VolumePricePair(long dVol,double dPrice)
	 {
		 set(new LongWritable(dVol),new DoubleWritable(dPrice));
	 }
	 public LongWritable getVolume()
	 {
		 
		 return volume;
	 }
	 
	 public DoubleWritable getPrice()
	 {
		 return price;
	 }
	 public void set(LongWritable volume,DoubleWritable price)
	 {
		 this.volume =volume;
		 this.price =price;
	 }
	 
	@Override
	public void readFields(DataInput din) throws IOException {
		// TODO Auto-generated method stub
         volume.readFields(din);
         price.readFields(din);
         
	}

	@Override
	public void write(DataOutput dout) throws IOException {
		// TODO Auto-generated method stub
            volume.write(dout);
            price.write(dout);
	}

	@Override
	public int compareTo(VolumePricePair o) {
		// TODO Auto-generated method stub
		 int comparison  = this.volume.compareTo(o.volume);
		 return (comparison !=0 )? comparison :this.price.compareTo(o.price);
			 
		
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VolumePricePair other = (VolumePricePair) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "[volume=" + volume + ", price=" + price + "]";
	}

	
}
