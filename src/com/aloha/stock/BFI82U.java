package com.aloha.stock;

import java.io.Serializable;
import java.util.ArrayList;

import org.jsoup.select.Elements;

public class BFI82U implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	public String date;
	public String[][] row = new String[5][3];
}
