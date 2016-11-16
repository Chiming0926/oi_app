package com.aloha.stock;

import java.io.Serializable;
import java.util.ArrayList;
/*
 * tr.12bk:contains(自營商)
 */
//single data
class TxoDetail implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;//自營商,投信 or 外資
	String buy; //買方
	String sell;//賣方
	String diff;//買賣差
}

//list for buy and sell
public class TxoNew implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String date;
//	double pc;
	ArrayList <TxoDetail> buyTxo;//買權
	ArrayList <TxoDetail> sellTxo;//賣權
}