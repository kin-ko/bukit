package com.bukit.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.Dialog;
import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.Gravity;
import android.text.util.Linkify;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bukit.android.client.SapiClient;
import com.bukit.android.client.SapiEnvironment;
import com.bukit.android.requestmodel.SearchParams;
import com.bukit.android.responsemodel.Listing;
import com.bukit.android.responsemodel.ListingContact;
import com.bukit.android.responsemodel.SearchResponse;
import com.bukit.android.responsemodel.ListingOpeningHours;


public class SearchResultActivity extends ListActivity implements OnItemClickListener  {

	private ListingSerpAdapter adapter;
	private String whatText;
	private String whereText;
	private SapiClient client;
	private String apiKey;
	private String proxyHost;
	private int proxyPort;

	private Dialog dialog;
	
	//TODO: show a message for no results
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try
		{
			apiKey = getString(R.string.api_key);
			client = new SapiClient(apiKey, SapiEnvironment.TEST);
			
			proxyHost = getString(R.string.proxy_path);
			if (proxyHost != null && !proxyHost.equals("") && !proxyHost.matches("\\s*")) {
			    proxyPort = getString(R.string.proxy_port) != null ? Integer.parseInt(getString(R.string.proxy_port)) : null;
			    client.setProxy(proxyHost, proxyPort);
			}
			
			setContentView(R.layout.activity_result);
			
			dialog = new Dialog(this);
		    dialog.setContentView(R.layout.activity_result_details);
		    dialog.setTitle("About Android Dialog Box");
		     
			Bundle extras = getIntent().getExtras();
			whatText = extras.getString("what");
			whereText = extras.getString("where");
			
			adapter = new ListingSerpAdapter(this, R.layout.list_row);
			setListAdapter(adapter);
			GetJsonResponseTask task = new GetJsonResponseTask(this, adapter);
			
			SearchParams params = new SearchParams.Builder()
				.withQuery(whatText)
				.withLocation(whereText)
				// Below are some examples of more types of filters that can be added as desired
	//			.withPage(2)
	//			.withPostcode(6000)
				.withRows(10)
	//			.withSortBy(SortBy.DISTANCE)
	//			.withState(State.WA)
	//			.withState(State.NT)
	//			.withContent(ContentFilter.BUSINESS_LOGO)
	//			.withContent(ContentFilter.SHORT_DESCRIPTOR)
				.withCategoryId("33650") // category id for restaurants
				.build();
			task.execute(params);
			
			getListView().setOnItemClickListener(this);
		}
		catch (Exception e)
		{
			Log.e("Error", e.getMessage(), e);
			e.printStackTrace();
		}
		//getListView().setOnScrollListener(this);
	}

	/*
	@Override
	public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
		// do nothing
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// do nothing
	}

	 */
	class GetJsonResponseTask extends AsyncTask<SearchParams, Integer, List<Listing>> {
		Context context;
		ProgressDialog waitSpinner;
		private ListingSerpAdapter adapter;
		
		public GetJsonResponseTask(Context context, ListingSerpAdapter adapter) {
			this.context = context;
			this.waitSpinner = new ProgressDialog(context);
			this.adapter = adapter;
		}
		
		@Override
		protected void onPreExecute() {
			waitSpinner = ProgressDialog.show(context, "Searching...", "", true);
		}
		
		@Override
		protected List<Listing> doInBackground(SearchParams... params) {
			try {
				SearchResponse response = client.search(params[0]);
				return response.getResults();
			} catch (Exception e) {
				//TODO: handle errors better?
				Log.e("Error", e.getMessage(), e);
				e.printStackTrace();
				
				return new ArrayList<Listing>();
			}
		}

		@Override
		protected void onPostExecute(List<Listing> results) {
			adapter.addListings(results);
			waitSpinner.cancel();
		}
	}
	
	class ListingSerpAdapter extends ArrayAdapter<Listing> {

		private Context context;
		int resource;
		
		public ListingSerpAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
			this.context = context;
			this.resource = textViewResourceId;
		}
		
		public void addListings(List<Listing> results) {
			for (Listing listing : results) {
				add(listing);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Listing listing = getItem(position);
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View rowView = inflater.inflate(R.layout.list_row, null);
	        
	        TextView lblBusinessName = (TextView) rowView.findViewById(R.id.lblBusinessName);
	        TextView lblHeadline = (TextView) rowView.findViewById(R.id.lblHeadline);
	        TextView lblPhone = (TextView) rowView.findViewById(R.id.lblPhone);
	        TextView lblAddress = (TextView) rowView.findViewById(R.id.lblAddress);
	        ImageView list_image = (ImageView) rowView.findViewById(R.id.list_image);
	        
	        try
	        {
		        lblBusinessName.setText(listing.getName());
		        lblHeadline.setText(listing.getShortDescriptor() + System.getProperty ("line.separator"));
	
				if (listing.getPrimaryAddress() != null) {
					String addressLine = listing.getPrimaryAddress().getAddressLine();
					lblAddress.setText(
							  (addressLine != null ? addressLine + " " : "") 
							+ listing.getPrimaryAddress().getSuburb());
				} else {
					lblAddress.setHeight(0); //TODO: better way to do this?
				}
				
				if (listing.getPrimaryContacts().get(0) != null) {
					lblPhone.setText(listing.getPrimaryContacts().get(0).getValue());
					//Linkify.addLinks(lblPhone, Linkify.ALL);
				} else {
					lblPhone.setHeight(0);
				}
			
				new AsyncDownloadImage(list_image).execute(listing.getBusinessLogo().getUrl());
	        }
	        catch(Exception e)
	        {
	        	Log.e("Error", e.getMessage(), e);
				e.printStackTrace();
	        }
	        
			return rowView;
		}
	}
	
	public void PopulateRestaurants()
    {
    	DataBaseHelper myDbHelper = new DataBaseHelper(this);

        try {
 
        	myDbHelper.createDataBase();
 
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 
	 	}
	 
	 	try {
	 
	 		myDbHelper.openDataBase();
	 		
	 		ArrayList<Restaurant> restaurants = myDbHelper.getAllRestaurants();
	 		
	 		//final RestaurantListAdapter adapter = new RestaurantListAdapter(this, restaurants);
	 		final ListAdapter adapter = new ListAdapter(this, restaurants);
	 		
	 		ListView lvRestaurants = (ListView) findViewById(android.R.id.list);

	 		lvRestaurants.setAdapter(adapter);
	 		    
	 		myDbHelper.close();
	 
	 	}catch(Exception sqle){
	 
	 		//String a = "rer";
	 		//throw sqle;
	 	}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	//super.onItemClick(parent, view, position, id);
    	try
    	{
    		Listing rest = (Listing)parent.getItemAtPosition(position);

    		//ShowRestaurantDetailsInNewActivity(rest, view);
    		ShowRestaurantDetailsInDialog(rest);
    	}
    	catch (Exception e)
    	{
    		Log.e("Error", e.getMessage(), e);
			e.printStackTrace();
    	}
    }
    
    private void ShowRestaurantDetailsInNewActivity(Listing rest, View view)
    {
    	Intent intent = new Intent(view.getContext(), ResultDetailsActivity.class);
		
     	intent.putExtra("business_name", rest.getName());
     	intent.putExtra("short_descriptor", rest.getShortDescriptor());
     	intent.putExtra("business_logo", rest.getBusinessLogo().getUrl());
     	
     	if (rest.getPrimaryAddress() != null) {
			String addressLine = rest.getPrimaryAddress().getAddressLine();
			intent.putExtra("address", (addressLine != null ? addressLine + " " : "") + rest.getPrimaryAddress().getSuburb());
     	}
	 	
		
     	for (ListingContact c : rest.getPrimaryContacts()) {   
     		switch(c.getType()){
	     		case URL:
	     			intent.putExtra("website", c.getValue());
	     			break;
	     		case EMAIL:
	     			intent.putExtra("email", c.getValue());
	     			break;
	     		case PHONE:
	     			intent.putExtra("phone", c.getValue());
	     			break;
	     		case PAGER:
	     		case MOBILE:
	     		case INTERNATIONAL:
	     		case FAX:
	     			break;
     		}
     	}
   
     	startActivity(intent);
    	
    }
    
    private void ShowRestaurantDetailsInDialog(Listing rest)
    {
    	//set up dialog
        Dialog dialog = new Dialog(SearchResultActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_result_details);
        //dialog.setTitle(rest.getName());
        dialog.setCancelable(true);
        
        
        //Window window = dialog.getWindow();
        //WindowManager.LayoutParams wlp = window.getAttributes();
        //wlp.gravity = Gravity.RIGHT;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //window.setAttributes(wlp);
        
        TextView lblBusinessName = (TextView) dialog.findViewById(R.id.lblBusinessName);
        TextView lblHeadline = (TextView) dialog.findViewById(R.id.lblHeadline);
        TextView lblPhone = (TextView) dialog.findViewById(R.id.lblPhone);
        TextView lblAddress = (TextView) dialog.findViewById(R.id.lblAddress);
        TextView lblEmail = (TextView) dialog.findViewById(R.id.lblEmail);
        TextView lblWebsite = (TextView) dialog.findViewById(R.id.lblWebsite);
        TextView lblFullDescription = (TextView) dialog.findViewById(R.id.lblFullDescription);
        WebView lblOpeningHours = (WebView) dialog.findViewById(R.id.lblOpeningHours);
        
        //ImageView imgBusinessImage = (ImageView) dialog.findViewById(R.id.imgBusinessImage);
        
        lblBusinessName.setText(rest.getName() );
        lblHeadline.setText(rest.getShortDescriptor() + System.getProperty ("line.separator"));
        
        for (ListingContact c : rest.getPrimaryContacts()) {   
     		switch(c.getType()){
	     		case URL:
	     			lblWebsite.setText(c.getValue());
	     			Linkify.addLinks(lblWebsite, Linkify.ALL);
	     			break;
	     		case EMAIL:
	     			lblEmail.setText(c.getValue() + System.getProperty ("line.separator"));
	     			Linkify.addLinks(lblEmail, Linkify.ALL);
	     			break;
	     		case PHONE:
	     			lblPhone.setText(c.getValue());
	     			Linkify.addLinks(lblPhone, Linkify.ALL);
	     			break;
	     		case PAGER:
	     		case MOBILE:
	     		case INTERNATIONAL:
	     		case FAX:
	     			break;
     		}
     	}
        
        WebSettings webSettings = lblOpeningHours.getSettings();
        lblOpeningHours.loadData(ListingManager.GetBusinessOpeningHours(rest), "text/html", "utf-8");
        lblOpeningHours.setBackgroundColor(0x00000000);
        webSettings.setDefaultFontSize(12);
   
        if (rest.getPrimaryAddress() != null) {
			String addressLine = rest.getPrimaryAddress().getAddressLine();
			lblAddress.setText((addressLine != null ? addressLine + " " : "") + rest.getPrimaryAddress().getSuburb());
			Linkify.addLinks(lblAddress, Linkify.ALL);
     	}
       
        lblFullDescription.setText("");
        
        //new AsyncDownloadImage(imgBusinessImage).execute(rest.getBusinessLogo().getUrl());
        
        dialog.show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
