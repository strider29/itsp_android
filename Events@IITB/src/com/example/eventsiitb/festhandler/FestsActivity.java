package com.example.eventsiitb.festhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventsiitb.MainActivity;
import com.example.eventsiitb.R;
import com.example.eventsiitb.eventhandler.EventActivity;

public class FestsActivity extends Activity {
	
	final Context context = this;
	private ListView lv;
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
		
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			DatabaseManager db = new DatabaseManager(context);
			int position = 0;
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                    int index, long arg3) {

                 position = index; 
                 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
         				context);
          
         			// set title
         			alertDialogBuilder.setTitle("Delete Fest ");
          
         			// set dialog message
         			alertDialogBuilder
         				.setMessage("Are you sure ?")
         				.setCancelable(false)
         				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
         					public void onClick(DialogInterface dialog,int id) {
         						// Deleting fest.
         						db.deleteFest(position+1);
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

		// Handling empty case.
		if(festNames.size() == 0 )
		{
			Toast.makeText(this,"Fest list is empty!", Toast.LENGTH_LONG).show(); 
		}
		
	}
}
