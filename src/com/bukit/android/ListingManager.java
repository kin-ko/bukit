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

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bukit.android.client.SapiClient;
import com.bukit.android.client.SapiEnvironment;
import com.bukit.android.requestmodel.SearchParams;
import com.bukit.android.responsemodel.Listing;
import com.bukit.android.responsemodel.ListingOpeningHours.Times;
import com.bukit.android.responsemodel.ListingOpeningHours.Day;
import com.bukit.android.responsemodel.ListingContact;
import com.bukit.android.responsemodel.ListingOpeningHoursStatus;
import com.bukit.android.responsemodel.SearchResponse;
import com.bukit.android.responsemodel.ListingOpeningHours;

public class ListingManager {

	public static String GetBusinessOpeningHours(Listing list)
	{
		String strHeading = "<table width='100%'><tr><th colspan='2'>Opening Hours" + System.getProperty ("line.separator") + "</th></tr>";
		String strOH = strHeading;
		ListingOpeningHours loh = list.getOpeningHours();
		
		try
		{
			if (loh != null)
			{
				strOH += "<tr><td>Monday:</td><td>" + GetDayOpeningHours(loh.getMonday()) + "</td></tr>";
				strOH += "<tr><td>Tuesday:</td><td>" + GetDayOpeningHours(loh.getTuesday()) + "</td></tr>";
				strOH += "<tr><td>Wednesday:</td><td>" + GetDayOpeningHours(loh.getWednesday()) + "</td></tr>";
				strOH += "<tr><td>Thursday:</td><td>" + GetDayOpeningHours(loh.getThursday()) + "</td></tr>";
				strOH += "<tr><td>Friday:</td><td>" + GetDayOpeningHours(loh.getFriday()) + "</td></tr>";
				strOH += "<tr><td>Saturday:</td><td>" + GetDayOpeningHours(loh.getSaturday()) + "</td></tr>";
				strOH += "<tr><td>Sunday:</td><td>" + GetDayOpeningHours(loh.getSunday()) + "</td></tr>";
				strOH += "<tr><td>Public Holidays:</td><td>" + GetDayOpeningHours(loh.getPublicHolidays()) + "</td></tr>";
			}
		}
		catch (Exception e)
		{
			//leave it for now
		}
		
		if (strOH.equals(strHeading))
			strOH = "";
		else
			strOH += "</table>";
		
		return strOH;
	}
	
	private static String GetDayOpeningHours(Day d)
	{
		String s = "";
		
		switch (d.getStatus())
		{
			case USE_TIMES:
				List<Times> lstTimes = d.getTimes();	
				
				for (Times t : lstTimes)
				{
					s = s + t.getOpen() + "-" + t.getClose() + "   ";
				}
				break;
			case BY_APPOINTMENT:
				s += "By Appointment";
				break;
			case CLOSED:
				s += "Closed";
				break;
			case TWENTY_FOUR_HOURS:
				s += "24 Hours";
				break;
			default:
				s += "N/A";
				break;
		}

		return s;
		
	}
}
