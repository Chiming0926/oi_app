package com.aloha.stock;

import java.util.ArrayList;
import java.util.Locale;

import com.aloha.stock.helper.DateHelper;
import com.aloha.stock.helper.Utils;

//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private final static boolean debug = false;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	static Activity _activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_activity = this;
		// Set up the action bar.
		// Set up the action bar.
        final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		mUtils = new Utils(this);
		mDM = new DataManager(this);
	}
	private static DataManager mDM;
	public static Utils mUtils;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 4 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			case 3:
				return getString(R.string.title_section4);
			case 4:
				return getString(R.string.title_section5);
			case 5:
				return "APP說明";
			}
			return null;
		}
	}
	static String major_r1[] = new String[4];
	static String major_r2[] = new String[4];
	static String major_r3[] = new String[4];
	static String major_r4[] = new String[2];
	static String major2_r1[] = new String[4];
	static String major2_r2[] = new String[4];
	static String major2_r3[] = new String[4];
	static String major2_r4[] = new String[2];

	//
	private static TableLayout tableP31;//店頭市場
	private static TableLayout tableP32;//資券餘額
	private static TableLayout tableT86;//stock table
	private static TableLayout tableBFI82U;
	private static TextView dateP3;
	private static TextView mLoadTitleP3;
	private static TextView mTvStatus;
	//future 
	private static TableLayout mFuTable;//feature table
	private static TextView mFuDate;
	private static TextView mFuStatus;
	
	//Option
	private static TableLayout mOptTable1;//option table
	private static TableLayout mOptTable2;//
	private static TableLayout mOptTable3;//
	private static TableLayout mOptTable4;//table_txo_buysell
	private static TextView mOptStatus;
	private static Spinner mOptSpinner;
	private static TextView mOptDate;
	private static TextView pc;
	private static ImageView pcImg;
	
	//big
	private static TableLayout mBig5;
	private static TableLayout mBig10;
	private static TableLayout mBigCall5;
	private static TableLayout mBigPut5;
	private static TableLayout mBigCall10;
	private static TableLayout mBigPut10;
	private static TextView mBigDateTV;
	private static TextView mBigStatusTV;
	private static TableLayout mTradeATL;
	private static TableLayout mTradeBTL;
	private static TableLayout mTradeCTL;
	private static TextView mTailTV;
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
		
		int pageNumber = 0;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = null;
			pageNumber = getArguments().getInt(ARG_SECTION_NUMBER);
			if (pageNumber == 1)
			{
				rootView = inflater.inflate(R.layout.p1, container,
						false);
				mOptTable1 = (TableLayout) rootView.findViewById(R.id.table);	
				mOptSpinner = (Spinner) rootView.findViewById(R.id.spinnner);
				mOptDate = (TextView) rootView.findViewById(R.id.date);
				pc = (TextView) rootView.findViewById(R.id.p_c);
				pcImg = (ImageView) rootView.findViewById(R.id.p_c_img);
				mOptStatus = (TextView) rootView.findViewById(R.id.status);//show loading or no data
				mOptTable2 = (TableLayout) rootView.findViewById(R.id.table_txo_buy);
				mOptTable3 = (TableLayout) rootView.findViewById(R.id.table_txo_sell);
				mOptTable4 = (TableLayout) rootView.findViewById(R.id.table_txo_buysell);
				Button btn = (Button) rootView.findViewById(R.id.adb_btn);
				btn.setText(Html.fromHtml("<font color=#000099>誠徵 期貨選擇權 客人</font> <br> <font color=#000000>開戶下單送 32G 隨身碟</font>"));
				btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 26);
				mOptionDate = new DateHelper();//get current date
				new OptionTask().execute(0);
			}
			else if (pageNumber == 2)
			{
				rootView = inflater.inflate(R.layout.p2, container,
						false);
				mFuTable = (TableLayout) rootView.findViewById(R.id.future_table);
				mFuDate = (TextView) rootView.findViewById(R.id.date);
				mFuStatus = (TextView) rootView.findViewById(R.id.no_data);
				mFutureDate = new DateHelper();
				new FutureTask().execute(0);
				Button btn = (Button) rootView.findViewById(R.id.adb_btn);
				btn.setText(Html.fromHtml("<font color=#000099>誠徵 期貨選擇權 客人</font> <br> <font color=#000000>開戶下單送 32G 隨身碟</font>"));
				btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 26);
			}
			else if (pageNumber == 3)
			{
				rootView = inflater.inflate(R.layout.p3, container,
						false);
				dateP3 = (TextView) rootView.findViewById(R.id.date);
				tableP31 = (TableLayout) rootView.findViewById(R.id.tableP31);
				tableP32 = (TableLayout) rootView.findViewById(R.id.tableP32);
				tableT86 = (TableLayout) rootView.findViewById(R.id.tableT86);
				tableBFI82U = (TableLayout) rootView.findViewById(R.id.tableBFI82U);
				mLoadTitleP3 = (TextView) rootView.findViewById(R.id.loading_title);
				mTvStatus =(TextView) rootView.findViewById(R.id.status);
				mBFI82UDate = new DateHelper();
				
				tableBFI82U.setVisibility(View.GONE);
				tableT86.setVisibility(View.GONE);
				tableP31.setVisibility(View.GONE);
				tableP32.setVisibility(View.GONE);
				new BFI82UTask().execute(0);
				new GetStockTask().execute();
			}
			else if (pageNumber == 4)
			{
				rootView = inflater.inflate(R.layout.p4, container,	false);
				mBigDate = new DateHelper();
				mBigDateTV = (TextView) rootView.findViewById(R.id.date);
				mBigStatusTV = (TextView) rootView.findViewById(R.id.status);
				mBig5 = (TableLayout) rootView.findViewById(R.id.table_first_five);
				mBig10 = (TableLayout) rootView.findViewById(R.id.table_first_ten);
				mBigCall5 = (TableLayout) rootView.findViewById(R.id.table_call_5);
				mBigPut5 = (TableLayout) rootView.findViewById(R.id.table_put_5);
				mBigCall10 = (TableLayout) rootView.findViewById(R.id.table_call_10);
				mBigPut10 = (TableLayout) rootView.findViewById(R.id.table_put_10);
				
				mTradeATL = (TableLayout) rootView.findViewById(R.id.table_trad_1);
				mTradeBTL = (TableLayout) rootView.findViewById(R.id.table_trad_2);
				mTradeCTL = (TableLayout) rootView.findViewById(R.id.table_trad_3);
				
				mTailTV = (TextView) rootView.findViewById(R.id.tailTV);
				
				new BigTask().execute(0);
			}
			else if (pageNumber == 5){
				rootView = inflater.inflate(R.layout.market, container,
						false);
			}
			else
			{
				rootView = inflater.inflate(R.layout.p5, container,	false);
				/*
				rootView.findViewById(R.id.call_me).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:0935142214" ));
						startActivity(intent);
					}
					
				});
				*/
				
			}
			return rootView;
		}

		private class GetStockTask extends AsyncTask<Void, Void, Void> {
			Stock stock;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				stock =  (Stock)mDM.getData("STOCK", null);
				if (stock == null)
					Log.e("", "@@@@@@@@@@@ stock null");
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				tableP31.removeAllViews();
				tableP32.removeAllViews();
				if (stock == null)
				{
					Log.e("", "@@@@@@@@@@@ 111");
					return;
				}
				Log.e("", "@@@@@@@@@@@ 222");
				//店頭市場
		        addRowP3(_activity,
		        		_activity.getString(R.string.data_date) +
		        		stock.year + "/" + stock.month + "/" +
		        				stock.date + "\n店頭市場", tableP31, 4);
		        String array[] = new String[]{
						_activity.getString(R.string.major_r_0),
						_activity.getString(R.string.major_r_1),
						_activity.getString(R.string.major_r_2),
						_activity.getString(R.string.major_r_3),
				};
		        addRowTitleP3(_activity, array, tableP31);
		        tableP31.setVisibility(View.VISIBLE);
		        
		        stock.major2_r[0][0] = _activity.getResources().getString(R.string.major_c_1);
		        stock.major2_r[1][0] = _activity.getResources().getString(R.string.major_c_2);
		        stock.major2_r[2][0] = _activity.getResources().getString(R.string.major_c_3);
		        stock.major2_r[3][0] = _activity.getResources().getString(R.string.major_c_4);

		        addRowColorP3(_activity, stock.major2_r[0], tableP31, 0);
		        addRowColorP3(_activity, stock.major2_r[1], tableP31, 0);
		        addRowColorP3(_activity, stock.major2_r[2], tableP31, 0);
		        addRowColorP3(_activity, stock.major2_r[3], tableP31, 0);
		        
		        //資券餘額
		        addRowP3(_activity, R.string.credit_title_1, tableP32, 7);
		        
		        String array2[] = new String[]{"",
		        		_activity.getString(R.string.credit_r_0),
		        		_activity.getString(R.string.credit_r_1),
		        		_activity.getString(R.string.credit_r_2),
				};
		        addRowTitleP32(_activity, array2, tableP32);
		        addRowForTable21(_activity);
		        addRows(_activity, stock.credit, tableP32);
		        tableP32.setVisibility(View.VISIBLE);
				super.onPostExecute(result);
			}
		}
	}
	
	//for 股票: 法人 買進 賣出 買賣超 or "" 融資 融券 當沖
	public static void addRowTitle(Context context,String array[], TableLayout table)
	{
		TableRow row= new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
        	
        	if (i != 0)
        	{
        		TableRow.LayoutParams params = new TableRow.LayoutParams();
        		params.span = 2;
        		params.gravity = Gravity.LEFT;
        		tv.setLayoutParams(params);
        	}
	        row.addView(tv);
        }
        row.setBackgroundResource(R.drawable.row_border);
        table.addView(row);
	}
	
	//for 店頭市場
	public static void addRowTitleP3(Context context,String array[], TableLayout table)
	{
        TableRow row = new TableRow(context);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
        	tv.setTextSize(15);
        	tv.setBackgroundResource(R.drawable.row_border7);
        	row.addView(tv);
        }
        table.addView(row);
	}
	
	//for 資券
	public static void addRowTitleP32(Context context,String array[], TableLayout table)
	{
		TableRow row= new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
        	
        	if (i != 0)
        	{
        		TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        		params.weight = 2;
        		tv.setLayoutParams(params);
        	}
        	tv.setBackgroundResource(R.drawable.row_border7);
        	
	        row.addView(tv);
        }
//        row.setBackgroundResource(R.drawable.row_border);
        table.addView(row);
	}
	
	//for table2
	public static void addRowForTable21(Context context)
	{
		int array[] = {R.string.credit_r_10,
						R.string.credit_r_11,
						R.string.credit_r_12,
						R.string.credit_r_13,
						R.string.credit_r_14,
						R.string.credit_r_15,
						R.string.credit_r_16
		};
		
		TableRow row= new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
        	tv.setTextSize(10);
        	tv.setBackgroundResource(R.drawable.row_border3);
	        row.addView(tv);
        }
        tableP32.addView(row);
	}
	
	public static void createBFI82UTable(Context context, BFI82U bfi82u)
	{
		tableBFI82U.removeAllViews();
		
		TableRow row = new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(context);
    	tv.setText("三大法人買賣金額統計表");
    	tv.setTextSize(15);
    	lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
    	lp.span = 4;
    	lp.gravity = Gravity.CENTER;
    	tv.setLayoutParams(lp);
        row.addView(tv);
        row.setBackgroundResource(R.drawable.row_border8);
        tableBFI82U.addView(row);
        
        String array[] = {"單位名稱","買進金額","賣出金額","買賣差額"};
        TableRow rowA = new TableRow(context);
        for (int i = 0; i < array.length ; i++)
        {
        	tv = new TextView(context);
        	tv.setText(array[i]);
        	tv.setTextSize(15);
        	tv.setBackgroundResource(R.drawable.row_border7);
        	rowA.addView(tv);
        }
        tableBFI82U.addView(rowA);
        
        String arrayB[] = {"自營商(自行買賣)","自營商(避險)","投信","外資及陸資","合計"};
        for (int i = 0; i < 5 ; i++)
        {
        	row = new TableRow(context);
        	tv = new TextView(context);
        	tv.setText(arrayB[i]);
        	tv.setTextSize(15);
        	tv.setBackgroundResource(R.drawable.row_border3);
	        row.addView(tv);
        	for(int j = 0; j < 3 ; j++)
        	{
            	tv = new TextView(context);
            	if (i == 4 && j == 2)
            	{
            		tv.setText(bfi82u.row[i][j] + "億");
            	}
            	else
            	{
            		tv.setText(bfi82u.row[i][j]);
            	}
            	tv.setTextSize(15);
            	tv.setBackgroundResource(R.drawable.row_border3);
            	if (bfi82u.row[i][j].startsWith("-"))
				{
            		tv.setTextColor(0xFF3CBD00);
				}
    	        row.addView(tv);
        	}
        	tableBFI82U.addView(row);
        }
        
        tableBFI82U.setVisibility(View.VISIBLE);
	}
	
	public static void createT86Table(Context context, T86 t86)
	{
		/*
		tableT86.removeAllViews();
		
		TableRow row = new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        TextView tv = new TextView(context);
    	tv.setText("買超前20名");
    	tv.setTextSize(15);
    	lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
    	lp.span = 11;
    	lp.gravity = Gravity.CENTER;
    	tv.setLayoutParams(lp);
        row.addView(tv);
        row.setBackgroundResource(R.drawable.row_border8);
        tableT86.addView(row);

		String array[] = {"證券\n代號\n\n","證券\n名稱\n\n","外資\n買進\n\n","外資\n賣出\n\n",
				"投信\n買進\n\n","投信\n賣出\n\n","自營商\n買進\n(自行\n買賣)","自營商\n賣出\n(自行\n買賣)",
				"自營商\n買進\n(避險)\n","自營商\n賣出\n(避險)\n","三大法人\n買賣超\n\n"
		};
		
		TableRow rowA = new TableRow(context);
		lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
		rowA.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	tv = new TextView(context);
        	tv.setText(array[i]);
        	tv.setTextSize(10);
        	tv.setBackgroundResource(R.drawable.row_border7);
        	rowA.addView(tv);
        }
        tableT86.addView(rowA);

        for (int i = 0; i < 20 ; i++)
        {
        	row = new TableRow(context);
        	for(int j = 0; j < 11 ; j++)
        	{
            	tv = new TextView(context);
            	tv.setText(t86.row[i][j]);
            	tv.setTextSize(10);
            	tv.setBackgroundResource(R.drawable.row_border3);
            	//if (t86.row[i][j].startsWith("-"))
				//{
            	//	tv.setTextColor(0xFF3CBD00);
				//}
    	        row.addView(tv);
        	}
        	tableT86.addView(row);
        }
        
		row = new TableRow(context);
		lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        tv = new TextView(context);
    	tv.setText("賣超前20名");
    	tv.setTextSize(15);
    	lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
    	lp.span = 11;
    	lp.gravity = Gravity.CENTER;
    	tv.setLayoutParams(lp);
        row.addView(tv);
        row.setBackgroundResource(R.drawable.row_border8);
        tableT86.addView(row);
        
        rowA = new TableRow(context);
		lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
		rowA.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	tv = new TextView(context);
        	tv.setText(array[i]);
        	tv.setTextSize(10);
        	tv.setBackgroundResource(R.drawable.row_border7);
        	rowA.addView(tv);
        }
        tableT86.addView(rowA);
        
        for (int i = 20; i < 40 ; i++)
        {
        	row = new TableRow(context);
        	for(int j = 0; j < 11 ; j++)
        	{
            	tv = new TextView(context);
            	tv.setText(t86.row[i][j]);
            	tv.setTextSize(10);
            	tv.setBackgroundResource(R.drawable.row_border3);
            	//if (t86.row[i][j].startsWith("-"))
				//{
            	//	tv.setTextColor(0xFF3CBD00);
				//}
    	        row.addView(tv);
        	}
        	tableT86.addView(row);
        }
        
        tableT86.setVisibility(View.VISIBLE);*/
	}
	
	//one column with yellow background
	public static void addRowP3(Context context, int id, TableLayout table, int span)
	{
		
		addRowP3( context, context.getString(id), table, span);
	}
	
	public static void addRowP3(Context context, String str, TableLayout table, int span)
	{
		TableRow row2= new TableRow(_activity);
		row2.setBackgroundColor(0xffFFF9B8);
		TextView tv = new TextView(_activity);
        tv.setText(str);
        tv.setGravity(Gravity.CENTER);
		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = span;
		tv.setLayoutParams(params);
        row2.addView(tv);
        row2.setBackgroundResource(R.drawable.row_border8);
        table.addView(row2);
	}
	
	//one column with 
	public static void addRow(Context context, String str, TableLayout table)
	{
		TableRow row2= new TableRow(_activity);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
		row2.setLayoutParams(lp);
		row2.setGravity(Gravity.CENTER_HORIZONTAL);
		row2.setBackgroundColor(0xffFFF9B8);
		TextView title2 = new TextView(_activity);
        title2.setText(str);
        title2.setGravity(Gravity.LEFT);
        row2.addView(title2);
        table.addView(row2);
	}
	
	//for table2
	public static void addRows(Context context, String elements[][], TableLayout table)
	{
		 for (int i = 0; i < 7 ; i++)
		 {
			 TableRow row= new TableRow(context);
			 for (int j = 0; j < 7 ; j ++)
			 {
				 TextView tv = new TextView(context);
				 tv.setText(elements[i][j]);
				 if (elements[i][j].startsWith("-"))
				 {
					 tv.setTextColor(0xFF3CBD00);
				 }
				 tv.setBackgroundResource(R.drawable.row_border3);
				 tv.setTextSize(10);
				 row.addView(tv);
			 }
	        if(i%2 == 0)
	        {
	        	row.setBackgroundResource(R.drawable.row_border);
	        }
			 table.addView(row);
		 }
	}
	static int selectedIndex = 0;
	static ArrayList<String> mOptionDateList = new ArrayList<String>();
	public static void parseTxoDate(final Txo txo)
	{
		mOptionDateList.clear();
		mOptTable1.setVisibility(View.VISIBLE);
		mOptStatus.setVisibility(View.GONE);

		try
		{
			if (txo.buylist.size() == 0)
			{
				showNoData();
				return;
			}

			for(_txo o: txo.buylist)
			{
				boolean b = false;
				for(String s: mOptionDateList)
				{
					if(s.equals(o.date))
					{
						b = true;
					}
				}
				if (b == false)
					mOptionDateList.add(o.date);
			}

			pc.setText("P/C Ratio:" + String.format("%.02f", txo.pc));
			if (txo.pc >= 1)
			{
				pcImg.setImageResource(R.drawable.arrow_up);
			}
			else
			{
				pcImg.setImageResource(R.drawable.arrow_down);
			}
			
			selectedIndex = 0;
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(_activity.getApplicationContext() ,/*android.R.layout.simple_spinner_item*/R.layout.my_spinner, mOptionDateList);
			adapter.setDropDownViewResource(/*android.R.layout.simple_spinner_dropdown_item */ R.layout.my_spinner);
			mOptSpinner.setAdapter(adapter);
			mOptSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					selectedIndex = position;
					if (debug) Log.d("LAI","selectedIndex:" + selectedIndex);
					updateTXOTable(_activity, txo, mOptTable1);
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
	
			});
		}catch(Exception e){
			showNoData();
		}
		
		/*
		 * get second table
		 */
		new OptionNewTask().execute(0);
	}
	
	private static void showNoData()
	{
		mOptTable1.removeAllViews();
		mOptTable1.setVisibility(View.GONE);
		mOptTable2.setVisibility(View.GONE);
		mOptTable3.setVisibility(View.GONE);
		mOptTable4.setVisibility(View.GONE);
		mOptStatus.setText(R.string.empty_data);
		mOptStatus.setVisibility(View.VISIBLE);
		mOptionDateList.clear();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(_activity.getApplicationContext() ,/*android.R.layout.simple_spinner_item*/R.layout.my_spinner, mOptionDateList);
		mOptSpinner.setAdapter(adapter);
		mOptSpinner.setOnItemSelectedListener(null);
	}
	
	public static void updateFutureTable(Context context, Future f , TableLayout table)
	{
		if (debug) Log.d("LAI","+updateFutureTable()");
		table.removeAllViews();
		String array[] = {"自營商","投信","外資"};
		String array1[] = {"臺指期","電子期","金融期","小臺指","台灣50","股票期","ETF期貨","櫃買期","非金電","東證期貨","期貨小計"};
		String arrayT[] = {"本日淨交易(口)","淨未平倉(口)"};
//		mFuDate.setText(mFutureDate.toDisplayString());
		
		if (f == null || f.data.size() == 0)
		{
			mFuStatus.setVisibility(View.VISIBLE);
			mFuStatus.setText(R.string.empty_data);
			mFuTable.setVisibility(View.GONE);
			return;
		}
		int i = 0, j = 0;
        for (_future future:f.data)
        {
        	if (j > 10)
        		break;
        	if (i == 3)
        		i = 0;
        	if (i == 0)
        	{
        		TableRow row = new TableRow(context);
        		TextView tv = new TextView(context);
        		
            	tv.setText(array1[j++]);
            	row.addView(tv);
            	
            	tv = new TextView(context);
            	tv.setText(arrayT[0]);
            	row.addView(tv);
            	
            	tv = new TextView(context);
            	tv.setText(arrayT[1]);
            	row.addView(tv);
//            	row.setBackgroundResource(R.drawable.row_border);
            	row.setBackgroundColor(0xffD0E0F0);
            	table.addView(row);
        	}
        	
        	TableRow row = new TableRow(context);
    		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            
        	TextView tv = new TextView(context);
        	tv.setText(array[i++]);
        	row.addView(tv);
        	
        	tv = new TextView(context);
        	tv.setText(future.a);
        	if (future.a.startsWith("-"))
        	{
        		tv.setTextColor(0xFF3CBD00);
        	}
        	row.addView(tv);
        	
        	tv = new TextView(context);
        	tv.setText(future.b);
        	if (future.b.startsWith("-"))
        	{
        		tv.setTextColor(0xFF3CBD00);
        	}
        	row.addView(tv);
        	
        	if(i%2 == 1)
	        {
	        	row.setBackgroundResource(R.drawable.row_border);
	        }

        	table.addView(row);
        }
        
        //total
        
        TableRow row = new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        
    	TextView tv = new TextView(context);
    	tv.setText("期貨合計");
    	row.addView(tv);
    	
    	tv = new TextView(context);
    	tv.setText(f.total1);
    	if (f.total1.startsWith("-"))
    	{
    		tv.setTextColor(0xFF3CBD00);
    	}
    	row.addView(tv);
    	
    	tv = new TextView(context);
    	tv.setText(f.total2);
    	if (f.total2.startsWith("-"))
    	{
    		tv.setTextColor(0xFF3CBD00);
    	}
    	row.addView(tv);
    	
    	table.addView(row);
        
        table.setVisibility(View.VISIBLE);
        mFuStatus.setVisibility(View.GONE);
		if (debug) Log.d("LAI","updateFutureTable()");
	}
	
	public static void updateBigTable(Context context, Big big)
	{
		if (mBig5.getChildCount() > 4)
		{
			mBig5.removeViews(4, 3);
		}
		
		if (mBig10.getChildCount() > 4)
		{
			mBig10.removeViews(4, 3);
		}
		
		if (mBigCall5.getChildCount() > 4)
		{
			mBigCall5.removeViews(4, 3);
		}
		
		if (mBigCall10.getChildCount() > 4)
		{
			mBigCall10.removeViews(4, 3);
		}

		if (mBigPut5.getChildCount() > 4)
		{
			mBigPut5.removeViews(4, 3);
		}

		if (mBigPut10.getChildCount() > 4)
		{
			mBigPut10.removeViews(4, 3);
		}

		if (mTradeATL.getChildCount() > 2)
		{
			mTradeATL.removeViews(2, 2);
		}
		
		if (mTradeBTL.getChildCount() > 2)
		{
			mTradeBTL.removeViews(2, 2);
		}
		
		if (mTradeCTL.getChildCount() > 2)
		{
			mTradeCTL.removeViews(2, 2);
		}
		
		if (big == null)
		{
			mBigStatusTV.setVisibility(View.VISIBLE);
			mBigStatusTV.setText(R.string.empty_data);
			return;
		}
		
		{
			TableRow.LayoutParams params = new TableRow.LayoutParams();
			TextView tv = new TextView(context);
			TableRow rowTA = new TableRow(context);
			TableRow rowTB = new TableRow(context);
	    	tv = new TextView(context);
	        tv.setText("多方");
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);
	        
	    	tv = new TextView(context);
	        tv.setText(big.rowData.get(1).text[1]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(1).text[3]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);

	        mTradeATL.addView(rowTA);
	        
	    	tv = new TextView(context);
	        tv.setText("空方");
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        
	    	tv = new TextView(context);
	        tv.setText(big.rowData.get(1).text[5]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(1).text[7]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        rowTB.setBackgroundResource(R.drawable.row_border6);
	        mTradeATL.addView(rowTB);
		}
		
		{
			TableRow.LayoutParams params = new TableRow.LayoutParams();
			TextView tv = new TextView(context);
			TableRow rowTA = new TableRow(context);
			TableRow rowTB = new TableRow(context);
	    	tv = new TextView(context);
	        tv.setText("買方");
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);
	        
	    	tv = new TextView(context);
	        tv.setText(big.rowData.get(4).text[1]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(7).text[1]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);

	        mTradeBTL.addView(rowTA);
	        
	    	tv = new TextView(context);
	        tv.setText("賣方");
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        
	    	tv = new TextView(context);
	        tv.setText(big.rowData.get(4).text[5]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(7).text[5]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        rowTB.setBackgroundResource(R.drawable.row_border6);
	        mTradeBTL.addView(rowTB);
		}
		
		{
			TableRow.LayoutParams params = new TableRow.LayoutParams();
			TextView tv = new TextView(context);
			TableRow rowTA = new TableRow(context);
			TableRow rowTB = new TableRow(context);
	    	tv = new TextView(context);
	        tv.setText("買方");
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);
	        
	    	tv = new TextView(context);
	        tv.setText(big.rowData.get(4).text[3]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(7).text[3]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTA.addView(tv);

	        mTradeCTL.addView(rowTA);
	        
	    	tv = new TextView(context);
	        tv.setText("賣方");
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        
	    	tv = new TextView(context);
	        tv.setText(big.rowData.get(4).text[7]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(7).text[7]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        rowTB.addView(tv);
	        rowTB.setBackgroundResource(R.drawable.row_border6);
	        mTradeCTL.addView(rowTB);
		}
		
		for (int i = 0; i < 9 ; i ++)
        {
			TableRow row = new TableRow(context);
			TableRow row2 = new TableRow(context);
			TextView tv = new TextView(context);
			
	        tv.setText(big.rowData.get(i).text[0]);
	        tv.setTextSize(13);
	        TableRow.LayoutParams params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[1]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row.addView(tv);

	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[2]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[5]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[6]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[9]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[0]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row2.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[3]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row2.addView(tv);

	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[4]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row2.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[7]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row2.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[8]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row2.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(big.rowData.get(i).text[9]);
	        tv.setTextSize(13);
	        params = new TableRow.LayoutParams();
			params.gravity = Gravity.CENTER;
    		tv.setLayoutParams(params);
	        row2.addView(tv);
	        
	        if (i < 3)
	        {
		        if(i%2 == 0)
		        {
		        	row.setBackgroundResource(R.drawable.row_border6);
		        	row2.setBackgroundResource(R.drawable.row_border6);
		        }
	        	mBig5.addView(row);
	        	mBig10.addView(row2);
	        }
	        else if (i >= 3 && i < 6)
	        {
		        if(i%2 == 1)
		        {
		        	row.setBackgroundResource(R.drawable.row_border6);
		        	row2.setBackgroundResource(R.drawable.row_border6);
		        }
	        	mBigCall5.addView(row);
	        	mBigCall10.addView(row2);
	        }
	        else
	        {
		        if(i%2 == 0)
		        {
		        	row.setBackgroundResource(R.drawable.row_border6);
		        	row2.setBackgroundResource(R.drawable.row_border6);
		        }
	        	mBigPut5.addView(row);
	        	mBigPut10.addView(row2);
	        }
        }
		mBig5.setVisibility(View.VISIBLE);
        mBig10.setVisibility(View.VISIBLE);
        mBigCall5.setVisibility(View.VISIBLE);
        mBigCall10.setVisibility(View.VISIBLE);
        mBigPut5.setVisibility(View.VISIBLE);
        mBigPut10.setVisibility(View.VISIBLE);
        mTradeATL.setVisibility(View.VISIBLE);
        mTradeBTL.setVisibility(View.VISIBLE);
        mTradeCTL.setVisibility(View.VISIBLE);
        mTailTV.setVisibility(View.VISIBLE);
	}
	public static void updateTXONewTable(Context context, TxoNew txo)
	{
		if (mOptTable2.getChildCount() > 2)
		{
			mOptTable2.removeViews(2, 3);
		}
		
		if (mOptTable3.getChildCount() > 2)
		{
			mOptTable3.removeViews(2, 3);
		}
		
		if (mOptTable4.getChildCount() > 2)
		{
			mOptTable4.removeViews(2, 4);
		}
	
		if (txo == null)
			return;
		int size = txo.buyTxo.size();
		for (int i = 0; i < size ; i++)
        {
			TableRow row = new TableRow(context);
			TextView tv = new TextView(context);
	        tv.setText(txo.buyTxo.get(i).name);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.buyTxo.get(i).buy);
	        row.addView(tv);

	        tv = new TextView(context);
	        tv.setText(txo.buyTxo.get(i).sell);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.buyTxo.get(i).diff);
	        if (txo.buyTxo.get(i).diff.startsWith("-"))
	        {
	        	tv.setTextColor(0xFF3CBD00);
	        }
	        row.addView(tv);
	        
	        if(i%2 == 0)
	        	row.setBackgroundResource(R.drawable.row_border);
	        
	        mOptTable2.addView(row);
        }
		
		mOptTable2.setVisibility(View.VISIBLE);

		size = txo.sellTxo.size();

		for (int i = 0; i < size ; i++)
        {
			TableRow row = new TableRow(context);
			TextView tv = new TextView(context);
	        tv.setText(txo.sellTxo.get(i).name);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.sellTxo.get(i).buy);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.sellTxo.get(i).sell);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.sellTxo.get(i).diff);
	        if (txo.sellTxo.get(i).diff.startsWith("-"))
	        {
	        	tv.setTextColor(0xFF3CBD00);
	        }
	        row.addView(tv);
	        
	        if(i%2 == 0)
	        	row.setBackgroundResource(R.drawable.row_border);
	        
	        mOptTable3.addView(row);
        }
		
		mOptTable3.setVisibility(View.VISIBLE);
		int t1 = 0, t2 = 0;
		for (int i = 0; i < size ; i++)
        {
			TableRow row = new TableRow(context);
			TextView tv = new TextView(context);
	        tv.setText(txo.sellTxo.get(i).name);
	        row.addView(tv);
	        
	        int temp = Integer.parseInt(txo.buyTxo.get(i).buy.replace(",", "")) + Integer.parseInt(txo.sellTxo.get(i).sell.replace(",", ""));
	        t1 += temp;
	        tv = new TextView(context);
	        tv.setText(formatValue(String.valueOf(temp)));
	        row.addView(tv);
	        
	        int temp2 = Integer.parseInt(txo.buyTxo.get(i).sell.replace(",", "")) + Integer.parseInt(txo.sellTxo.get(i).buy.replace(",", ""));
	        t2  += temp2;
	        tv = new TextView(context);
	        tv.setText(formatValue(String.valueOf(temp2)));
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        if (temp - temp2 < 0)
	        {
	        	tv.setTextColor(0xFF3CBD00);
	        }
	        tv.setText(formatValue(String.valueOf(temp - temp2)));
	        row.addView(tv);
	        
	        if(i%2 == 0)
	        	row.setBackgroundResource(R.drawable.row_border);
	        
	        mOptTable4.addView(row);
        }
		
		TableRow row = new TableRow(context);
		TextView tv = new TextView(context);
        tv.setText(R.string.major_c_4);
        row.addView(tv);
        
        tv = new TextView(context);
        tv.setText(formatValue(String.valueOf(t1)));
        row.addView(tv);
        
        tv = new TextView(context);
        tv.setText(formatValue(String.valueOf(t2)));
        row.addView(tv);
        
        mOptTable4.addView(row);
        
		mOptTable4.setVisibility(View.VISIBLE);
	}
	
	private static String formatValue(String value)
	{
		String finalString = "";
        if (value.startsWith("-"))
        {
        	finalString = "-";
        }
		value = value.replace("-", "");
        int total_len = value.length();
        

        
        if (total_len > 3)
        {
        	return finalString + value.substring(0, total_len - 3) + "," + value.substring(total_len - 3);
        }
        else
        {
        	return finalString + value;
        }
	}
	public static void updateTXOTable(Context context, Txo txo , TableLayout table)
	{
		if (debug) Log.d("LAI","+updateTXOTable()");
		table.removeAllViews();
		
		final int array[] = 
		{
			R.string.p1_c_0,
			R.string.p1_c_1,
			R.string.p1_c_2,
			R.string.p1_c_3,
			R.string.p1_c_4
		};

		TableRow row = new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
        	{
        		TableRow.LayoutParams params = new TableRow.LayoutParams();
        		if(i == 0 || i == 1)
        			params.gravity = Gravity.RIGHT;
        		if(i == 3 || i == 4)
        			params.gravity = Gravity.LEFT;
        		if(i == 2)
        			params.gravity = Gravity.CENTER;
        		params.height=0;
        		tv.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        		tv.setLayoutParams(params);
        	}
	        row.addView(tv);
        }
        table.addView(row);


		TableRow.LayoutParams params = new TableRow.LayoutParams();
		int buysize = txo.buylist.size();
		int buyTotal = 0;
		int sellTotal = 0;
		int maxBuy = 0;
		int maxSell = 0;
		String buy = "";
		String sell = "";
        for (int i = 0; i < buysize ; i++)
        {
	        if (!txo.buylist.get(i).date.equals(mOptionDateList.get(selectedIndex)))
	        {
	        	continue;
	        }
	        int ibuy = Integer.parseInt(txo.buylist.get(i).amount);
	        if (maxBuy < ibuy)
	        {
	        	maxBuy = ibuy;
	        }
	        
	        int isell = Integer.parseInt(txo.selllist.get(i).amount);
	        if (maxSell < isell)
	        {
	        	maxSell = isell;
	        }
        }
        for (int i = 0; i < buysize /*&& i < sellsize*/ ; i++)
        {
	        if (!txo.buylist.get(i).date.equals(mOptionDateList.get(selectedIndex)))
	        {
	        	continue;
	        }
	        
        	row = new TableRow(context);
        	lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        	row.setLayoutParams(lp);
        	
        	TextView tv = new TextView(context);
//        	buyTotal += Integer.parseInt(txo.buylist.get(i).amount);
        	
        	
	        buy = txo.buylist.get(i).amount;
	        int iBuy = Integer.parseInt(buy);
	        buyTotal += iBuy;
	        if (maxBuy == iBuy)
	        {
	        	tv.setTypeface(null, Typeface.BOLD);
	        	tv.setTextColor(Color.YELLOW);
	        	tv.setBackgroundColor(Color.BLUE);
	        }
	        
	        tv.setText(buy);
	        
        	params = new TableRow.LayoutParams();
    		params.gravity = Gravity.RIGHT;
        	tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.buylist.get(i).value);
	        params = new TableRow.LayoutParams();
    		params.gravity = Gravity.RIGHT;
	        tv.setLayoutParams(params);
	        tv.setTypeface(null, Typeface.BOLD);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.buylist.get(i).date.substring(4));
	        params = new TableRow.LayoutParams();
    		params.gravity = Gravity.CENTER;
	        tv.setLayoutParams(params);
	        tv.setTextColor(Color.RED);
	        tv.setTypeface(null, Typeface.BOLD);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        tv.setText(txo.selllist.get(i).value);
	        params = new TableRow.LayoutParams();
    		params.gravity = Gravity.LEFT;
    		tv.setTypeface(null, Typeface.BOLD);
	        tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        tv = new TextView(context);
	        sell = txo.selllist.get(i).amount;
	        int iSell = Integer.parseInt(sell);
	        sellTotal += iSell;
	        if (maxSell == iSell)
	        {
	        	tv.setTypeface(null, Typeface.BOLD);
	        	tv.setTextColor(Color.YELLOW);
	        	tv.setBackgroundColor(Color.BLUE);
	        }
	        tv.setText(sell);
	        params = new TableRow.LayoutParams();
    		params.gravity = Gravity.LEFT;
    		tv.setLayoutParams(params);
	        row.addView(tv);
	        
	        if(i%2 == 0)
	        	row.setBackgroundResource(R.drawable.row_border);

	        table.addView(row);
        }
        
        row = new TableRow(context);
    	lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
    	row.setLayoutParams(lp);
    	
    	double dpc =  (double)sellTotal/buyTotal;
    	pc.setText("P/C Ratio:" + String.format("%.02f", dpc));
		if (dpc >= 1)
		{
			pcImg.setImageResource(R.drawable.arrow_up);
		}
		else
		{
			pcImg.setImageResource(R.drawable.arrow_down);
		}
		
    	TextView tv = new TextView(context);
    	tv.setText(String.valueOf(buyTotal));
    	params = new TableRow.LayoutParams();
		params.gravity = Gravity.RIGHT;
		params.column = 0;
		tv.setTextColor(Color.RED);
    	tv.setLayoutParams(params);
        row.addView(tv);
        
        tv = new TextView(context);
        params = new TableRow.LayoutParams();
		params.gravity = Gravity.CENTER;
		params.column = 2;
        tv.setLayoutParams(params);
        tv.setTextColor(Color.RED);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(R.string.major_c_4);
        row.addView(tv);
        
    	tv = new TextView(context);
	    tv.setText(String.valueOf(sellTotal));
	    params = new TableRow.LayoutParams();
 		params.gravity = Gravity.LEFT;
 		params.column = 4;
 		tv.setTextColor(Color.RED);
 		tv.setLayoutParams(params);
	    row.addView(tv);
	    
	    row.setBackgroundResource(R.drawable.row_border2);
	    table.addView(row);
        if (debug) Log.d("LAI","-updateTXOTable()");
	}
	
	public static void addRow(Context context, int array[], TableLayout table, int resid)
	{
		 String _array[] = new String[array.length];
		 for (int i = 0; i < array.length ; i++)
		 {
			 _array[i] = context.getString(array[i]);
		 }
	     
		 addRow(context, _array, table, resid);
	}
	public static void addRow(Context context, String array[], TableLayout table, int resid)
	{
		TableRow row= new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
	        row.addView(tv);
        }
        if (resid != 0)
	        row.setBackgroundResource(resid);
        
        table.addView(row);
	}
	
	public static void addRowColorP3(Context context, String array[], TableLayout table, int resid)
	{
		TableRow row= new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);

    		if (array[i].startsWith("-"))
    		{
    			tv.setTextColor(0xFF3CBD00);
    		}

    		tv.setBackgroundResource(R.drawable.row_border3);
    		TableRow.LayoutParams params = new TableRow.LayoutParams();
    		params.span = 1;
    		tv.setLayoutParams(params);
	        row.addView(tv);
        }
        if (resid != 0)
	        row.setBackgroundResource(resid);
        
        table.addView(row);
	}
	
	public static void addRow2C(Context context, String array[], TableLayout table, int resid)
	{
		TableRow row= new TableRow(context);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        for (int i = 0; i < array.length ; i++)
        {
        	TextView tv = new TextView(context);
        	tv.setText(array[i]);
        	
        	if (i == 1)//for total
        	{
        		TableRow.LayoutParams params = new TableRow.LayoutParams();
        		params.span = 3;
        		params.gravity = Gravity.CENTER;
        		tv.setLayoutParams(params);
        	}
	        row.addView(tv);
        }
        if (resid != 0)
	        row.setBackgroundResource(resid);
        table.addView(row);
	}
	static DateHelper mOptionDate;
	static DateHelper mFutureDate;
	static DateHelper mBigDate;
	static DateHelper mBFI82UDate;//與t86共用
	public void onPreDate(View view)
	{
		if (debug) Log.d("LAI","onPreDate");
		mOptionDate.gotoPreDate();
		new OptionTask().execute(0);
	}
	public void onPreDateF(View view)
	{
		mFutureDate.gotoPreDate();
		new FutureTask().execute(0);
	}
	
	public void onPreDateB(View view)
	{
		mBigDate.gotoPreDate();
		new BigTask().execute(0);
	}
	
	public void onPreDateP3(View view)
	{
		mBFI82UDate.gotoPreDate();

		new BFI82UTask().execute(0);
	}
	
	public void onToday(View view)
	{
		if (debug) Log.d("LAI","onToday");
		mOptionDate = new DateHelper();
		new OptionTask().execute(0);
	}

	public void onTodayF(View view)
	{
		mFutureDate = new DateHelper();
		new FutureTask().execute(0);
	}
	
	public void onTodayB(View view)
	{
		mBigDate = new DateHelper();
		new BigTask().execute(0);
	}
	
	public void onTodayP3(View view)
	{
		mBFI82UDate = new DateHelper();
		new BFI82UTask().execute(0);
	}
	
	public void onNextDate(View view)
	{
		if (debug) Log.d("LAI","onNextDate");
		mOptionDate.gotoNextDate();
		new OptionTask().execute(1);
	}
	
	public void onNextDateB(View view)
	{
		mBigDate.gotoNextDate();
		new BigTask().execute(1);
	}
	
	public void onNextDateF(View view)
	{
		mFutureDate.gotoNextDate();
		new FutureTask().execute(1);
	}
	
	public void onNextDateP3(View view)
	{
		mBFI82UDate.gotoNextDate();
		new BFI82UTask().execute(1);
	}
	
	static boolean biggerThanToday(DateHelper _dh)
	{
		DateHelper today = new DateHelper();
		if(_dh.getYear()> today.getYear()||
				_dh.getMonth()> today.getMonth()||
				_dh.getDay()> today.getDay() && _dh.getMonth() >= today.getMonth())
		{
			return true;
		}
		return false;
	}
	static class BigTask extends AsyncTask<Integer, Void, Big> {
		boolean bNetworkError = false;
		@Override
		protected void onPostExecute(Big result) {
			// TODO Auto-generated method stub
			if (bNetworkError)
			{
				showBigNetworkError();
			}
			else
			{
				mBigStatusTV.setVisibility(View.GONE);
				updateBigTable(_activity, result);
				mBigDateTV.setText(_activity.getString(R.string.data_date) + mBigDate.toDisplayString());
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mBig5.setVisibility(View.GONE);
			mBig10.setVisibility(View.GONE);
			mBigCall5.setVisibility(View.GONE);
			mBigPut5.setVisibility(View.GONE);
			mBigCall10.setVisibility(View.GONE);
			mBigPut10.setVisibility(View.GONE);
			mTradeATL.setVisibility(View.GONE);
			mTradeBTL.setVisibility(View.GONE);
			mTradeCTL.setVisibility(View.GONE);
			mTailTV.setVisibility(View.GONE);
			mBigStatusTV.setText(R.string.read_data_please_wait);
			mBigStatusTV.setVisibility(View.VISIBLE);
			mBigDateTV.setText(_activity.getString(R.string.data_date) + mBigDate.toDisplayString());
			super.onPreExecute();
		}

		@Override
		protected Big doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			int direction = arg0[0];
			DateHelper dh = new DateHelper();
//			if (biggerThanToday(mBigDate))
//			{
//				return null;
//			}
			
			Big big = (Big) mDM.getData("BIG", mBigDate);
			if(big == null || big != null && (big.rowData.size() == 0))
			{
				if(!dh.hasNewData() && !mBigDate.toString().equals(dh.toString()) && mBigDate.getWeekDay() != 1 && mBigDate.getWeekDay() != 7)
				{
					//do nothing
				}
				else if(!dh.hasNewData() || mBigDate.getWeekDay() == 1 || mBigDate.getWeekDay() == 7)
				{
					if(direction == 0)
					{
						mBigDate.gotoPreviousValidDay();
						if (debug) Log.d("LAI","gotoPreviousValidDay:" + mBigDate.toDisplayString());
					}
					else
					{
						mBigDate.gotoNextValidDay();
						if (biggerThanToday(mBigDate))
						{
							return null;
						}
						if (debug) Log.d("LAI","gotoNextValidDay:" + mBigDate.toDisplayString());
					}
				}
				big = (Big) mDM.getData("BIG", mBigDate);
				if (big == null)
				{
					if (mUtils.isOnline(_activity))
					{
						mDM.fetchData("BIG", mBigDate);
						big = (Big) mDM.getData("BIG", mBigDate);
					}
					else
					{
						bNetworkError = true;
					}
				}
			}
			// TODO Auto-generated method stub
			return big;
		}
		
	}
	
	static class OptionNewTask extends AsyncTask<Integer, Void, TxoNew> {
		boolean bNetworkError = false;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(TxoNew txo) {
			// TODO Auto-generated method stub
			super.onPostExecute(txo);
			if (bNetworkError)
			{
				showOptNetworkError();
			}
			else
			{
				updateTXONewTable(_activity,txo);
			}
			if (debug) Log.d("LAI_915","-OptionNewTask()");
		}
		@Override
		protected TxoNew doInBackground(Integer... params) {
			if (debug) Log.d("LAI_915","going to get Opt date:" + mOptionDate.toDisplayString());
			DateHelper dh = new DateHelper();
//			if (biggerThanToday(mOptionDate))
//			{
//				if (debug) Log.d("LAI_915","biggerThanToday, today:" + dh.toDisplayString());
//				return null;
//			}
			
			TxoNew txo = (TxoNew) mDM.getData("NEWOPT", mOptionDate);
			if(txo == null || txo != null && (txo.buyTxo.size() == 0))
			{
				if (mUtils.isOnline(_activity))
				{
					mDM.fetchData("NEWOPT", mOptionDate);
					txo = (TxoNew) mDM.getData("NEWOPT", mOptionDate);
				}
				else
				{
					bNetworkError = true;
				}
			}
			// TODO Auto-generated method stub
			return txo;
		}
	}
	static class OptionTask extends AsyncTask<Integer, Void, Txo> {
		boolean bNetworkError = false;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (debug) Log.d("LAI","+OptionTask()");
			mOptTable1.setVisibility(View.GONE);
			mOptTable2.setVisibility(View.GONE);
			mOptTable3.setVisibility(View.GONE);
			mOptTable4.setVisibility(View.GONE);
			mOptStatus.setText(R.string.read_data_please_wait);
			mOptStatus.setVisibility(View.VISIBLE);
			mOptDate.setText(_activity.getString(R.string.data_date) + mOptionDate.toDisplayString());
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(Txo txo) {
			// TODO Auto-generated method stub
			super.onPostExecute(txo);
			if (bNetworkError)
			{
				showOptNetworkError();
			}
			else
			{
				mOptStatus.setVisibility(View.GONE);
				mOptDate.setText(_activity.getString(R.string.data_date) + mOptionDate.toDisplayString());
				parseTxoDate(txo);
			}
			if (debug) Log.d("LAI","-OptionTask()");
			
			
		}
		@Override
		protected Txo doInBackground(Integer... params) {
			int direction = params[0];
			if (debug) Log.d("LAI","going to get Opt date:" + mOptionDate.toDisplayString());
			DateHelper dh = new DateHelper();
//			if (biggerThanToday(mOptionDate))
//			{
//				if (debug) Log.d("LAI","biggerThanToday, today:" + dh.toDisplayString());
//				return null;
//			}
			
			Txo txo = (Txo) mDM.getData("OPT", mOptionDate);
			if(txo == null || txo != null && (txo.buylist.size() == 0))
			{
				if(!dh.hasNewData() && !mOptionDate.toString().equals(dh.toString()) && mOptionDate.getWeekDay() != 1 && mOptionDate.getWeekDay() != 7)
				{
					//do nothing
				}
				else if(!dh.hasNewData() || mOptionDate.getWeekDay() == 1 || mOptionDate.getWeekDay() == 7)
				{
					if(direction == 0)
					{
						mOptionDate.gotoPreviousValidDay();
						if (debug) Log.d("LAI","gotoPreviousValidDay:" + mOptionDate.toDisplayString());
					}
					else
					{
						mOptionDate.gotoNextValidDay();
						if (biggerThanToday(mOptionDate))
						{
							return null;
						}
						if (debug) Log.d("LAI","gotoNextValidDay:" + mOptionDate.toDisplayString());
					}
				}
				txo = (Txo) mDM.getData("OPT", mOptionDate);
				if (txo == null)
				{
					if (mUtils.isOnline(_activity))
					{
						mDM.fetchData("OPT", mOptionDate);
						txo = (Txo) mDM.getData("OPT", mOptionDate);
					}
					else
					{
						bNetworkError = true;
					}
				}
			}
			// TODO Auto-generated method stub
			return txo;
		}
	}
	
	static private void showOptNetworkError()
	{
		mOptStatus.setVisibility(View.VISIBLE);
		mOptStatus.setText(R.string.network_error);
		mOptTable1.setVisibility(View.GONE);
		mOptTable2.setVisibility(View.GONE);
		mOptTable3.setVisibility(View.GONE);
		mOptTable4.setVisibility(View.GONE);
	}
	
	static private void showFuNetworkError()
	{
		mFuStatus.setVisibility(View.VISIBLE);
		mFuStatus.setText(R.string.network_error);
		mFuTable.setVisibility(View.GONE);
	}
	
	static private void showP3NetworkError()
	{
		mTvStatus.setVisibility(View.VISIBLE);
		mTvStatus.setText(R.string.network_error);
		tableBFI82U.setVisibility(View.GONE);
		tableT86.setVisibility(View.GONE);
		mLoadTitleP3.setVisibility(View.GONE);
	}
	
	static private void showBigNetworkError()
	{
		mBigStatusTV.setVisibility(View.VISIBLE);
		mBigStatusTV.setText(R.string.network_error);
		mBig5.setVisibility(View.GONE);
		mBig10.setVisibility(View.GONE);
		mBigCall5.setVisibility(View.GONE);
		mBigPut5.setVisibility(View.GONE);
		mBigCall10.setVisibility(View.GONE);
		mBigPut10.setVisibility(View.GONE);
		mTradeATL.setVisibility(View.GONE);
		mTradeBTL.setVisibility(View.GONE);
		mTradeCTL.setVisibility(View.GONE);
		mTailTV.setVisibility(View.GONE);
	}

	static class BFI82UTask extends AsyncTask<Integer, Void, BFI82U> {
		boolean bNetworkError = false;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (debug) Log.d("BFI82U","+BFI82UTask()");
			mLoadTitleP3.setVisibility(View.VISIBLE);
			mTvStatus.setVisibility(View.GONE);
			tableBFI82U.setVisibility(View.GONE);
			tableT86.setVisibility(View.GONE);
			mLoadTitleP3.setText(R.string.read_data_please_wait);
			mLoadTitleP3.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(BFI82U f) {
			// TODO Auto-generated method stub
			super.onPostExecute(f);
			mLoadTitleP3.setVisibility(View.GONE);
			Log.e("", "@@@@@@@@@@@@@ BFI82UTask  111");
			if (bNetworkError)
			{
				Log.e("", "@@@@@@@@@@@@@ BFI82UTask  222");
				showP3NetworkError();
			}
			else
			{
				Log.e("", "@@@@@@@@@@@@@ BFI82UTask  333");
				dateP3.setText(_activity.getString(R.string.data_date) + mBFI82UDate.toDisplayString());
				
				if (f != null && f.date != null &&!f.date.isEmpty())
				{
					Log.e("", "@@@@@@@@@@@@@ BFI82UTask  444");
					createBFI82UTable(_activity, f);
				}
				new T86Task().execute(0);
			}
			
			
			if (debug) Log.d("BFI82U","-BFI82UTask()");
		}
		@Override
		protected BFI82U doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			if (debug) Log.d("BFI82U","doInBackground()"+ mBFI82UDate.toDisplayString());
			int direction = params[0];
			DateHelper dh = new DateHelper();
//			if (biggerThanToday(mBFI82UDate))
//			{
//				if (debug) Log.d("BFI82U","biggerThanToday, today:" + dh.toDisplayString());
//				return null;
//			}
			BFI82U f = (BFI82U) mDM.getData("BFI82U", mBFI82UDate);
			if(f == null)//|| f != null && (f.data.size() == 0))
			{
				if(!dh.hasNewData() && !mBFI82UDate.toString().equals(dh.toString()) && mBFI82UDate.getWeekDay() != 1 && mBFI82UDate.getWeekDay() != 7)
				{
					//do nothing
				}
				else if(!dh.hasNewData() || mBFI82UDate.getWeekDay() == 1 || mBFI82UDate.getWeekDay() == 7)
				{
					if(direction == 0)
					{
						mBFI82UDate.gotoPreviousValidDay();
						if (debug) Log.d("BFI82U","gotoPreviousValidDay:" + mBFI82UDate.toDisplayString());
					}
					else
					{
						mBFI82UDate.gotoNextValidDay();
						if (biggerThanToday(mBFI82UDate))
						{
							mBFI82UDate.gotoPreviousValidDay();
							return null;
						}
						if (debug) Log.d("BFI82U","gotoNextValidDay:" + mBFI82UDate.toDisplayString());
					}
				}
				f = null;//(BFI82U) mDM.getData("BFI82U", mBFI82UDate);
				if (f == null)
				{
					if (mUtils.isOnline(_activity))
					{
						Log.e("", "mDM.fetchData(\"BFI82U\", mBFI82UDate)");
						mDM.fetchData("BFI82U", mBFI82UDate);
						f = (BFI82U) mDM.getData("BFI82U", mBFI82UDate);
						if (f == null)
							Log.e("", "mDM.fetchData(\"BFI82U\", mBFI82UDate) 222");
					}
					else
					{
						bNetworkError = true;
						//network is not available
					}
				}
			}
			return f;
		}
		
	}
	
	static class T86Task extends AsyncTask<Integer, Void, T86> {
		boolean bNetworkError = false;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (debug) Log.d("xxxx","+T86Task()");
			mLoadTitleP3.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(T86 f) {
			// TODO Auto-generated method stub
			super.onPostExecute(f);
			mLoadTitleP3.setVisibility(View.GONE);
			if (bNetworkError)
			{
			}
			else
			{
				if (f == null)
				{
				}
				else
				{
					createT86Table(_activity, f);
				}
			}
			if (debug) Log.d("xxxx","-T86Task()");
		}
		@Override
		protected T86 doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int direction = params[0];
			DateHelper dh = new DateHelper();
//			if (biggerThanToday(mBFI82UDate))
//			{
//				if (debug) Log.d("xxxx","biggerThanToday, today:" + dh.toDisplayString());
//				return null;
//			}
			T86 f = (T86) mDM.getData("T86", mBFI82UDate);
			if(f == null)//|| f != null && (f.data.size() == 0))
			{
				{
					if (mUtils.isOnline(_activity))
					{
						mDM.fetchData("T86", mBFI82UDate);
						f = (T86) mDM.getData("T86", mBFI82UDate);
					}
					else
					{
						bNetworkError = true;
						//network is not available
					}
				}
			}
			return f;
		}
		
	}
	
	static class FutureTask extends AsyncTask<Integer, Void, Future> {
		boolean bNetworkError = false;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (debug) Log.d("LAI","+FutureTask()");
			mFuTable.setVisibility(View.GONE);
			mFuStatus.setText(R.string.read_data_please_wait);
			mFuStatus.setVisibility(View.VISIBLE);
			mFuDate.setText(_activity.getString(R.string.data_date) + mFutureDate.toDisplayString());
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(Future f) {
			// TODO Auto-generated method stub
			super.onPostExecute(f);
			if (bNetworkError)
			{
				showFuNetworkError();
			}
			else
			{
				mFuStatus.setVisibility(View.GONE);
				mFuDate.setText(_activity.getString(R.string.data_date) + mFutureDate.toDisplayString());
				updateFutureTable(_activity, f, mFuTable);
			}
			if (debug) Log.d("LAI","-FutureTask()");
		}
		@Override
		protected Future doInBackground(Integer... params) {
			int direction = params[0];
			if (debug) Log.d("LAI","going to get Future date:" + mFutureDate.toDisplayString());
			DateHelper dh = new DateHelper();
//			if (biggerThanToday(mFutureDate))
//			{
//				if (debug) Log.d("LAI","biggerThanToday, today:" + dh.toDisplayString());
//				return null;
//			}
			
			Future f = (Future) mDM.getData("F", mFutureDate);
			if(f == null || f != null && (f.data.size() == 0))
			{
				if(!dh.hasNewData() && !mFutureDate.toString().equals(dh.toString()) && mFutureDate.getWeekDay() != 1 && mFutureDate.getWeekDay() != 7)
				{
					//do nothing
				}
				else if(!dh.hasNewData() || mFutureDate.getWeekDay() == 1 || mFutureDate.getWeekDay() == 7)
				{
					if(direction == 0)
					{
						mFutureDate.gotoPreviousValidDay();
						if (debug) Log.d("LAI","gotoPreviousValidDay:" + mFutureDate.toDisplayString());
					}
					else
					{
						mFutureDate.gotoNextValidDay();
						if (biggerThanToday(mFutureDate))
						{
							return null;
						}
						if (debug) Log.d("LAI","gotoNextValidDay:" + mFutureDate.toDisplayString());
					}
				}
				f = (Future) mDM.getData("F", mFutureDate);
				if (f == null)
				{
					if (mUtils.isOnline(_activity))
					{
						mDM.fetchData("F", mFutureDate);
						f = (Future) mDM.getData("F", mFutureDate);
					}
					else
					{
						bNetworkError = true;
						//network is not available
					}
				}
			}
			// TODO Auto-generated method stub
			return f;
		}
	}
	
	public void onClickLink(View v)
	{
		if (debug) Log.d("LAI","onClickLink");
		Intent browse = new Intent( Intent.ACTION_VIEW ,
				Uri.parse( "https://docs.google.com/spreadsheet/viewform?fromEmail=true&formkey=dGs0ajhBb28weE5LS0VYdS1nUDBDRkE6MQ" ) );
	    startActivity( browse );
	}
	
	public void onClickOpen(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW ,
				Uri.parse("http://www.cnyes.com/economy/indicator/EconomicsCalendar/Calendar.aspx"));
	    startActivity( browse );
	}
	
	
	public void onClickA(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://kululu0517.blogspot.tw/2014/09/blog-post_4.html") );
	    startActivity( browse );
	}
	
	public void onClickB(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://kululu0517.blogspot.tw/2014/09/blog-post_60.html") );
	    startActivity( browse );
	}

	public void onClickC(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://www.taifex.com.tw/chinese/5/stockmargining.asp") );
    	startActivity( browse );
	}

	public void onClickD(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://www.concordfutures.com.tw/exchange_tradeSpecification.htm") );
	    startActivity( browse );
	}
	
	public void onClickE(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://www.concordfutures.com.tw/exchange_margin.htm") );
	    startActivity( browse );
	}
	
	public void onClickF(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://kululu0517.blogspot.tw/2014/09/blog-post.html") );
	    startActivity( browse );
	}
	
	public void onClickG(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://kululu0517.blogspot.tw/2014/09/pc-ratio.html") );
		startActivity( browse );
	}
	
	public void onClickH(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://www.concordfutures.com.tw/exchange_tradeDay.htm") );
	    startActivity( browse );
	}
	
	public void onClickI(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://www.concordfutures.com.tw/exchange_holiday.htm") );
	    startActivity( browse );
	}
	
	public void onClickJ(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://www.concordfutures.com.tw/information/world_time.htm") );
	    startActivity( browse );
	}
	
	public void onClickLineID(View v)
	{
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://line.me/ti/p/S1h0xUlJD5") );
	    startActivity( browse );
	}
	
	public void onClickShare(View v)
	{
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "台灣期貨未平倉查詢\nhttps://play.google.com/store/apps/details?id=com.aloha.stock");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}
	
	public void onClickBlog(View v) {
		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("http://yenchun0517.blogspot.tw/") );
	    startActivity( browse );
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public void onDownLoadAppTextClick(View v)
	{
		try 
		{
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.concords.Futures.onlineacct&hl=zh_TW")));
		} 
		catch (android.content.ActivityNotFoundException anfe) 
		{
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.concords.Futures.onlineacct&hl=zh_TW")));
		}
	}
	
	public void onDownLoadMunualTextClick(View v)
	{
		try 
		{
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.appmk.magazine.AOSZBBKIOVVHVSJ&hl=zh_TW")));
		} 
		catch (android.content.ActivityNotFoundException anfe) 
		{
		    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.appmk.magazine.AOSZBBKIOVVHVSJ&hl=zh_TW")));
		}
	}
}

