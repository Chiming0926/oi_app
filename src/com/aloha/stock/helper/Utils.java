package com.aloha.stock.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {
	private final static boolean debug = false;

	// constructor
	public Utils(Context context) {
	}
	
	public static boolean saveData(Object obj,String dir, String fileName)
	{
		File file = new File(dir, fileName);
		boolean done = true;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file); //mContext.openFileOutput(getFloder() + fileName, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			done = false;
		}
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(fos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			done = false;
		}
		try {
			os.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			done = false;
		}
		try {
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			done = false;
		}
		return done;
	}
	
	public static Object loadData(String dir, String fileName)
	{
		File file = new File(dir, fileName);
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(fis);
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object simpleClass = null;
		if (is == null)
		{
			if (debug) Log.d("LAI","return null!!");
			return null;
		}
		try {
			simpleClass = is.readObject();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (debug) Log.d("LAI","return simpleClass!");
		return simpleClass;
	}
	
	public void createFolder(String path)
	{
		File theDir = new File(path);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
		    boolean result = theDir.mkdir();  
		     if(result) {    
//		       System.out.println("DIR created");  
		     }
		  }
	}
	
	public boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()
                     && cm.getActiveNetworkInfo().isAvailable()
                     && cm.getActiveNetworkInfo().isConnected()) {
               return true;
        }
        return false;
    }
}
