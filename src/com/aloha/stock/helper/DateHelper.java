package com.aloha.stock.helper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.util.Log;

public class DateHelper {
	private final boolean debug = false;
	private static final int NEW_DATA_HOUR = 15;
	private int mMonth;
	private int mDay;
	private int mYear;
	private int mday;
	public DateHelper()
	{
		Calendar cal = Calendar.getInstance();
		mMonth = cal.get(Calendar.MONTH) + 1;
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		mYear = cal.get(Calendar.YEAR);
		mday = cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public DateHelper(DateHelper dh)
	{
		mMonth = dh.getMonth();
		mDay = dh.getDay();
		mYear = dh.getYear();
		mday = dh.getWeekDay();
	}
	
	public int getDay()
	{
		return mDay;
	}
	
	public int getMonth()
	{
		return mMonth;
	}
	
	public int getYear()
	{
		return mYear;
	}
	
	public int getWeekDay()
	{
		return mday;
	}
	
	public String gotoPreDate()
	{
		Calendar cal = new GregorianCalendar(mYear, mMonth - 1, mDay);
		cal.add(Calendar.DATE, -1);
		mMonth = cal.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", mMonth);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d",mDay);
		mYear = cal.get(Calendar.YEAR);
		mday = cal.get(Calendar.DAY_OF_WEEK);
		if (debug) Log.d("DateHelper", "" + mMonth + "/" + mDay + "/" + mYear + " date of week:" + cal.get(Calendar.DAY_OF_WEEK));
		return "" + mYear + strMonth + strDate;
	}
	
	public String getPreDate(int n)
	{
		Calendar cal = new GregorianCalendar(mYear, mMonth - 1, mDay);
		cal.add(Calendar.DATE, - n);
		int month = cal.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", month);
		int date = cal.get(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d", date);
		int year = cal.get(Calendar.YEAR);
		if (debug) Log.d("DateHelper", "" + month + "/" + date + "/" + year + " date of week:" + cal.get(Calendar.DAY_OF_WEEK));
		return "" + year + strMonth + strDate;
	}
	
	public String getNextDate(int n)
	{
		Calendar cal = new GregorianCalendar(mYear, mMonth - 1, mDay);
		cal.add(Calendar.DATE, - n);
		int month = cal.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", month);
		int date = cal.get(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d", date);
		int year = cal.get(Calendar.YEAR);
		if (debug) Log.d("DateHelper", "" + month + "/" + date + "/" + year + " date of week:" + cal.get(Calendar.DAY_OF_WEEK));
		return "" + year + strMonth + strDate;
	}
	
	public String gotoPreDateN(int n)
	{
		Log.d("DateHelper",""+this.toString());
		if(this.toString().equals("20141229") && n == 3)
		{
			n = 2;
		}
		if(this.toString().equals("20141228") && n == 2)
		{
			n = 1;
		}
		Log.d("DateHelper","n "+ n);
			
		Calendar cal = new GregorianCalendar(mYear, mMonth - 1, mDay);
		cal.add(Calendar.DATE, - n);
		mMonth = cal.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", mMonth);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d",mDay);
		mYear = cal.get(Calendar.YEAR);
		mday = cal.get(Calendar.DAY_OF_WEEK);
		if (debug) Log.d("DateHelper", "" + strMonth + "/" + strDate + "/" + mYear + " date of week:" + cal.get(Calendar.DAY_OF_WEEK));

		return "" + mYear + strMonth + strDate;
	}
	
	
	public String gotoNextDateN(int n)
	{
		if(this.toString().equals("20141226") && n == 3)
		{
			n = 1;
		}
		
		Calendar cal = new GregorianCalendar(mYear, mMonth - 1, mDay);
		cal.add(Calendar.DATE, + n);
		mMonth = cal.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", mMonth);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d",mDay);
		mYear = cal.get(Calendar.YEAR);
		mday = cal.get(Calendar.DAY_OF_WEEK);
		if (debug) Log.d("DateHelper", "" + strMonth + "/" + strDate + "/" + mYear + " date of week:" + cal.get(Calendar.DAY_OF_WEEK));
		return "" + mYear + strMonth + strDate;
	}
	
	public String gotoNextDate()
	{
		Calendar cal = new GregorianCalendar(mYear, mMonth - 1, mDay);
		cal.add(Calendar.DATE, +1);
		mMonth = cal.get(Calendar.MONTH) + 1;
		String strMonth = String.format("%02d", mMonth);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		String strDate = String.format("%02d",mDay);
		mYear = cal.get(Calendar.YEAR);
		mday = cal.get(Calendar.DAY_OF_WEEK);
		if (debug) Log.d("DateHelper", "" + mMonth + "/" + mDay + "/" + mYear + " date of week:" + cal.get(Calendar.DAY_OF_WEEK));
		return String.valueOf(mYear) + strMonth + strDate;
	}
	
	public String toString()
	{
		String strMonth = String.format("%02d", mMonth);
		String strDate = String.format("%02d", mDay);
		return String.valueOf(mYear) + strMonth + strDate;
	}
	
	public String toDisplayString()
	{
		String strMonth = String.format("%02d", mMonth);
		String strDate = String.format("%02d", mDay);
		return String.valueOf(mYear) +"/"+ strMonth +"/"+ strDate;
	}
	
	public String toChineseDisplayString()
	{
		String strMonth = String.format("%02d", mMonth);
		String strDate = String.format("%02d", mDay);
		return String.valueOf(mYear - 1911) +"/"+ strMonth +"/"+ strDate;
	}

	public boolean hasNewData()
	{
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (debug) Log.d("LAI","day:" + day + ", hour:" + hour);
		
		if(day != 1 && day != 7 && hour >= NEW_DATA_HOUR)
		{
			return true;//use today data, please download
		}
		return false;//use previous day data
	}
	
	public void gotoPreviousValidDay()
	{
		if (mday >= 3 && mday <= 7)
		{
			gotoPreDate();
		}
		else if (mday == 2)
		{
			gotoPreDateN(3);
		}
		else if (mday == 1)
		{
			gotoPreDateN(2);
		}
	}
	
	
	public void gotoNextValidDay()
	{
		if (mday >= 1 && mday <= 5)
		{
			gotoNextDate();
		}
		else if (mday == 6)
		{
			gotoNextDateN(3);
		}
		else if (mday == 7)
		{
			gotoNextDateN(2);
		}
	}
	
//	public String getPreviousValidDay()
//	{
//		if (mday >= 3 && mday <= 7)
//		{
//			return getPreDate(1);
//		}
//		else if (mday == 2)
//		{
//			return getPreDate(3);
//		}
//		else if (mday == 1)
//		{
//			return getPreDate(2);
//		}
//		return "";
//	}
}
