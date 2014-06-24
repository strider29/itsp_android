package com.example.events;

import java.util.ArrayList;
import java.util.Iterator;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.events.eventhandler.Event;
import com.example.events.eventhandler.EventDatabaseManager;
import com.example.events.festhandler.DatabaseManager;
import com.example.events.festhandler.Fest;
import com.example.events.festhandler.FestsActivity;

public class EventActivity extends ActionBarActivity {

	private int fId;
	private ArrayList<Event> allEvents = new ArrayList<Event>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Intent intent = getIntent();
		int  id = intent.getIntExtra("festid",-1);
		fId = id;
		Log.d("Event activity start.", "Int received is " + id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_event,
					container, false);
			return rootView;
		}
	}
	
	public void onClick(View view)
	{
		Log.d("onClick" , "trying to show events.");
		EventDatabaseManager edb = new EventDatabaseManager(this);
		System.out.println(fId+"is the fest id. no. of events : "+allEvents.size());
		allEvents = edb.eventsOfFestId(fId);
		Event e = new Event();
		// now should populate list view with this ArrayList.
		ArrayList<String>  eventNames = new ArrayList<String>();
		Iterator<Event> eIter = allEvents.iterator();
		while(eIter.hasNext())
		{
			e = (Event) eIter.next();
			eventNames.add(e.getName());
			Log.d("event names :" , " "+e.getName());
		}
		final ListView lv;
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, eventNames));
		lv.setTextFilterEnabled(true);	
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				Intent intent = new Intent(EventActivity.this,EventDisplayActivity.class);
				String eventName =(String) lv.getItemAtPosition(position);
				//DatabaseManager db  = new DatabaseManager(EventActivity.this);
				intent.putExtra("eventname",eventName);
				startActivity(intent);
			}		
		});
	}
}
