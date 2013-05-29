package com.bukit.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
	
	private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SetSearchButtonClickListener();
    }


    public void SetSearchButtonClickListener(){
    	
    	ImageButton btnSearch = (ImageButton)findViewById(R.id.btnSearch);
    	
    	btnSearch.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
				
				try
				{
					//TextView txtPostcode = (TextView)findViewById(R.id.txtPostcode);
					
					Intent intent = new Intent(MainActivity.this, ResultActivity.class);
					
			     	//intent.putExtra("postcode", txtPostcode.getText().toString());
					
			     	startActivity(intent);
				}
				catch(Exception e){
					//TextView txtPostcode = (TextView)findViewById(R.id.txtPostcode);
				}
				
				progressDialog.dismiss();
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
