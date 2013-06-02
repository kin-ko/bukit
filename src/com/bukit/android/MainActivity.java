package com.bukit.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SetSearchButtonClickListener();
    }

    public void SetSearchButtonClickListener(){
    	ImageButton btnSearch = (ImageButton)findViewById(R.id.btnSearch);
    	
    	btnSearch.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
				try
				{
					Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
					
			     	intent.putExtra("what", "restaurants");
			     	intent.putExtra("where", "victoria");
			     	startActivity(intent);
				}
				catch(Exception e){
					Log.e("Error", e.getMessage(), e);
					e.printStackTrace();
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
