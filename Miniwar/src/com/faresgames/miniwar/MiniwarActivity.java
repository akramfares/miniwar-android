package com.faresgames.miniwar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class MiniwarActivity extends ActionItems {
	Facebook facebook = new Facebook("121460807998402");
    private SharedPreferences mPrefs;
    String player_data = null;

	@Override
	public void setContent(TextView view) {
		player_data = readFile("mw_player");
        
        /*
         * Get existing access_token if any
         */
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        /*
         * Only call authorize if the access_token has expired.
         */
        if(!facebook.isSessionValid()) {
        	
            facebook.authorize(this, new String[] { "email", "publish_actions" }, new DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                }
    
				@Override
                public void onFacebookError(FacebookError error) {}
    
				@Override
                public void onError(DialogError e) {}
    
                
				@Override
                public void onCancel() {}
            });
            
        }
        
        if(facebook.isSessionValid()){// && player_data.equals("0")){
        	try {
				// We retrieve information from facebook and other
        		JSONObject fbJson_data = Util.parseJson(facebook.request("me"));
				
				final String uid = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
				TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        	 	
				int id = fbJson_data.getInt("id");
				String username = fbJson_data.getString("first_name")+" "+ fbJson_data.getString("last_name");
				String email = fbJson_data.getString("email");
				String access = facebook.getAccessToken();
				String country = tm.getSimCountryIso();
				Date date= new Date();
				String timestamp = new Timestamp(date.getTime()).toString();
				
				// We create the JSON object of the player
				JSONObject player_data = new JSONObject();
				player_data.put("id", id);
				player_data.put("username", username);
				player_data.put("access_token", access);
				player_data.put("email", email);
				player_data.put("country", country);
				player_data.put("uid", uid);
				player_data.put("timestamp", timestamp);
				player_data.put("gold", 1000);
				player_data.put("xp", 0);
				player_data.put("mine", 1);
				player_data.put("soldier", 0);
				player_data.put("knight", 0);
				player_data.put("archer", 0);
				player_data.put("hero", 0);
				
				// We create the player file
				writeFile("mw_player", player_data.toString());
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("Erreur JSON : ",e.toString());
			}
        }
        if(!player_data.equals("0")){
        	//view.setText(player_data);
        	
        	try {
        		JSONObject data = Util.parseJson(player_data);
				super.setTitle("Welcome, "+ data.getString("username"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    private void writeFile(String file, String text){
		FileOutputStream fos;
		try {
			fos = this.openFileOutput(file, Context.MODE_PRIVATE);
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
			FileInputStream fis = this.openFileInput(file);
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