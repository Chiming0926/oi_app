package com.aloha.stock;

import java.io.Serializable;
import java.util.ArrayList;

//single data
class _txo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String date;
	String value;
	String amount;
}

//list for buy and sell
public class Txo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String date;
	double pc;
	ArrayList <_txo> buylist;
	ArrayList <_txo> selllist;			
}