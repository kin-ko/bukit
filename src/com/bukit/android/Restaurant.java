package com.bukit.android;

public class Restaurant {

	//private variables
    int _id;
    String _business_name;
    String _phone;
    String _address;
    String _email;
    String _website;
    String _suburb;
    String _postcode;
    String _description;
    String _full_description;
    byte[] _business_image;

     
    // Empty constructor
    public Restaurant(){
         
    }
    // constructor
    public Restaurant(int id, byte[] business_image, String business_name, String phone, String address, String email, String website, String suburb, String postcode, String description, String full_description){
        this._id = id;
        this._business_name = business_name;
        this._phone = phone;
        this._address = address;
        this._email = email;
        this._website = website;
        this._suburb = suburb;
        this._postcode = postcode;
        this._description = description;
        this._full_description = full_description;
        this._business_image = business_image;
    }
     
    // constructor
    public Restaurant(byte[] business_image, String business_name, String phone, String address, String email, String website, String suburb, String postcode, String description, String full_description){
    	this._business_name = business_name;
        this._phone = phone;
        this._address = address;
        this._email = email;
        this._website = website;
        this._suburb = suburb;
        this._postcode = postcode;
        this._description = description;
        this._full_description = full_description;
        this._business_image = business_image;
    }
    
    public int getID(){
        return this._id;
    }
     
    public void setID(int id){
        this._id = id;
    }
    
    public byte[] getBusinessImage(){
        return this._business_image;
    }
     
    public void setBusinessImage(byte[] business_image){
        this._business_image = business_image;
    }
     
    public String getBusinessName(){
        return this._business_name;
    }
     
    public void setBusinessName(String business_name){
        this._business_name = business_name;
    }
     
    public String getPhone(){
        return this._phone;
    }
     
    public void setPhone(String phone){
        this._phone = phone;
    }
    
    public String getAddress(){
        return this._address;
    }
     
    public void setAddress(String address){
        this._address = address;
    }
    
    public String getEmail(){
        return this._email;
    }
     
    public void setEmail(String email){
        this._email = email;
    }
    
    public String getWebsite(){
        return this._website;
    }
     
    public void setWebsite(String website){
        this._website = website;
    }
    
    public String getSuburb(){
        return this._suburb;
    }
     
    public void setSuburb(String suburb){
        this._suburb = suburb;
    }
    
    public String getPostcode(){
        return this._postcode;
    }
     
    public void setPostcode(String postcode){
        this._postcode = postcode;
    }
    
    public String getDescription(){
        return this._description;
    }
     
    public void setDescription(String description){
        this._description = description;
    }
    
    public String getFullDescription(){
        return this._full_description;
    }
     
    public void setFullDescription(String full_description){
        this._full_description = full_description;
    }
}
