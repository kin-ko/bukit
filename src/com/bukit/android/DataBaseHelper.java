package com.bukit.android;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

	private static String DB_PATH = "/data/data/com.bukit.android/databases/";
	 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "restaurants";
 
    // Contacts table name
    private static final String TABLE_NAME = "restaurants";
 
    /*
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_BUSINESS_NAME = "business_name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FULL_DESCRIPTION = "full_description";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_POSTCODE = "postcode";
    private static final String KEY_SUBURB = "suburb";
    private static final String KEY_WEBSITE = "website";
    */
    
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	this.myContext = context;
    }	
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DATABASE_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DATABASE_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
    // Getting All Restaurants
    
    
    public ArrayList<Restaurant> getAllRestaurants() {
    	
    	
    	ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
 
        SQLiteDatabase db = myDataBase;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Cursor cursor = db.query(TABLE_NAME, new String[] {"_id", "business_name", "address", "description"}, null, null, null, null, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Restaurant restaurant = new Restaurant();
            	restaurant.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            	restaurant.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            	restaurant.setBusinessName(cursor.getString(cursor.getColumnIndex("business_name")));
            	restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            	restaurant.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            	restaurant.setFullDescription(cursor.getString(cursor.getColumnIndex("full_description")));
            	restaurant.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            	restaurant.setPostcode(cursor.getString(cursor.getColumnIndex("postcode")));
            	restaurant.setSuburb(cursor.getString(cursor.getColumnIndex("suburb")));
            	restaurant.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
            	restaurant.setBusinessImage(cursor.getBlob(cursor.getColumnIndex("business_image")));
                // Adding restaurant to list
            	restaurantList.add(restaurant);
            } while (cursor.moveToNext());
        }
 
        return restaurantList;
    }
    
    
public Restaurant getAllRestaurantDetails (String id){
    	
    	
    	Restaurant restaurant = new Restaurant();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id;
 
        SQLiteDatabase db = myDataBase;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Cursor cursor = db.query(TABLE_NAME, new String[] {"_id", "business_name", "address", "description"}, null, null, null, null, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	restaurant.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            	restaurant.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            	restaurant.setBusinessName(cursor.getString(cursor.getColumnIndex("business_name")));
            	restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            	restaurant.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            	restaurant.setFullDescription(cursor.getString(cursor.getColumnIndex("full_description")));
            	restaurant.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            	restaurant.setPostcode(cursor.getString(cursor.getColumnIndex("postcode")));
            	restaurant.setSuburb(cursor.getString(cursor.getColumnIndex("suburb")));
            	restaurant.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
            	restaurant.setBusinessImage(cursor.getBlob(cursor.getColumnIndex("business_image")));
            } while (cursor.moveToNext());
        }
 
        return restaurant;
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	/*
        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
        		+ KEY_BUSINESS_NAME + " TEXT,"
        		+ KEY_PHONE + " TEXT,"
        		+ KEY_ADDRESS + " TEXT,"
        		+ KEY_DESCRIPTION + " TEXT,"
        		+ KEY_FULL_DESCRIPTION + " TEXT,"
        		+ KEY_EMAIL + " TEXT,"
        		+ KEY_POSTCODE + " TEXT,"
        		+ KEY_SUBURB + " TEXT,"
        		+ KEY_WEBSITE + " TEXT)";
        db.execSQL(CREATE_RESTAURANT_TABLE);
        */
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	/*
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 
        // Create tables again
        onCreate(db);
        */
    }
}
