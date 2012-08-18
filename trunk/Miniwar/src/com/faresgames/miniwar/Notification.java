package com.faresgames.miniwar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Notification {

	private String type;
	private String message;
	private int date;
	private boolean read;
	private static Context c;
	private static String notification_file;
	private static JSONArray jArray;
	private static ArrayList<Notification> notifications = new ArrayList<Notification>(); 
	
	public Notification(Context context){
		c = context;
		init();
	}
	
	public Notification(String type, String message) {
		this.type = type;
		this.message = message;
		this.date = (int) (System.currentTimeMillis()/1000);
		this.read = false;
	}
	
	public Notification(String type, String message, int date, boolean read) {
		this.type = type;
		this.message = message;
		this.date = date;
		this.read = read;
	}
	
	private void init(){
		JSONObject data;
		jArray = new JSONArray();
		notifications.clear();
		if(exists()){
			try {
				
				jArray = new JSONArray(notification_file);
				for(int i=0;i<jArray.length();i++){
					data = jArray.getJSONObject(i);
					type = data.getString("type");
					message = data.getString("message");
					date = data.getInt("date");
					read = data.getBoolean("read");
					notifications.add(new Notification(type, message, date, read));
				}
			} catch (JSONException e) {
				Log.e("Notification JSON", e.toString());
			}
		}
	}
	
	public boolean exists(){
		notification_file = readFile("mw_notification");
		if(!notification_file.equals("0")) return true;
		else return false;
	}
	
	public void addNotification(Notification n){
		try {
			JSONObject notification_data = new JSONObject();
			notification_data.put("type", n.getType());
			notification_data.put("message", n.getMessage());
			notification_data.put("date", n.getDate());
			notification_data.put("read", n.isRead());
			jArray.put(notification_data);
			// Add the notification to the ArrayList
			notifications.add(n);
			// Save the new notification to the file
			writeFile("mw_notification", jArray.toString());
			Log.e("CONTENT NOTIFICATION", jArray.toString());
			
		} catch (JSONException e) {
			Log.e("ERREUR ADD NOTIFICATION", e.toString());
		}
	}
	
	public Notification getLast(){
		return notifications.get(notifications.size()-1);
	}
	
	public ArrayList<Notification> getAll(){
		return notifications;
	}

	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public String getDate() {
		return ""+date;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDate(int date) {
		this.date = date;
	} 
	
	private void writeFile(String file, String text){
		FileOutputStream fos;
		try {
			fos = c.openFileOutput(file, Context.MODE_PRIVATE);
			fos.write(text.getBytes());
        	fos.close();
		} catch (FileNotFoundException e) {
			Toast.makeText(c, "ERROR: can't open file", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(c, "ERROR: can't write file", Toast.LENGTH_SHORT).show();
		}	
	}
	private String readFile(String file) {
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
	
}
