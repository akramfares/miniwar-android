package com.faresgames.miniwar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;


public class Player extends Activity{
	private String player_file;
	private static int id;
	private static String username;
	private static String email;
	private static String access;
	private static String country;
	private static String uid;
	private static String timestamp;
	private static int gold;
	private static int xp;
	private static int mine;
	private static int soldier;
	private static int knight;
	private static int archer;
	private static int hero;
	private static Context c;
	
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
		Player.id = id;
		register();
	}
	public void setUsername(String username) {
		Player.username = username;
		register();
	}
	public void setEmail(String email) {
		Player.email = email;
		register();
	}
	public void setAccess(String access) {
		Player.access = access;
		register();
	}
	public void setCountry(String country) {
		Player.country = country;
		register();
	}
	public void setTimestamp(String timestamp) {
		Player.timestamp = timestamp;
		register();
	}
	public void setGold(int gold) {
		Player.gold = gold;
		register();
	}
	public void setXp(int xp) {
		Player.xp = xp;
		register();
	}
	public void setMine(int mine) {
		Player.mine = mine;
		register();
	}
	public void upgradeMine() {
		this.setMine(mine+1);
	}
	public void setSoldier(int soldier) {
		Player.soldier = soldier;
		register();
	}
	public void setKnight(int knight) {
		Player.knight = knight;
		register();
	}
	public void setArcher(int archer) {
		Player.archer = archer;
		register();
	}
	public void setHero(int hero) {
		Player.hero = hero;
		register();
	}
	public void setUid(String uid) {
		Player.uid = uid;
		register();
	}
	public void setPlayer_file(String player_file) {
		this.player_file = player_file;
	}
	
	private static void writeFile(String file, String text){
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
	
	
}
