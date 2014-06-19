package com.example.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

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
	public Fest addFest(String url) throws XmlPullParserException, ClientProtocolException, URISyntaxException, IOException
	{
		Log.d("xml parsing ","started parse function");
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        
        Log.d("xml parsing ","no probs with basic ones");
        Fest newFest = new Fest();
        boolean festIdSet = false;
        boolean festNameSet = false;
        xpp.setInput(new InputStreamReader(getUrlData("http://www.cse.iitb.ac.in/~amanmadaan/itsp/events/celloFest.xml")));  

        Log.d("xml parsing ","parsing about to start.");
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
        		if(fid == "festid")festIdSet = true;
        		if(fid == "name")festNameSet = true;
        		System.out.println("start tag "+fid);
        	}
        	else if(eventType == XmlPullParser.END_TAG)
        	{
             System.out.println("End tag "+xpp.getName());
        	}
        	else if(eventType == XmlPullParser.TEXT) {
             System.out.println("Text "+xpp.getText());
             if(festIdSet)
             {
            	 System.out.println("id succesfully set.");
            	 newFest.setId(Integer.parseInt(xpp.getText()));
             }
             if(festNameSet)
             {
            	 System.out.println("name successfully set.");
            	 newFest.setName(xpp.getText());
             }
         }
         eventType = xpp.next();
        } }catch(Exception e)
        {
        	Log.d("xml parsing " , "exception "+e);
        }
        Log.d("xml parsing " , "returning properly.");
        Log.d("xml parsing " , "name of fest :" + newFest.getName());
        return newFest;
	}
	
	

	public InputStream getUrlData(String url) 

			throws URISyntaxException, ClientProtocolException, IOException {
			if(url.charAt(0) != 'h'){url = "http://"+url;Log.d("uff" , "input stream");}

			  DefaultHttpClient client = new DefaultHttpClient();

			  HttpGet method = new HttpGet(new URI(url));
			  try{

			  HttpResponse res = client.execute(method);
			  }catch(Exception e)
			  {
				  Log.d("input stream  " , "error : " +e);
			  }
			  
			  HttpResponse res = client.execute(method);
			  return  res.getEntity().getContent();
			}

		
		 
	}


