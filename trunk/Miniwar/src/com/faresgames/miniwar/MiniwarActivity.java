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

	@Override
	public void setContent(TextView view) {
		Player player = new Player(this);
        
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
        
        if(facebook.isSessionValid() && !player.exists()){
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
				
				// We create the player
				player.setId(id);
				player.setUsername(username);
				player.setAccess(access);
				player.setEmail(email);
				player.setCountry(country);
				player.setUid(uid);
				player.setTimestamp(timestamp);
				player.setGold(1000);
				player.setXp(0);
				player.setMine(1);
				player.setSoldier(0);
				player.setKnight(0);
				player.setArcher(0);
				player.setHero(0);
				player.register();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("Erreur JSON : ",e.toString());
			}
        }
        
        if(player.exists()){
				super.setTitle("Welcome, "+ player.getUsername());
				TextView MineLevel = (TextView)findViewById(R.id.MineLevel);
				MineLevel.setText("Level "+player.getMine());
				TextView MineProduction = (TextView)findViewById(R.id.MineProduction);
				int production = 34 + (player.getMine()*34)/4;
				MineProduction.setText(""+production+" Gold/Hour");
				
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

}