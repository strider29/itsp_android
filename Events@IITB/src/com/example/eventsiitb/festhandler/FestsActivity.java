package com.example.eventsiitb.festhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;









import com.example.eventsiitb.MainActivity;
import com.example.eventsiitb.R;
import com.example.eventsiitb.R.id;
import com.example.eventsiitb.R.layout;
import com.example.eventsiitb.eventhandler.Event;
import com.example.eventsiitb.eventhandler.EventActivity;
import com.example.eventsiitb.eventhandler.EventDatabaseManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FestsActivity extends Activity {

	private ListView lv;
	private String s;
	public TextView t;
	List<String> festNames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fests);
		
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
