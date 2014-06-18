package com.example.events;

public class Event {
	
	String name;
	String manager;
	String time;
	String venue;
	int eventId;
	int festId;
	
	
	Event()
	{
		// Empty constructor.
	}
	
	// setting functions.
	public void setName(String name)
	{
		this.name = name;
	}
	public void setManager(String mngr)
	{
		manager = mngr;
	}
	public void setTime(String t)
	{
		time = t;
	}
	public void setVenue(String p)
	{
		venue = p;
	}
	public void setEventId(int e)
	{
		eventId = e;
	}
	public void setFestId(int f)
	{
		festId = f;
	}
	
	// returning functions.
	public String getName()
	{
		return name;
	}
	public String getManager()
	{
		return manager;
	}
	public String getTime()
	{
		return time;
	}
	public String getVenue()
	{
		return venue;
	}
	public int getEventId()
	{
		return eventId;
	}
	public int getFestId()
	{
		return festId;
	}
	
}
