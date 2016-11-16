package com.aloha.stock;

import java.io.Serializable;
import java.util.ArrayList;

class _future implements Serializable{
	String a;
	String b;
	int	   type;
}
public class Future implements Serializable {
	String date;
	ArrayList <_future> data = new ArrayList <_future>();
	String total1,total2;
}
