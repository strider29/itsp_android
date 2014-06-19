package com.example.events;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class EventDatabaseManager extends SQLiteOpenHelper
{
	  private static final String DATABASE_NAME = "events.db";
	  private static  int DATABASE_VERSION = 1;
	  
	  // table name.
	  private static final String TABLE_EVENTS = "events";
	  // column names.
	  private static final String KEY_ID = "id";
	  private static final String KEY_FESTID = "festid";
	  private static final String KEY_NAME = "title";
	  private static final String KEY_MANAGER = "manager";
	  private static final String KEY_TIME = "time";
	  private static final String KEY_VENUE = "venue";
	  
	  private static final int FESTID_INDEX = 1;
	  private static final int NAME_INDEX = 2;
	  private static final int MANAGER_INDEX = 3;
	  private static final int TIME_INDEX = 4;
	  private static final int VENUE_INDEX = 5;
	  
	public EventDatabaseManager(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		Log.d("database","creating db");
		String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS
				+ "( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FESTID + " INTEGER , "+
				  KEY_NAME + " TEXT , " + KEY_MANAGER + " TEXT , "+
				KEY_TIME + " TEXT , " + KEY_VENUE + " TEXT )";

			Log.d("create fn",CREATE_EVENTS_TABLE);
	 
		 database.execSQL(CREATE_EVENTS_TABLE);
		      
		    
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_EVENTS);
 
        // create fresh books table
        
        this.onCreate(db);	
	}
	
	/**
	 * Adding a new event.
	 */
	public void addEvent(Event e) 
	{
		try{
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
		
		
			/**
			 * checking to see if the event is already added or not.
			 */
			ArrayList<Event> list = getAllEvents();
			Event testEvent = new Event();
			Iterator<Event> in = list.iterator();
			while(in.hasNext())
			{
				testEvent = in.next();
				if(testEvent.getEventId() == e.getEventId() && testEvent.getFestId() == e.getFestId())
					{
					// TODO : showing error.
					return;	
					}
			}
			
		
			values.put(KEY_FESTID, e.getFestId());
			values.put(KEY_NAME, e.getName());
			values.put(KEY_MANAGER, e.getManager());
			values.put(KEY_TIME, e.getTime());
			values.put(KEY_VENUE, e.getVenue());
			Log.d("adding event" , "values row created.");
			// Inserting Row
			if(!db.isOpen())
			{
				Log.wtf("adding events." , "db is not open.");
			}
			db.insert(TABLE_EVENTS, null, values);
			Log.d("adding event" , "event row added.");
			//db.close(); // Closing database connection
			}catch(Exception ex)
			{
				Log.d("adding event" , "exception caught" +ex);
			}
	}
	
	/**
	 * Returning all Events.
	 */
	public ArrayList<Event> getAllEvents() 
	{
		ArrayList<Event> EventList = new ArrayList<Event>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_EVENTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Event e = new Event();
				e.setEventId(Integer.parseInt(cursor.getString(0)));
				e.setFestId(Integer.parseInt(cursor.getString(FESTID_INDEX)));
				e.setName(cursor.getString(NAME_INDEX));
				e.setManager(cursor.getString(MANAGER_INDEX));
				e.setTime(cursor.getString(TIME_INDEX));
				e.setVenue(cursor.getString(VENUE_INDEX));
				// Adding Event to list
				EventList.add(e);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		// return fest list
		return EventList;
	}
	
	// function to return all Events corresponding to a fest id.
	ArrayList<Event> eventsOfFestId(int fid)
	{
		ArrayList<Event> EventList = new ArrayList<Event> ();
		String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Event e = new Event();
				e.setEventId(Integer.parseInt(cursor.getString(0)));
				e.setFestId(Integer.parseInt(cursor.getString(FESTID_INDEX)));
				e.setName(cursor.getString(NAME_INDEX));
				e.setManager(cursor.getString(MANAGER_INDEX));
				e.setTime(cursor.getString(TIME_INDEX));
				e.setVenue(cursor.getString(VENUE_INDEX));
				// Adding Event to list
				if(e.getFestId() == fid)EventList.add(e);
			} while (cursor.moveToNext());
		}
		
		return EventList;
	}
	
}
