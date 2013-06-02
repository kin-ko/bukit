package com.bukit.android;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import com.bukit.android.responsemodel.Address;
import com.bukit.android.responsemodel.Listing;
import com.bukit.android.responsemodel.ListingContact;

public class ResultDetailsActivity extends Activity {

	String Business_Name = "";
	String Short_Descriptor = "";
	String Address = "";
	String Phone = "";
	String Website = "";
	String Email = "";
	String Full_Descriptor = "";
	String Business_Logo_URL = "";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);
        
        Bundle extras = getIntent().getExtras();
        Business_Name = extras.getString("business_name");
        Short_Descriptor = extras.getString("short_descriptor");
        Address = extras.getString("address");
        Phone = extras.getString("phone");
        Website = extras.getString("website");
        Email = extras.getString("email");
        Full_Descriptor = extras.getString("full_descriptor");
        Business_Logo_URL = extras.getString("business_logo");

        PopulateRestaurantDetails();
    }
	
	public void PopulateRestaurantDetails()
    {
	 	try 
	 	{
	 		TextView lblBusinessName = (TextView) findViewById(R.id.lblBusinessName);
	        TextView lblHeadline = (TextView) findViewById(R.id.lblHeadline);
	        TextView lblPhone = (TextView) findViewById(R.id.lblPhone);
	        TextView lblAddress = (TextView) findViewById(R.id.lblAddress);
	        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
	        TextView lblWebsite = (TextView) findViewById(R.id.lblWebsite);
	        TextView lblFullDescription = (TextView) findViewById(R.id.lblFullDescription);
	        //ImageView imgBusinessImage = (ImageView) findViewById(R.id.imgBusinessImage);
	        
	        lblBusinessName.setText(Business_Name);
	        lblHeadline.setText(Short_Descriptor+ System.getProperty ("line.separator"));
	        lblPhone.setText(Phone);
	        lblAddress.setText(Address);
	        lblWebsite.setText(Website);
	        lblEmail.setText(Email + System.getProperty ("line.separator"));
	        lblFullDescription.setText(Full_Descriptor);
	        
	        setTitle(Business_Name);
	        
	        //new AsyncDownloadImage(imgBusinessImage).execute(Business_Logo_URL);
	 
	 	}catch(Exception e)
	 	{
	 		Log.e("Error", e.getMessage(), e);
			e.printStackTrace();
	 	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
}


