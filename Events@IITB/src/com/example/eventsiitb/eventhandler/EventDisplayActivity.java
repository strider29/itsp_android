package com.example.eventsiitb.eventhandler;



import com.example.eventsiitb.R;
import com.example.eventsiitb.R.id;
import com.example.eventsiitb.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class EventDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_display);
		
		Intent intent = getIntent();
		String eventName = intent.getStringExtra("eventname");
		TextView t = (TextView) findViewById(R.id.textView1);
		t.setText(eventName);
		EventDatabaseManager edb  = new EventDatabaseManager(this);
		Event e = new Event();
		e = edb.eventFromName(eventName);
		String eventDisplay = e.getName()+"\n"+"Time : "+e.getTime()+"\n"+ "Place :" + e.getVenue() + "\n"+e.getPosterUrl();
		Toast.makeText(this,eventDisplay, Toast.LENGTH_SHORT).show(); 
		t.setText(eventDisplay);
	}
}
