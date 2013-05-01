package com.example.internationaltimezonecoverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeZoneConverter extends Activity {
	public static final String DATE_FORMAT = "yyyy-MM-dd' 'hh:mm' 'Z";
	public static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd' 'hh:mm' 'Z";
	private ArrayList<String> timeZoneIDs = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_zone);

		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		String[] TZ = TimeZone.getAvailableIDs();
		ArrayList<String> TZ1 = new ArrayList<String>();
		for (int i = 0; i < TZ.length; i++) {
			if (!(TZ1.contains(TimeZone.getTimeZone(TZ[i]).getDisplayName()))) {
				TZ1.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
				timeZoneIDs.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
			}
		}
		for (int i = 0; i < TZ1.size(); i++) {
			adapter.add(TZ1.get(i));
		}
		final Spinner TZone = (Spinner) findViewById(R.id.time_zone);
		TZone.setAdapter(adapter);
		for (int i = 0; i < TZ1.size(); i++) {
			if (TZ1.get(i).equals(TimeZone.getDefault().getDisplayName())) {
				TZone.setSelection(i);
			}
		}
		
		findViewById(R.id.convert).setOnClickListener(convert);
	}

	@SuppressLint("SimpleDateFormat")
	public String ChangeTimeZone(Date inputeDateTime) {
		// output date specification of the format of the date
		DateFormat formater = new SimpleDateFormat(OUTPUT_DATE_FORMAT);
		// get an instance of the user calendar
		Calendar calendar = Calendar.getInstance();
		// get the user's timezone
		String timezone = calendar.getTimeZone().getID();
		// change the date to the user's timezone date
		formater.setTimeZone(TimeZone.getTimeZone(timezone));
		String newDateTime = formater.format(inputeDateTime);
		return newDateTime;
	}

	@SuppressLint("SimpleDateFormat")
	public Date getDateFromString(String inputDateTime) { // Input date you have
															// to specify the
															// time zone of the
															// input date
		DateFormat formater = new SimpleDateFormat(DATE_FORMAT);
		Date inputDate = null;
		try {
			inputDate = formater.parse(inputDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputDate;
	}

	private OnClickListener convert = new OnClickListener() {

		@Override
		public void onClick(View v) {
			DatePicker date = (DatePicker) findViewById(R.id.date);
			TimePicker time = (TimePicker) findViewById(R.id.time);
			Spinner timeZone = (Spinner) findViewById(R.id.time_zone);
			TextView outputDateTime = (TextView) findViewById(R.id.output_date_time);
			
			String dateTime = "";
			dateTime += date.getYear() + "-";
			int month = date.getMonth() + 1;
			if (month < 10) {
				dateTime += "0";
			}
			dateTime += month + "-";
			int day = date.getDayOfMonth();
			if (day < 10) {
				dateTime += "0";
			}
			dateTime += day;
			dateTime += " ";
			int hour = time.getCurrentHour();
			if (hour < 10) {
				dateTime += "0";
			}
			dateTime += hour;
			dateTime += ":";
			int minute = time.getCurrentMinute();
			if (minute < 10) {
				dateTime += "0";
			}
			dateTime += minute;
			dateTime += " ";
			
			String timezoneID = timeZoneIDs.get(timeZone.getSelectedItemPosition());
//			String timezone = TimeZone.getTimeZone(timezoneID).getDisplayName();
			dateTime += timezoneID;
			Log.i("dateTime", "t" + dateTime);
			Date outDate = getDateFromString(dateTime);
			String out = ChangeTimeZone(outDate);
			Log.i("dateTime out", "t" + out);
			outputDateTime.setText(out);
			
		}
	};
}
