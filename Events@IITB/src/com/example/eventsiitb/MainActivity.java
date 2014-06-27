package com.example.eventsiitb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import com.example.eventsiitb.eventhandler.Event;
import com.example.eventsiitb.eventhandler.EventDatabaseManager;
import com.example.eventsiitb.festhandler.DatabaseManager;
import com.example.eventsiitb.festhandler.Fest;
import com.example.eventsiitb.festhandler.FestsActivity;
import com.example.eventsiitb.qrscanner.CameraTestActivity;
import com.example.eventsiitb.xmlparser.XmlParser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	public final static String FESTS_KEY = "com.example.events.FESTS";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

/**
 * 	Adding new Fest.
 */
public void addNewEvent (View view) throws ClientProtocolException, XmlPullParserException, URISyntaxException, IOException
	{
		// testing on Emulator for database testing.
		addFestFromUrl("http://www.cse.iitb.ac.in/~amanmadaan/itsp/celloFest.xml");
		/* original code :
		 Toast.makeText(this,"Point your Camera at QR Code", Toast.LENGTH_SHORT).show(); 
		 Intent intent = new Intent(this, CameraTestActivity.class);
		 startActivityForResult(intent,1);		 */
	}
	
protected void onActivityResult(int requestCode, int resultCode, Intent data) 
{
    if (requestCode == 1) {
        if(resultCode == RESULT_OK){
            String result=data.getStringExtra("result");
            Log.d("url retrieval" , result);
            addFestFromUrl(result);
        }
        if (resultCode == RESULT_CANCELED) {
        	Toast.makeText(this,"Oops!!The QR code doesn't seem to be correct.", Toast.LENGTH_SHORT).show(); 
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
		//TODO : Maintaining a string array of urls.
		ConnectivityManager connMgr = (ConnectivityManager) 
		        getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		    if (networkInfo == null || !networkInfo.isConnected()) {
		    	Toast.makeText(this,"Please Check Your internet Connection.Fest not added.", Toast.LENGTH_SHORT).show();
		    	return;
		    } 

		XmlParser myParser = new XmlParser();
		f = myParser.getFest(url);
		eventList = myParser.getAllEvents(url);
	}catch(Exception ex)
	{
		Log.d("xml parsing : " , "Exception :"+ ex);
	}
	
	
	try{
		DatabaseManager db = new DatabaseManager(this);
		db.addFest(f);
		EventDatabaseManager edb  = new EventDatabaseManager(this);
		Event e = new Event();
		Iterator<Event> it = eventList.iterator();
		while(it.hasNext())
		{
			e = (Event) it.next();
			// adding image part.
			//String fileName = Integer.toString(e.getFestId())+Integer.toString(e.getEventId());
			// new DownloadFileFromURL().execute(e.getPosterUrl(),fileName);
			edb.addEvent(e);
		}						
	}catch(Exception e)
	{
		Log.d("adding fest and events to database " , "Exception "+e);
	}
}

public void openFests(View view)
{
	  DatabaseManager db  = new DatabaseManager(this);
	  ArrayList<Fest> allFests = db.getAllFests();
	  Intent intent = new Intent(this, FestsActivity.class);   // calling FestActivity class.
	  intent.putParcelableArrayListExtra(FESTS_KEY, allFests);
	  startActivity(intent);
}	

}

