package com.aloha.stock;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.aloha.stock.helper.DateHelper;




import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DataManager{
	private final boolean debug = false;
	private String PATH ;
	private String STOCK_PATH;
	private String FUTURE_PATH;
	private String BFI82U_PATH;
	private String T86_PATH;
	ArrayList <String> datelist;
	MainActivity _activity;
	HashMap <String, Txo> dataMap = new HashMap<String, Txo> ();
	HashMap <String, TxoNew> txoNewMap = new HashMap<String, TxoNew> ();
	HashMap <String, Future> futureMap = new HashMap<String, Future> ();
	HashMap <String, BFI82U> bfi82Map = new HashMap<String, BFI82U> ();
	HashMap <String, T86> t86Map = new HashMap<String, T86> ();
	HashMap <String, Big> bigMap = new HashMap<String, Big> ();
	int opt_day;
	int opt_month;
	int opt_year;
	Stock stock;
	DataManager(MainActivity activity)
	{
		_activity = activity;
		PATH = activity.getApplicationInfo().dataDir + "/data/";
		STOCK_PATH = activity.getApplicationInfo().dataDir + "/stock/";
		FUTURE_PATH = activity.getApplicationInfo().dataDir + "/future/";
		BFI82U_PATH = activity.getApplicationInfo().dataDir + "/BFI82U/";
		T86_PATH = activity.getApplicationInfo().dataDir + "/T86/";
		datelist = new ArrayList <String>();
		init();
	}
	
	void init()
	{
		datelist.clear();
		_activity.mUtils.createFolder(PATH);
		_activity.mUtils.createFolder(STOCK_PATH);
		_activity.mUtils.createFolder(FUTURE_PATH);
		_activity.mUtils.createFolder(BFI82U_PATH);
		_activity.mUtils.createFolder(T86_PATH);
		File f = new File(PATH);
		File[] files = f.listFiles();
		if (files == null)
			return;
		for (File inFile : files) {
			if (debug) Log.d("LAI", inFile.getName());
			datelist.add(inFile.getName());
		}
	}
	
	public int get_id(String string)
	{
		String array[] = {"臺股","電子","金融","小型","臺灣50","股票","ETF","櫃買","非金","東證","期貨"};
		for (int i=0; i<array.length; i++)
		{
			if (string.compareTo(array[i]) == 0)
			{
				return i;
			}
		}
		return -1;
	}

	public String fetchData(String mode, DateHelper dh)
	{
		String year = String.valueOf(dh.getYear());
		String month = String.valueOf(dh.getMonth());
		String day = String.valueOf(dh.getDay());
		if (debug) Log.d("LAI","+fetchData() " + mode + " y:" + year + " m:"+ month + " d:" + day);
		Document doc = null;
		try {
			if (mode.equals("NEWOPT"))//OPTION
			{
				doc = Jsoup.connect("http://www.taifex.com.tw/chinese/3/7_12_5.asp")
					    .data("syear", year)
					    .data("smonth", month)
					    .data("sday", day)
					    .data("COMMODITY_ID", "TXO")
					    .timeout(5000)
					    .post();
				Elements elements = doc.select("span:contains(日期)");
				String _date = elements.get(0).text().toString();
				_date = _date.substring(_date.indexOf("期") + 1);
				String words[] = _date.split("/");
				opt_year = Integer.parseInt(words[0]);
				opt_month = Integer.parseInt(words[1]);
				opt_day = Integer.parseInt(words[2]);
				elements = doc.select("font[color]");
				
				TxoNew txo = new TxoNew();
				txo.date = String.valueOf(opt_year) + String.format("%02d", opt_month) + String.format("%02d", opt_day);
				txo.buyTxo = new ArrayList <TxoDetail>();
				txo.sellTxo = new ArrayList <TxoDetail>();
				
				TxoDetail d = new TxoDetail();
				d.name = String.valueOf("自營商");
				d.buy = elements.get(3).text();
				d.sell = elements.get(4).text();
				d.diff = elements.get(5).text();
				txo.buyTxo.add(d);
				
				d = new TxoDetail();
				d.name = String.valueOf("投信");
				d.buy = elements.get(9).text();
				d.sell = elements.get(10).text();
				d.diff = elements.get(11).text();
				txo.buyTxo.add(d);
				
				d = new TxoDetail();
				d.name = String.valueOf("外資");
				d.buy = elements.get(15).text();
				d.sell = elements.get(16).text();
				d.diff = elements.get(17).text();
				txo.buyTxo.add(d);
				
				d = new TxoDetail();
				d.name = String.valueOf("自營商");
				d.buy = elements.get(21).text();
				d.sell = elements.get(22).text();
				d.diff = elements.get(23).text();
				txo.sellTxo.add(d);
				
				d = new TxoDetail();
				d.name = String.valueOf("投信");
				d.buy = elements.get(27).text();
				d.sell = elements.get(28).text();
				d.diff = elements.get(29).text();
				txo.sellTxo.add(d);
				
				d = new TxoDetail();
				d.name = String.valueOf("外資");
				d.buy = elements.get(33).text();
				d.sell = elements.get(34).text();
				d.diff = elements.get(35).text();
				txo.sellTxo.add(d);
				
				_activity.mUtils.saveData(txo, PATH , mode + txo.date);
				txoNewMap.put(mode + year + month + day, txo);
			}
			else if (mode.equals("OPT"))//OPTION
			{
				doc = Jsoup.connect("http://www.taifex.com.tw/chinese/3/3_2_1.asp")
				    .data("syear", year)
				    .data("smonth", month)
				    .data("sday", day)
				    .data("commodity_id", "TXO")
				    .data("commodity_id2","")
				    .timeout(5000)
				    .post();
				Elements elements = doc.select("span:contains(日期:)");
				String _date = elements.get(0).text().toString();
				_date = _date.substring(_date.indexOf(":") + 1);
				String words[] = _date.split("/");
				opt_year = Integer.parseInt(words[0]);
				opt_month = Integer.parseInt(words[1]);
				opt_day = Integer.parseInt(words[2]);

				if (debug) Log.d("JSOUP", "get done: " + opt_year + opt_month + opt_day);
				elements = doc.select("table[class$=table_a]");
				if (debug) Log.d("JSOUP","elements size:"+elements.size());
				Elements elements_buy = elements.get(0).select("TR[bgColor]");
				if (debug) Log.d("JSOUP","elements_buy");
				Elements elements_sell = elements.get(1).select("TR[bgColor]");
				if (debug) Log.d("JSOUP","elements_sell");
				Txo txo = new Txo();
				txo.date = String.valueOf(opt_year) + String.format("%02d", opt_month) + String.format("%02d", opt_day);
				txo.buylist = new ArrayList <_txo> ();
				txo.selllist = new ArrayList <_txo> ();
				double call = 0,put = 0 ;
				for (Element element : elements_buy) {
				    words = element.text().split(" ");
				    if (words.length == 9)
				    {
				    	_txo a = new _txo();
				    	a.date = words[0];
				    	a.value = words[1];
				    	a.amount = words[8];
				    	call += Integer.parseInt(words[8]);
				    	txo.buylist.add(a);
				    }
				}
				
				for (Element element : elements_sell) {
				    words = element.text().split(" ");
				    if (words.length == 9)
				    {
				    	_txo a = new _txo();
				    	a.date = words[0].substring(4);
				    	a.value = words[1];
				    	a.amount = words[8];
				    	put += Integer.parseInt(words[8]);
				    	txo.selllist.add(a);
				    }
				}
				txo.pc = put/call;
				if (debug) Log.d("LAI", "PC:" + txo.pc);
				_activity.mUtils.saveData(txo, PATH , mode + txo.date);
				dataMap.put(mode + year + month + day, txo);
			}
			else if (mode.equals("BIG"))//OPTION
			{
				doc = Jsoup.connect("http://www.taifex.com.tw/chinese/3/7_8.asp")
					    .data("yytemp", year)
					    .data("mmtemp", month)
					    .data("ddtemp", day)
					    .data("chooseitemtemp", "TX")
					    .data("choose_yy", year)
					    .data("choose_mm", month)
					    .data("choose_dd", day)
					    .timeout(5000)
					    .post();
				Elements elements = doc.select("td.11b");
				if (elements.size() == 0)
				{
					return year + month + day;
				}
				Big big = new Big();
				big.rowData = new ArrayList<BigRowData>();
				for(int i = 0; i < 3; i ++)
				{
					BigRowData row = new BigRowData();
					for (int j = 0 ;j < 10 ; j++)
					{
						row.text[j] = elements.get(i*10 + j).text().replace(" ", "\n");
					}
					big.rowData.add(row);
				}
				doc = Jsoup.connect("http://www.taifex.com.tw/chinese/3/7_9.asp")
					    .data("yytemp", year)
					    .data("mmtemp", month)
					    .data("ddtemp", day)
					    .data("chooseitemtemp", "TXO")
					    .data("choose_yy", year)
					    .data("choose_mm", month)
					    .data("choose_dd", day)
					    .timeout(5000)
					    .post();
				elements = doc.select("td.11b");
				for(int i = 0; i < 6; i ++)
				{
					BigRowData row = new BigRowData();
					for (int j = 0 ;j < 10 ; j++)
					{
						row.text[j] = elements.get(i*10 + j).text().replace(" ", "\n");
					}
					big.rowData.add(row);		
				}

				if (month.length() == 1)
				{
					month = "0" + month;
				}
				if (day.length() == 1)
				{
					day = "0" + day;
				}
				big.date = year + month + day;
				_activity.mUtils.saveData(big, PATH , mode + big.date);
				bigMap.put(mode + year + month + day, big);
			}
			else if (mode.equals("BFI82U"))
			{
				BFI82U bf = getBFI82U(dh);
				if (bf != null)
				{
					return bf.date;
				}
			}
			else if (mode.equals("T86"))
			{
				T86 bf = getT86(dh);
				if (bf != null)
				{
					return bf.date;
				}
			}
			else
			{
				Log.e("@@@@@@@@", "test  future12322" + ", y=" + year + " , m=" + month + " , day=" + day);
				String _date_ = year + "/" + month + "/" + day;
				doc = Jsoup.connect("http://www.taifex.com.tw/chinese/3/7_12_3.asp")
					.data("goday","")	
				    .data("DATA_DATE_Y", year)
				    .data("DATA_DATE_M", month)
				    .data("DATA_DATE_D", day)
				    .data("syear", year)
				    .data("smonth", month)
				    .data("sday", day)
				    .data("datestart", _date_)
				    .data("COMMODITY_ID","") 
				    .timeout(5000)
				    .post();
				Future f = new Future();
				String _date = null;
				try{
					_date = doc.select("p.clearfix").first().text();
				}catch(Exception e){
					Log.e("", "Can't get data");
					//no data at the day
					f.date = year + month + day;
					_activity.mUtils.saveData(f, FUTURE_PATH , mode + year + month + day);
					futureMap.put(mode + f.date, f);
					return f.date;
				}
				 
				String words[] = _date.substring(_date.indexOf("期")+1).split("/");
				String m = words[1];
				String d = words[2];
				if (month.length()==1)
					month = "0" + month;
				
				if (day.length()==1)
					day = "0" + day;
					
				f.date = year + month + day;
				Log.e("", "fdate = " + f.date);
				int data_id = 0;	
				Elements elements = doc.select("tr.12bk");
				for (Element element : elements) {
					words = element.text().split(" ");
					_future future = new _future();
					
					if (words.length == 13)
					{
						if (words[0].contains("計"))
						{
							f.total1 = words[5];
							f.total2 = words[11];
						}
						else
						{
							future.a = words[5];
							future.b = words[11];
							future.type = data_id;
							f.data.add(future);		
						}
					}
					else if (words.length == 16)
					{
						data_id = get_id(words[1]);
//						future.a = words[7];
//						future.b = words[13];
//						future.type = data_id;
//						f.data.add(future);
						future.a = words[8];
						future.b = words[14];
						future.type = data_id;
						f.data.add(future);
					}
					else if (words.length == 15)
					{
						data_id = get_id(words[0]);
						future.a = words[7];
						future.b = words[13];
						future.type = data_id;
						f.data.add(future);
					}
					else if (words.length == 17)
					{
						data_id = get_id(words[1]);
//						future.a = words[7];
//						future.b = words[13];
//						future.type = data_id;
//						f.data.add(future);
						future.a = words[9];
						future.b = words[15];
						future.type = data_id;		
		 				f.data.add(future);
					}
				}
				//_activity.mUtils.saveData(f, FUTURE_PATH , mode + f.date);
				futureMap.put(mode + f.date, f);
				return f.date;
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (debug) Log.d("LAI","-fetchData()");
		return year + month + day;
	}

	/*
	 * get from memory or file
	 */
	public Object getData(String page, DateHelper date)
	{
		synchronized(this){
			//1. load file into memory
			DateHelper dh = new DateHelper();
			if(page.equals("OPT"))
			{
				String key = page + date.toString();
				Txo txo = dataMap.get(key);
				if (txo == null)
				{
					if (debug) Log.d("LAI", "loadData:" + PATH + key);
					txo = (Txo)_activity.mUtils.loadData(PATH, key);
					if (txo != null)
					{
						dataMap.put(key, txo);
						if (debug) Log.d("LAI", "txo: done" + txo.date); 
					}
				}
				return txo;
			}
			else if(page.equals("NEWOPT"))
			{
				String key = page + date.toString();
				TxoNew txo = txoNewMap.get(key);
				if (txo == null)
				{
					if (debug) Log.d("LAI", "loadData:" + PATH + key);
					txo = (TxoNew)_activity.mUtils.loadData(PATH, key);
					if (txo != null)
					{
						txoNewMap.put(key, txo);
						if (debug) Log.d("LAI", "txoNew: done" + txo.date);
					}
				}
				return txo;
			}
			else if(page.equals("BIG"))
			{
				String key = page + date.toString();
				Big txo = bigMap.get(key);
				if (txo == null)
				{
					if (debug) Log.d("LAI_923", "loadData:" + PATH + key);
					txo = (Big)_activity.mUtils.loadData(PATH, key);
					if (txo != null)
					{
						bigMap.put(key, txo);
						if (debug) Log.d("LAI", "txoNew: done" + txo.date);
					}
				}
				return txo;
			}
			else if(page.startsWith("F"))
			{
				String key = page + date.toString();
				Future f = futureMap.get(key);
				if (f == null)
				{
					if (debug) Log.d("LAI", "loadData:" + FUTURE_PATH + key);
					//f = (Future)_activity.mUtils.loadData(FUTURE_PATH, key);
					if (f != null)
					{
						futureMap.put(key, f);
						if (debug) Log.d("LAI", "f: done" + f.date);
					}
				}
				return f; // return f;
			}
			else if (page.equals("BFI82U"))
			{
				String key = date.toString();
				BFI82U bf = bfi82Map.get(key);
				if (bf == null)
				{
					if (debug) Log.d("LAI", "loadData:" + BFI82U_PATH + key);
					bf = (BFI82U)_activity.mUtils.loadData(BFI82U_PATH, key);
					if (bf != null)
					{
						bfi82Map.put(key, bf);
						if (debug) Log.d("LAI", "BFI82U key: " + key);//bfi82Map.date);
					}
				}
				return bf;
			}
			else if (page.equals("T86"))
			{
				String key = date.toString();
				T86 bf = t86Map.get(key);
				if (bf == null)
				{
					if (debug) Log.d("xxxx", "loadData:" + T86_PATH + key);
					
					bf = (T86)_activity.mUtils.loadData(T86_PATH, key);
					if (bf != null)
					{
						t86Map.put(key, bf);
						if (debug) Log.d("xxxx", "T86 key: " + key);
					}
				}
				return bf;
			}
			else //if (key.startsWith("S"))//MODE = STOCK
			{
				if (debug) Log.d("LAI", "STOCK!");
				if(dh.hasNewData())//after 3pm
				{
					if (debug) Log.d("LAI", "after 3pm!");
					stock = (Stock) _activity.mUtils.loadData(STOCK_PATH, "stock");
					if (stock != null && stock.year != null && stock.month != null && stock.date != null)
					{
						String _date = stock.year + stock.month + stock.date;
						if (_date.equals(dh.toString()) ||
								(dh.getWeekDay() == 7 && !_date.equals(dh.getPreDate(1)))||
								(dh.getWeekDay() == 1 && !_date.equals(dh.getPreDate(2))))
						{
							return stock;
						}
					}
					/*
					 * download
					 */
					stock = getStock();
					return stock;
				}
				else//before 3pm , get old data is ok
				{
					if (debug) Log.d("LAI", "before 3pm!");
					if(stock == null)
					{
						stock = (Stock) _activity.mUtils.loadData(STOCK_PATH, "stock");
					}
					String _date = "";
					if (stock != null)
					{
						_date = stock.year + stock.month + stock.date;
					}
					else
					{
						if (debug) Log.d("LAI", "stock=null");
					}
					if (stock != null && (_date.equals(dh.toString())||_date.equals(dh.getPreDate(1))||
										 (dh.getWeekDay() == 7 && _date.equals(dh.getPreDate(1)))||
										 (dh.getWeekDay() == 1 && _date.equals(dh.getPreDate(2)))||
										 (dh.getWeekDay() == 2 && _date.equals(dh.getPreDate(3)))))
					{
						return stock;
					}
					else
					{
						stock = getStock();
						return stock;
					}
				}
			}
		}
	}

	/*
	 * http://www.twse.com.tw/ch/trading/fund/BFI82U/BFI82U.php
	 */
	private BFI82U getBFI82U(DateHelper dh)
	{
		BFI82U data = new BFI82U();
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.twse.com.tw/ch/trading/fund/BFI82U/BFI82U.php")
				    .data("input_date", dh.toChineseDisplayString())
				    .timeout(5000)
				    .post();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			_activity.runOnUiThread(new Runnable() {
			    public void run() {
					Toast toast = Toast.makeText(_activity, R.string.network_error, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
			    }
			});
			return null;
		}

		/*
		 * get elements
		 */
		Elements elements1 = doc.select("td[class$=basic2]");
		if (elements1.isEmpty())
			return null;
		
		for (int i = 0; i < 5 ;i ++)
		{
			for(int j = 0; j < 3 ;j ++)
			{
				data.row[i][j] = toE(elements1.get(i*3 + j).text());
			}
		}
		
		data.date = dh.toDisplayString();
		_activity.mUtils.saveData(data, BFI82U_PATH, dh.toDisplayString().replace("/", ""));
		return data;
	}
	
	private String toE(String value)
	{
		boolean negitive = false;
		if (value.startsWith("-"))
		{
			negitive = true;
			value = value.substring(1);
		}
		
		String new_value = value.replace(",", "");
		if (new_value.length() == 8)
		{
			new_value = 0 + "." + new_value.substring(0, 2);
		}
		else if (new_value.length() == 9)
		{
			new_value = new_value.substring(0, 1) + "." + new_value.substring(1, 3);
		}
		else if (new_value.length() == 10)
		{
			new_value = new_value.substring(0, 2) + "." + new_value.substring(2, 4);
		}
		else if (new_value.length() == 11)
		{
			new_value = new_value.substring(0, 3) + "." + new_value.substring(3, 5);
		}
		else if (new_value.length() == 7)
		{
			new_value = "0.0" + new_value.substring(0, 1);
		}
		else
		{
			new_value = "0";
		}
		
		if(negitive)
		{
			return "-" + new_value;
		}
		return new_value;
	}
	
	private T86 getT86(DateHelper dh)
	{
		T86 data = new T86();
		Document doc = null;
		try {
			doc = Jsoup.connect("http://www.twse.com.tw/ch/trading/fund/T86/T86.php")
				    .data("qdate", dh.toChineseDisplayString())
				    .data("select2", "ALLBUT0999")//全部(不含權證、牛熊證、可展延牛熊證)
				    .timeout(1000)
				    .post();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			_activity.runOnUiThread(new Runnable() {
			    public void run() {
					Toast toast = Toast.makeText(_activity, R.string.network_error, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
			    }
			});
			return null;
		}
		
		/*
		 * get elements
		 */
		Elements elements1 = doc.select("table[class$=sortable]");
		String[] array = elements1.text().split(" ");
		
		int cnt = 0;
		if (array.length < 100)
			return null;
		for (int i=38; i<array.length; i+=16)
		{
			int useful_data[] = {0, 1, 2, 3, 5, 6, 9, 10, 12, 13, 15};
			for (int j=0; j<11; j++)
				data.row[cnt][j] = array[i+useful_data[j]];
			
			
			for (int j=0; j<11; j++)
				data.row[cnt+20][j] = array[array.length-16-i+38+useful_data[j]];
			
			cnt++;
			if (cnt >= 20)
				break;
		}
		
		_activity.mUtils.saveData(data, T86_PATH, dh.toDisplayString().replace("/", ""));
		return data;
	}
	
	private Stock getStock()
	{
		if (debug) Log.d("JSOUP", "+getStock()");
		Stock stock = new Stock();
		Document doc = null;
		try {
			doc = Jsoup.connect("https://tw.stock.yahoo.com/d/i/major.html").get();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			_activity.runOnUiThread(new Runnable() {
			    public void run() {
					Toast toast = Toast.makeText(_activity, R.string.network_error, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
			    }
			});
			return null;
		}

		Elements elements1 = doc.select("tr[bgcolor$=#ffffff]:contains(買進)");
		//集中
		Elements elements2 = elements1.get(0).select("td[align$=CENTER]");
		stock.major_r[0][1] = elements2.get(6).text();
		stock.major_r[1][1] = elements2.get(0).text();
		stock.major_r[2][1] = elements2.get(5).text();
		stock.major_r[3][1] = String.format("%.02f", Float.parseFloat(stock.major_r[0][1]) +
											Float.parseFloat(stock.major_r[1][1]) +
											Float.parseFloat(stock.major_r[2][1]));
		Log.e("","@@@@@ stock.major_r[0][1]" + stock.major_r[0][1]);
		Log.e("","@@@@@ stock.major_r[1][1]" + stock.major_r[1][1]);
		Log.e("","@@@@@ stock.major_r[2][1]" + stock.major_r[2][1]);
		
		//店頭
		elements2 = elements1.get(1).select("td[align$=CENTER]");
		stock.major2_r[0][1] = elements2.get(2).text();
		stock.major2_r[1][1] = elements2.get(0).text();
		stock.major2_r[2][1] = elements2.get(1).text();
		stock.major2_r[3][1] = String.format("%.02f", Float.parseFloat(stock.major2_r[0][1]) +
											Float.parseFloat(stock.major2_r[1][1]) +
											Float.parseFloat(stock.major2_r[2][1]));
		
		elements1 = doc.select("tr[bgcolor$=#ffffff]:contains(賣出)");
		//集中
		elements2 = elements1.get(0).select("td[align$=CENTER]");
		stock.major_r[0][2] = elements2.get(6).text();
		stock.major_r[1][2] = elements2.get(0).text();
		stock.major_r[2][2] = elements2.get(5).text();
		stock.major_r[3][2] = String.format("%.02f", Float.parseFloat(stock.major_r[0][2]) +
											  Float.parseFloat(stock.major_r[1][2]) +
											  Float.parseFloat(stock.major_r[2][2]));
		//店頭
		elements2 = elements1.get(1).select("td[align$=CENTER]");
		stock.major2_r[0][2] = elements2.get(2).text();
		stock.major2_r[1][2] = elements2.get(0).text();
		stock.major2_r[2][2] = elements2.get(1).text();
		stock.major2_r[3][2] = String.format("%.02f", Float.parseFloat(stock.major2_r[0][2]) +
												Float.parseFloat(stock.major2_r[1][2]) +
											    Float.parseFloat(stock.major2_r[2][2]));
		
		elements1 = doc.select("tr[bgcolor$=#ffffff]:contains(買賣超)");
		//集中
		elements2 = elements1.get(0).select("td[align$=CENTER]");
		stock.major_r[0][3] = elements2.get(6).text();
		stock.major_r[1][3] = elements2.get(0).text();
		stock.major_r[2][3] = elements2.get(5).text();
		
		//店頭
		elements2 = elements1.get(1).select("td[align$=CENTER]");
		stock.major2_r[0][3] = elements2.get(2).text();
		stock.major2_r[1][3] = elements2.get(0).text();
		stock.major2_r[2][3] = elements2.get(1).text();
		
		elements1 = doc.select("FONT[COLOR$=#FF0000]");
		stock.major_r[3][3] = elements1.get(0).text();
		stock.major2_r[3][3] = elements1.get(1).text();
		
		elements1 = doc.select("td[class$=tt]");//"td[class$=tt]:contains(資料日期)");
		stock.date = elements1.get(0).text().toString();
		stock.date = stock.date.substring(stock.date.indexOf("：") + 1);
		stock.date = stock.date.replace("/", " ");
		String[] words = stock.date.split(" ");
		stock.year = String.valueOf((Integer.parseInt(words[0]) + 1911));
		stock.month = words[1];
		stock.date = words[2];
		try {
			doc = Jsoup.connect("https://tw.stock.yahoo.com/d/i/credit.html").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements =  doc.select("td[align$=center]:matches([0-9,.])");
		for (int i = 0; i < 7 ; i++)
		{
			for (int j = 0; j < 7 ; j ++)
			{
				if (j == 0)
				{
					stock.credit[i][j] = elements.get(i*7 + j).text().substring(4);
				}
				else
				{
					stock.credit[i][j] = elements.get(i*7 + j).text();
				}
			}
		}
		_activity.getSharedPreferences("stock",  Context.MODE_PRIVATE).edit().putString("date", stock.date).commit();
		if (debug) Log.d("JSOUP", "saveData path:" + STOCK_PATH + "stock");
		_activity.mUtils.saveData(stock, STOCK_PATH, "stock");
		if (debug) Log.d("JSOUP", "-getStock()");
		return stock;
	}
}
