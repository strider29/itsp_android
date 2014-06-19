package com.example.events;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public DatabaseManager db;
	public EventDatabaseManager edb;
	public final static String FESTS_KEY = "com.example.events.FESTS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DatabaseManager(this);
		edb = new EventDatabaseManager(this);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void addNewEvent (View view) throws ClientProtocolException, XmlPullParserException, URISyntaxException, IOException
	{
		// snippet to scan new qr code.
		 Toast.makeText(this,"so you want to scan qr code ?", Toast.LENGTH_SHORT).show(); // just to check functionality.
		 
		 // string is to be generated by the qr code.
		 // now, taking a sample url.
		 String xml_url = getString(R.string.sample_url);
		 System.out.println(xml_url);
		 
		/* // testing to add a new fest.
		  int testId = 1;
		  String testName = "cello" ;
		  
		  Fest f = new Fest();
		  f.setId(testId);
		  f.setName(testName);
		  
		  DatabaseManager db = new DatabaseManager(this);
		  db.addFest(f);
		  
		  ArrayList<Fest> list = new ArrayList<Fest>();
		  System.out.println(list.size());
		  list = db.getAllFests();
		  System.out.println(list.size());
		  
		  f.setId(2);
		  f.setName("TechFest");
		  db.addFest(f);
		  list = db.getAllFests();
		  
		 
		  System.out.println(list.size());
		  
		 */
		 
		 // testing event table.
		 int id = 1;
		 int fid = 1;
		 String name = "intro";
		 String manager = "Sandeep";
		 String time = "june 23 6 PM" ;
		 String venue = "vmcc";
		 
		 Event e  = new Event();
		 e.setEventId(id);
		 e.setFestId(fid);
		 e.setManager(manager);
		 e.setName(name);
		 e.setTime(time);
		 e.setVenue(venue);
		 
		 EventDatabaseManager db = new EventDatabaseManager(this);
		 db.addEvent(e);
		 
		 ArrayList<Event> list = new ArrayList<Event>();
		 list = db.getAllEvents();
		 Iterator<Event> it = list.iterator();
		 while(it.hasNext()) {
			 e = it.next();
			 System.out.println(e.getVenue());
		 }
		 e = list.get(0);
		 list = db.eventsOfFestId(1);
		 e=list.get(0);
		 System.out.println(e.getTime());
		 
		 XmlParser p = new XmlParser();
		 Fest testFest = new Fest();
		 Log.d("test","fest created.");
		 testFest = p.addFest(xml_url);
		 Log.d("main activity" , "fest added succesfully");
		 
//		 System.out.println(testFest.getName());
		 
		 
		 
		 
	}
	
	public void openFests(View view)
	{
		// calling new activity to show fests.
		
		 Fest testFest = new Fest();
		 testFest.setId(1);
		 testFest.setName("mi");
		 db.addFest(testFest);
		 
		 ArrayList<Fest> allFests = db.getAllFests();

		 // now to convert array list to list view.
		 
		 
		 /**
		  * calling FestsActivity.
		  */
		  Intent intent = new Intent(this, FestsActivity.class);
		  intent.putParcelableArrayListExtra(FESTS_KEY, allFests);
		  startActivity(intent);
		  

	}

}
