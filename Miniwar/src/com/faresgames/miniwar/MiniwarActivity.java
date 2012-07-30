package com.faresgames.miniwar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class MiniwarActivity extends ActionItems {
	Facebook facebook = new Facebook("121460807998402");
	private SharedPreferences mPrefs;
	PlayerMW Player = new PlayerMW(this);

	@Override
	public void setContent(TextView view) {
		Player.Load();

		/*
		 * Get existing access_token if any
		 */
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		/*
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {

			facebook.authorize(this,
					new String[] { "email", "publish_actions" },
					new DialogListener() {
						@Override
						public void onComplete(Bundle values) {
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();
						}

						@Override
						public void onFacebookError(FacebookError error) {
						}

						@Override
						public void onError(DialogError e) {
						}

						@Override
						public void onCancel() {
						}
					});

		}

		Log.e("isLoaded", Player.isLoaded() + "");

		Log.e("isSessionValid", facebook.isSessionValid() + "");

		if (facebook.isSessionValid() && !Player.isLoaded()) {
			Log.e("ET1", "oo");
			try {
				// We retrieve information from facebook and other
				JSONObject fbJson_data = Util.parseJson(facebook.request("me"));
				final String uid = Secure.getString(this.getContentResolver(),
						Secure.ANDROID_ID);
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

				int id = fbJson_data.getInt("id");
				String username = fbJson_data.getString("first_name") + " "
						+ fbJson_data.getString("last_name");
				String email = fbJson_data.getString("email");
				String access = facebook.getAccessToken();
				String country = tm.getSimCountryIso();
				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());

				// We create the JSON object of the player
				Player.setId(id);
				Player.setUsername(username);
				Player.setAccess_token(access);
				Player.setEmail(email);
				Player.setCountry(country);
				Player.setUid(uid);
				Player.setTimestamp(timestamp);
				Player.setGold(1000);
				Player.setXp(0);
				Player.setMine(1);
				Player.setSoldier(0);
				Player.setKnight(0);
				Player.setArcher(0);
				Player.setHero(0);

				// We save the player file
				Player.Save();

			} catch (MalformedURLException e) {
				Log.e("MalformedURLException : ", e.toString());
			} catch (IOException e) {
				Log.e("IOException : ", e.toString());
			} catch (JSONException e) {
				Log.e("Erreur JSON : ", e.toString());
			}
		}

		if (Player.isLoaded()) {
			super.setTitle("Welcome, " + Player.getUsername());
			TextView MineLevel = (TextView) findViewById(R.id.MineLevel);
			MineLevel.setText("Level " + Player.getMine());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

}