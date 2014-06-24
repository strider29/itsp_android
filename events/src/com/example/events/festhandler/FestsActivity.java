package com.example.events.festhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.events.EventActivity;
import com.example.events.MainActivity;
import com.example.events.R;
import com.example.events.eventhandler.Event;
import com.example.events.eventhandler.EventDatabaseManager;

public class FestsActivity extends ActionBarActivity {
	
	private ListView lv;
	private String s;
	public TextView t;
	List<String> festNames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fests);

		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Intent intent = getIntent();
		ArrayList<Fest> festList = new ArrayList<Fest>();
		festList = intent.getParcelableArrayListExtra(MainActivity.FESTS_KEY);
		
		System.out.println(festList.size());
		
		Fest f = new Fest();
		// now should populate list view with this ArrayList.
		festNames = new ArrayList<String>();
		Iterator<Fest> festIter = festList.iterator();
		while(festIter.hasNext())
		{
			f = (Fest) festIter.next();
			festNames.add(f.getName());
			Log.d("fest names :" , " "+f.getName());
		}
		

		EventDatabaseManager edb = new EventDatabaseManager(this);
		ArrayList<Event> eventList = new ArrayList<Event>();
		eventList = edb.getAllEvents();
		s = Integer.toString(festNames.size()*10+eventList.size());
		if(festNames.size() > 0)s = s+festNames.get(0);
		if(eventList.size() >0)s = s + eventList.get(0).getVenue();

		

		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fests, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_fests,
					container, false);
			return rootView;
		}
	}


	public void onClick(View view)
	{
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, festNames));
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
			{
				Intent intent = new Intent(FestsActivity.this,EventActivity.class);
				String festName =(String) lv.getItemAtPosition(position);
				DatabaseManager db  = new DatabaseManager(FestsActivity.this);
				int festId = db.idFromName(festName);
				intent.putExtra("festid",festId);
				startActivity(intent);
			}		
		});
	}
}
