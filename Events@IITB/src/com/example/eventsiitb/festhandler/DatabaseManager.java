package com.example.eventsiitb.festhandler;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseManager extends SQLiteOpenHelper
{
	  private static final String DATABASE_NAME = "fests.db";
	  private static final int DATABASE_VERSION = 1;
	  
	  // table name.
	  private static final String TABLE_FESTS = "fests";
	  // column names.
	  private static final String KEY_ID = "id";
	  private static final String KEY_NAME = "title";
	  
	  private static final int NAME_INDEX = 1;
	  
	public DatabaseManager(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		String CREATE_FESTS_TABLE = "CREATE TABLE " + TABLE_FESTS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT )";
	 
		 database.execSQL(CREATE_FESTS_TABLE);
		      
		    
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FESTS);
 
        // create fresh books table
        this.onCreate(db);	
	}
	
	/**
	 * Adding a new fest.
	 */
	public void addFest(Fest f) 
	{
		/**
		 * checking for repetition.
		 */
		boolean added =false;
		Fest testFest = new Fest();
		ArrayList<Fest> festList = new ArrayList<Fest>();
		festList = getAllFests();
		Iterator<Fest> festiter = festList.iterator();
		while(festiter.hasNext())
		{
			testFest = (Fest) festiter.next();
			Log.d("fest testing" , "name of fests :" + testFest.getName()+f.getName());
			if(testFest.getId() == f.getId() && testFest.getName().equalsIgnoreCase(f.getName())){added =true;Log.d("fest adding" , "duplicate found.");}
		}
		
		if(added)return;
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, f.getName()); 
		// Inserting Row
		db.insert(TABLE_FESTS, null, values);
		db.close(); // Closing database connection
	}
	
	/**
	 * Returning all Fests.
	 */
	public ArrayList<Fest> getAllFests() 
	{
		ArrayList<Fest> FestList = new ArrayList<Fest>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_FESTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Fest f = new Fest();
				f.setId(Integer.parseInt(cursor.getString(0)));
				f.setName(cursor.getString(NAME_INDEX));
				// Adding Fest to list
				FestList.add(f);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// return fest list
		return FestList;
	}
	/**
	 * @param fName name of the fest.
	 * @return fest id of the corresponding fest.
	 */
	public int idFromName(String fName)
	{
		String selectQuery = "SELECT * FROM " + TABLE_FESTS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Fest f = new Fest();
				f.setId(Integer.parseInt(cursor.getString(0)));
				f.setName(cursor.getString(NAME_INDEX));
				// Adding Fest to list
				if(f.getName().equalsIgnoreCase(fName))return f.getId();
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return -1;

	}
	
}
