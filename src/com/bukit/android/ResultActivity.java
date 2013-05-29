
package com.bukit.android;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.database.Cursor;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        //Bundle extras = getIntent().getExtras();
        //String strPostcode = extras.getString("postcode");
        
        PopulateRestaurants();
        
        SetListviewListener();
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
	 		
	 		ListView lvRestaurants = (ListView) findViewById(R.id.lvRestaurants);

	 		lvRestaurants.setAdapter(adapter);
	 		    
	 		myDbHelper.close();
	 
	 	}catch(Exception sqle){
	 
	 		//String a = "rer";
	 		//throw sqle;
	 	}
    }
    
    
    private void SetListviewListener()
    {
    	ListView lvRestaurants = (ListView) findViewById(R.id.lvRestaurants);
    	
    	// Click event for single list row
    	lvRestaurants.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	
            	try
            	{
            		Restaurant rest = (Restaurant)parent.getItemAtPosition(position);
   
	            	Intent intent = new Intent(view.getContext(), ResultDetailsActivity.class);
			     	intent.putExtra("id", String.valueOf(rest.getID()));
			     	startActivity(intent);
            	}
            	catch (Exception e)
            	{
            		String test = "";
            	}
            }
        });
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
}
