package com.aloha.stock;

import java.io.Serializable;
import java.util.ArrayList;

import org.jsoup.select.Elements;

public class Stock implements Serializable {
	public String date;
	public String year;
	public String month;
	public String[][] major_r = new String[4][4];
	public String[][] major2_r = new String[4][4];
	public String[][] credit = new String[7][7];
}
