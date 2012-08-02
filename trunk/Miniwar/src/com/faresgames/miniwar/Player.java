package com.faresgames.miniwar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Player extends Activity{
	private String player_file;
	private int id;
	private String username;
	private String email;
	private String access;
	private String country;
	private String uid;
	private String timestamp;
	private int gold;
	private int xp;
	private int mine;
	private int soldier;
	private int knight;
	private int archer;
	private int hero;
	private Context c;
	
	public Player(Context context){
		c = context;
		if(exists()){
			try {
				JSONObject data = Util.parseJson(player_file);
				id = data.getInt("id");
				username = data.getString("username");
				email = data.getString("email");
				access = data.getString("access_token");
				country = data.getString("country");
				uid = data.getString("uid");
				timestamp = data.getString("timestamp");
				gold = data.getInt("gold");
				xp = data.getInt("xp");
				mine = data.getInt("mine");
				soldier = data.getInt("soldier");
				knight = data.getInt("knight");
				archer = data.getInt("archer");
				hero = data.getInt("hero");
				
			} catch (FacebookError e) {
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("PLAYER JSON", e.toString());
			}
		}
	}
	public boolean exists(){
		player_file = readFile("mw_player");
		if(!player_file.equals("0")) return true;
		else return false;
	}
	public void register(){
		JSONObject player_data = new JSONObject();
		try {
			player_data.put("id", id);
			player_data.put("username", username);
			player_data.put("access_token", access);
			player_data.put("email", email);
			player_data.put("country", country);
			player_data.put("uid", uid);
			player_data.put("timestamp", timestamp);
			player_data.put("gold", gold);
			player_data.put("xp", xp);
			player_data.put("mine", mine);
			player_data.put("soldier", soldier);
			player_data.put("knight", knight);
			player_data.put("archer", archer);
			player_data.put("hero", hero);
			// We create the player file
			writeFile("mw_player", player_data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public String getAccess() {
		return access;
	}
	public String getCountry() {
		return country;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public int getGold() {
		return gold;
	}
	public int getXp() {
		return xp;
	}
	public int getMine() {
		return mine;
	}
	public int getSoldier() {
		return soldier;
	}
	public int getKnight() {
		return knight;
	}
	public int getArcher() {
		return archer;
	}
	public int getHero() {
		return hero;
	}
	public String getUid() {
		return uid;
	}
	public String getPlayer_file() {
		return player_file;
	}
	
	public void setId(int id) {
		this.id = id;
		register();
	}
	public void setUsername(String username) {
		this.username = username;
		register();
	}
	public void setEmail(String email) {
		this.email = email;
		register();
	}
	public void setAccess(String access) {
		this.access = access;
		register();
	}
	public void setCountry(String country) {
		this.country = country;
		register();
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
		register();
	}
	public void setGold(int gold) {
		this.gold = gold;
		register();
	}
	public void setXp(int xp) {
		this.xp = xp;
		register();
	}
	public void setMine(int mine) {
		this.mine = mine;
		register();
	}
	public void setSoldier(int soldier) {
		this.soldier = soldier;
		register();
	}
	public void setKnight(int knight) {
		this.knight = knight;
		register();
	}
	public void setArcher(int archer) {
		this.archer = archer;
		register();
	}
	public void setHero(int hero) {
		this.hero = hero;
		register();
	}
	public void setUid(String uid) {
		this.uid = uid;
		register();
	}
	public void setPlayer_file(String player_file) {
		this.player_file = player_file;
	}
	
	private void writeFile(String file, String text){
		FileOutputStream fos;
		try {
			fos = c.openFileOutput(file, Context.MODE_PRIVATE);
			fos.write(text.getBytes());
        	fos.close();
		} catch (FileNotFoundException e) {
			Toast.makeText(this, "ERROR: can't open file", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(this, "ERROR: can't write file", Toast.LENGTH_SHORT).show();
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
	
	
}
