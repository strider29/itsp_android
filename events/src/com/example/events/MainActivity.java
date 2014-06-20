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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {


	public final static String FESTS_KEY = "com.example.events.FESTS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


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
		// testing on Emulator for database testing.
		addFestFromUrl("http://www.cse.iitb.ac.in/~amanmadaan/itsp/cf.xml");
		// snippet to scan new qr code.
		/*
		 Toast.makeText(this,"Point your Camera at QR Code", Toast.LENGTH_SHORT).show(); 
		 Intent intent = new Intent(this, CameraTestActivity.class);
		 startActivityForResult(intent,1);		 */
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String result=data.getStringExtra("result");
	            addFestFromUrl(result);
	        }
	        if (resultCode == RESULT_CANCELED) {
	          // TODO  : Displaying error. Some problem with QR Code.
	        }
	    }
	}
	/**
	 * function to link qr and xml parser.
	 * @param url obtained from CameraTestActity
	 */
	public  void addFestFromUrl(String url)
	{
		Fest f = new Fest();
		ArrayList<Event> eventList = new ArrayList<Event>();
		try{
			//TODO : Check Internet Connection. Accordingly display errors.
			XmlParser myParser = new XmlParser();
			f = myParser.getFest(url);
			eventList = myParser.getAllEvents(url);
			Log.d("adding events. " , "number of events added :"+eventList.size());
		}catch(Exception ex)
		{
			Log.d("adding fest from url function " , "Exception :"+ ex);
		}
		System.out.println(f.getName());
		try{
			DatabaseManager db = new DatabaseManager(this);
			db.addFest(f);
			EventDatabaseManager edb  = new EventDatabaseManager(this);
			Event e = new Event();
			Iterator it = eventList.iterator();
			while(it.hasNext())
			{
				e = (Event) it.next();
				edb.addEvent(e);
			}						
		}catch(Exception e)
		{
			Log.d("adding fest and events to database " , "Exception "+e);
		}
	}
	public void openFests(View view)
	{
		// calling new activity to show fests.
		

		 DatabaseManager db = new DatabaseManager(this);
		 ArrayList<Fest> allFests = db.getAllFests();
		 /**
		  * calling FestsActivity.
		  */
		  Intent intent = new Intent(this, FestsActivity.class);
		  intent.putParcelableArrayListExtra(FESTS_KEY, allFests);
		  startActivity(intent);

	}

}
