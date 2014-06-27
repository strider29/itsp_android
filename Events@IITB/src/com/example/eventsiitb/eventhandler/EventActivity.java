package com.example.eventsiitb.eventhandler;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eventsiitb.R;

public class EventActivity extends Activity {

	private Context context = this;
	private int fId;
	private ArrayList<Event> allEvents = new ArrayList<Event>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
	
	Intent intent = getIntent();
	int  id = intent.getIntExtra("festid",-1);
	fId = id;
	Log.d("Event activity start.", "Int received is " + id);
	
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
	
	lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

		EventDatabaseManager edb = new EventDatabaseManager(context);
		int position = 0;
        public boolean onItemLongClick(AdapterView<?> arg0, View v,
                int index, long arg3) {

             position = index; 
             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
     				context);
      
     			// set title
     			alertDialogBuilder.setTitle("Delete Event ");
      
     			// set dialog message
     			alertDialogBuilder
     				.setMessage("Are you sure ?")
     				.setCancelable(false)
     				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
     					public void onClick(DialogInterface dialog,int id) {
     						// Deleting fest.
     						edb.deleteEvent(position+1);
     						finish();
     					}
     				  })
     				.setNegativeButton("No",new DialogInterface.OnClickListener() {
     					public void onClick(DialogInterface dialog,int id) {
     						// if this button is clicked, just close
     						// the dialog box and do nothing
     						dialog.cancel();
     					}
     				});
      
     				// create alert dialog
     				AlertDialog alertDialog = alertDialogBuilder.create();
      
     				// show it
     				alertDialog.show();
     				
     				
            return true;
        }
	}); 



	
	}
}
