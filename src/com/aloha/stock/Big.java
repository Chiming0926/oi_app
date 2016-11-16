package com.aloha.stock;

import java.io.Serializable;
import java.util.ArrayList;
/*
 * tr.12bk:contains(自營商)
 */
//single data
class BigRowData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String text[] = new String[10];
}

//list for buy and sell
public class Big implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String date;
	ArrayList <BigRowData> rowData;
}