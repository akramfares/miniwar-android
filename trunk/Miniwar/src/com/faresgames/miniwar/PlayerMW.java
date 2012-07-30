package com.faresgames.miniwar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class PlayerMW {

	static String PLAYERMW_FILE = "mw_player";
	Context Con;

	Integer id;
	String username;
	String access_token;
	String email;
	String country;
	String uid;
	Timestamp timestamp;
	Integer gold;
	Integer xp;
	Integer mine;
	Integer soldier;
	Integer knight;
	Integer archer;
	Integer hero;
	boolean Loaded = false;

	public PlayerMW(Context Con) {
		this.Con = Con;
	}

	public JSONObject toJSONObject() {
		JSONObject player_data = new JSONObject();
		try {
			player_data.put("id", id);
			player_data.put("username", username);
			player_data.put("access_token", access_token);
			player_data.put("email", email);
			player_data.put("country", country);
			player_data.put("uid", uid);
			player_data.put("timestamp", timestamp.getTime());
			player_data.put("gold", gold);
			player_data.put("xp", xp);
			player_data.put("mine", mine);
			player_data.put("soldier", soldier);
			player_data.put("knight", knight);
			player_data.put("archer", archer);
			player_data.put("hero", hero);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("Erreur JSON : ", e.toString());
		}
		return player_data;
	}

	public void Save() {
		FileOutputStream fos;
		try {
			fos = Con.openFileOutput(PLAYERMW_FILE, Context.MODE_PRIVATE);
			fos.write(this.toJSONObject().toString().getBytes());
			fos.close();
			Loaded = true;// Si on enregistre un player cela veut dire qu'il a
							// été chargé une seule fois au moins
			Log.e("SAVED", "oo");
		} catch (FileNotFoundException e) {
			Log.e("File not found Save PLAYER: ", e.toString());
		} catch (IOException e) {
			Log.e("IOException Save PLAYER: ", e.toString());
		}

	}

	public void Load() {
		StringBuilder json = new StringBuilder();
		try {
			FileInputStream fis = Con.openFileInput(PLAYERMW_FILE);
			BufferedReader r = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = r.readLine()) != null) {
				json.append(line);
			}
			fis.close();

			JSONObject JSONPlayer = new JSONObject(json.toString());

			id = JSONPlayer.getInt("id");
			username = JSONPlayer.getString("username");
			access_token = JSONPlayer.getString("access_token");
			email = JSONPlayer.getString("email");
			country = JSONPlayer.getString("country");
			uid = JSONPlayer.getString("uid");
			timestamp = new Timestamp(JSONPlayer.getLong("timestamp"));
			gold = JSONPlayer.getInt("gold");
			xp = JSONPlayer.getInt("xp");
			mine = JSONPlayer.getInt("mine");
			soldier = JSONPlayer.getInt("soldier");
			knight = JSONPlayer.getInt("knight");
			archer = JSONPlayer.getInt("archer");
			hero = JSONPlayer.getInt("hero");
			Loaded = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("Erreur JSON Load PLAYER: ", e.toString());
		} catch (FileNotFoundException e) {
			Log.e("File not found Load PLAYER: ", e.toString());
		} catch (IOException e) {
			Log.e("IOException Load PLAYER: ", e.toString());
		}

	}

	public boolean isLoaded() {
		return Loaded;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getXp() {
		return xp;
	}

	public void setXp(Integer xp) {
		this.xp = xp;
	}

	public Integer getMine() {
		return mine;
	}

	public void setMine(Integer mine) {
		this.mine = mine;
	}

	public Integer getSoldier() {
		return soldier;
	}

	public void setSoldier(Integer soldier) {
		this.soldier = soldier;
	}

	public Integer getKnight() {
		return knight;
	}

	public void setKnight(Integer knight) {
		this.knight = knight;
	}

	public Integer getArcher() {
		return archer;
	}

	public void setArcher(Integer archer) {
		this.archer = archer;
	}

	public Integer getHero() {
		return hero;
	}

	public void setHero(Integer hero) {
		this.hero = hero;
	}
}
