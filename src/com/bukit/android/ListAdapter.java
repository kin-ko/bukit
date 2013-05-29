package com.bukit.android;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

 
public class ListAdapter extends ArrayAdapter<Restaurant> {
 
    //private Activity activity;
    //private ArrayList<HashMap<String, String>> data;
    //private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    
    private final Context context;
    private final ArrayList<Restaurant> values;
 
    /*
    public ListAdapater(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    */
    
    public ListAdapter(Context context, ArrayList<Restaurant> values) {
        super(context, R.layout.activity_result, values);
        this.context = context;
        this.values = values;
     }
    
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, null);
        
        TextView lblBusinessName = (TextView) rowView.findViewById(R.id.lblBusinessName);
        TextView lblHeadline = (TextView) rowView.findViewById(R.id.lblHeadline);
        TextView lblPhone = (TextView) rowView.findViewById(R.id.lblPhone);
        TextView lblAddress = (TextView) rowView.findViewById(R.id.lblAddress);

        
        Restaurant rest = values.get(position);
        lblBusinessName.setText(rest.getBusinessName() );
        lblHeadline.setText(rest.getDescription() + System.getProperty ("line.separator"));
        lblPhone.setText(rest.getPhone());
        lblAddress.setText(rest.getAddress());

        
        return rowView;
    }
}
