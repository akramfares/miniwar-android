package com.faresgames.miniwar.dashboard;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import android.content.Context;

public class Mine {
	public static final int PRODUCTION_INIT = 34;
	public static final int PRICE_INIT = 72;
	public static final int TIME_INIT = 94;
	private static Context c; 
	
	public static void init(Context con){
		c = con;
	}
	
	public static int getProduction(int level){
		return PRODUCTION_INIT + (level*PRODUCTION_INIT)/4;
	}
	
	public static int getPrice(int level){
		return PRICE_INIT + (level*PRICE_INIT)/4;
	}
	
	public static int getTime(int level){
		return TIME_INIT + (level*TIME_INIT)/3;
	}

	public static void setUpgrade(int level) { 
		int seconds = (int) (System.currentTimeMillis()/1000 + getTime(level));
		writeFile("mw_mine",""+seconds);
	}
	
	public static void finishUpgrade() {
		writeFile("mw_mine","0");
	}
	
	public static int getUpgradeTime() {
		if(!readFile("mw_mine").equals("0"))
			return Integer.parseInt(readFile("mw_mine"));
		else return 0;
	}
	
	private static void writeFile(String file, String text){
		FileOutputStream fos;
		try {
			fos = c.openFileOutput(file, Context.MODE_PRIVATE);
			fos.write(text.getBytes());
        	fos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}	
	}
	private static String readFile(String file) {
		StringBuilder json = new StringBuilder();
		try {
			FileInputStream fis = c.openFileInput(file);
			BufferedReader r = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = r.readLine()) != null) {
			    json.append(line);
			}
			fis.close();
			return json.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "0";
		} catch (IOException e) {
			e.printStackTrace();
			return "0";
		}
	}
}
