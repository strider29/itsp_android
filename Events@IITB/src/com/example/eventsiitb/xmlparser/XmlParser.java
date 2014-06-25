package com.example.eventsiitb.xmlparser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.eventsiitb.eventhandler.Event;
import com.example.eventsiitb.festhandler.Fest;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;



public class XmlParser  extends android.app.Activity {

	/**
	 * @param url
	 * @return fest corresponding to the xml contained in url. 
	 * 			events are not added.
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	public Fest getFest(String url) throws XmlPullParserException, ClientProtocolException, URISyntaxException, IOException
	{
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        
        Fest newFest = new Fest();
        boolean festIdSet = false;
        boolean festNameFound = false;
        boolean festNameSet =false; // extra variable because events too have same tag called name.
        xpp.setInput(new InputStreamReader(getUrlData(url)));  

        try{
        	int eventType = xpp.getEventType();
        	while (eventType != XmlPullParser.END_DOCUMENT)
        	{
        		if(eventType == XmlPullParser.START_DOCUMENT) 
        		{
        			Log.d("xml parser","parsing started");
        		} 
        		else if(eventType == XmlPullParser.END_DOCUMENT)
        		{
        			Log.d("xml parser", "parsing done.");
        		}
        		else if(eventType == XmlPullParser.START_TAG)
        		{
        			String  fid = xpp.getName();
        			if(fid.equalsIgnoreCase("festid"))festIdSet = true;
        			if(fid.equalsIgnoreCase("name") && !festNameSet)festNameFound = true;
        		}
        		else if(eventType == XmlPullParser.END_TAG)
        		{
        			System.out.println("End tag "+xpp.getName());
        		}
        		else if(eventType == XmlPullParser.TEXT) {
        			if(festIdSet)
        			{
        				System.out.println("id succesfully set.  : " + xpp.getText() );
        				int a = Integer.parseInt(xpp.getText());
        				newFest.setId(a);
        				festIdSet =false;
        			}
        			if(festNameFound)
        			{
        				System.out.println("name successfully set.");
        				newFest.setName(xpp.getText());
        				festNameSet = true;
        				festNameFound =false;
        			}
        		}
        		eventType = xpp.next();
        	}
        }catch(Exception e)
        {
        	Log.d("xml parsing " , "exception "+e);
        }
        
        return newFest;
	}
	

	/**
	 * @param url
	 * @return ArrayList of events corresponding to the xml contained in url. 
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	public ArrayList<Event> getAllEvents(String url) throws XmlPullParserException, ClientProtocolException, URISyntaxException, IOException
	{
		
			
			int festId = 0;
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
	        xpp.setInput(new InputStreamReader(getUrlData(url)));  
	        
			ArrayList<Event> eventList = new ArrayList<Event>();
			Event newEvent = new Event();
			boolean festIdSet = false;
			boolean eventIdSet = false;
			boolean nameSet = false;
			boolean managerSet =false;
			boolean timeSet = false;
			boolean venueSet = false;
			boolean imageUrlSet = false;
			try{
				int eventType = xpp.getEventType();
	        	while (eventType != XmlPullParser.END_DOCUMENT)
	        	{
	        		if(eventType == XmlPullParser.START_DOCUMENT) 
	        		{
	        			Log.d("xml parser","parsing started");
	        		} 
	        		else if(eventType == XmlPullParser.START_TAG)
	        		{
	        			String  fid = xpp.getName();
	        			if(fid.equalsIgnoreCase("festid"))festIdSet = true;
	        			if(fid.equalsIgnoreCase("name") )nameSet = true;
	        			if(fid.equalsIgnoreCase("eventId"))eventIdSet = true;
	        			if(fid.equalsIgnoreCase("time"))timeSet = true;
	        			if(fid.equalsIgnoreCase("manager"))managerSet = true;
	        			if(fid.equalsIgnoreCase("venue"))venueSet = true;
	        			if(fid.equalsIgnoreCase("posterUrl")){imageUrlSet = true;Log.d("xml parsing " , "image url found.");}
	        		}
	        		else if(eventType == XmlPullParser.END_TAG)
	        		{
	        			System.out.println("End tag "+xpp.getName());
	        			if(xpp.getName().equalsIgnoreCase("event"))
	        				{
	        				newEvent.setFestId(festId);
	        				eventList.add(newEvent);
	        				Event e = new Event();
	        				newEvent = e;
	        				System.out.println("first element name is "+eventList.get(0).getName());
	        				System.out.println("Adding event with name " + newEvent.getName());
	        				}
	        		}
	        		else if(eventType == XmlPullParser.TEXT) {
	        			if(festIdSet)
	        			{
	        				System.out.println("id succesfully set.  : " + xpp.getText() );
	        				int a = Integer.parseInt(xpp.getText());
	        				festId = a;
	        				festIdSet =false;
	        			}
	        			if(nameSet)
	        			{
	        				System.out.println("name successfully set.");
	        				newEvent.setName(xpp.getText());
	        				nameSet = false;
	        			}
	        			if(timeSet)
	        			{
	        				newEvent.setTime(xpp.getText());
	        				timeSet =false;
	        			}
	        			if(venueSet)
	        			{
	        				newEvent.setVenue(xpp.getText());
	        				venueSet =false;
	        			}
	        			if(managerSet)
	        			{
	        				newEvent.setManager(xpp.getText());
	        				managerSet =false;
	        			}
	        			if(eventIdSet)
	        			{
	        				newEvent.setEventId(Integer.parseInt(xpp.getText()));
	        				eventIdSet =false;
	        			}
	        			if(imageUrlSet)
	        			{
	        				newEvent.setPosterUrl(xpp.getText());
	        				imageUrlSet = false;
	        				Log.d("url :" , xpp.getText());
	        			}
	        		}
	        		eventType = xpp.next();
	        	}
			
			}catch(Exception e)
			{
				Log.d("xml parsing, events returning" , "Exception caught"+e);
			}
		
			return eventList;
	}
        

	
	
	
	public InputStream getUrlData(String url) 

			throws URISyntaxException, ClientProtocolException, IOException {
			if(url.charAt(0) != 'h'){url = "http://"+url;Log.d("uff" , "input stream");}
			  DefaultHttpClient client = new DefaultHttpClient();
			  HttpGet method = new HttpGet(new URI(url));
			  HttpResponse res = client.execute(method);
			  return  res.getEntity().getContent();
			}

		
		 
	}


