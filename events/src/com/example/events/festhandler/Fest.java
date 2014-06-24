package com.example.events.festhandler;

import android.os.Parcel;
import android.os.Parcelable;

public class Fest implements Parcelable{
	
private int id;
private String festname;

	public Fest( )
	{
	
	}
	
	public Fest(Parcel in)
	{
		id = in.readInt();
		festname = in.readString();
		
	}
	
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		return festname;
	}
	
	public void setId(int i)
	{
		id = i;
	}
	
	public void setName(String name)
	{
		festname  = name;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(festname);
	}
	
	   public static final Parcelable.Creator<Fest> CREATOR = new Parcelable.Creator<Fest>() {
	        public Fest createFromParcel(Parcel in) {
	            return new Fest(in);
	        }

	        public Fest[] newArray(int size) {
	            return new Fest[size];
	        }
	    };

}
