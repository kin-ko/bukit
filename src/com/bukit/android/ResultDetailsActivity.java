package com.bukit.android;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ResultDetailsActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);
        
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        
        PopulateRestaurantDetails(id);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
    }
	
	public void PopulateRestaurantDetails(String id)
    {
    	DataBaseHelper myDbHelper = new DataBaseHelper(this);

	 	try {
	 
	 		myDbHelper.openDataBase();
	 		
	 		Restaurant restaurants = myDbHelper.getAllRestaurantDetails(id);
	 		    
	 		myDbHelper.close();
	 		
	 		TextView lblBusinessName = (TextView) findViewById(R.id.lblBusinessName);
	        TextView lblHeadline = (TextView) findViewById(R.id.lblHeadline);
	        TextView lblPhone = (TextView) findViewById(R.id.lblPhone);
	        TextView lblAddress = (TextView) findViewById(R.id.lblAddress);
	        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
	        TextView lblWebsite = (TextView) findViewById(R.id.lblWebsite);
	        TextView lblFullDescription = (TextView) findViewById(R.id.lblFullDescription);
	        
	        lblBusinessName.setText(restaurants.getBusinessName() );
	        lblHeadline.setText(restaurants.getDescription() + System.getProperty ("line.separator"));
	        
	        lblPhone.setText(restaurants.getPhone());
	        lblAddress.setText(restaurants.getAddress());
	        
	        lblWebsite.setText(restaurants.getWebsite());
	        lblEmail.setText(restaurants.getEmail() + System.getProperty ("line.separator"));
	        
	        lblFullDescription.setText(restaurants.getFullDescription());
	        
	        setTitle(restaurants.getBusinessName());
	 
	 	}catch(Exception sqle){
	 
	 		String a = "rer";
	 		//throw sqle;
	 	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
}



