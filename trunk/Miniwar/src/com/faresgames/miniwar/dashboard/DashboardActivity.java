package com.faresgames.miniwar.dashboard;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.facebook.android.Util;
import com.faresgames.miniwar.Player;
import com.faresgames.miniwar.R;

public class DashboardActivity extends DashboardActionItems {
	Facebook facebook = new Facebook("121460807998402");
	private Dialog dialog;
	Player player;
	protected Timer timeTicker= new Timer("Ticker");
    private     Handler timerHandler    = new Handler();
    protected   int     timeTickDown        = 10;
    private MyTimerTask tick = new MyTimerTask();
    private int mineCount;
    private int now;
    
	@Override
	public void setContent(TextView view) {
		 player = new Player(this);
		 Mine.init(this);
		 now = (int) (System.currentTimeMillis()/1000);
		 if((mineCount = Mine.getUpgradeTime() - now) > 0)
			timeTicker.scheduleAtFixedRate(tick, 0, 100);
		 
        /*
         * Get existing access_token if any
         */
        Bundle tokenBundle = getIntent().getExtras();
        if(tokenBundle != null){
	        String access_token = tokenBundle.getString("access_token");
	        long expires = tokenBundle.getLong("access_expires");
	        
	        if(access_token != null) {
	            facebook.setAccessToken(access_token);
	        }
	        if(expires != 0) {
	            facebook.setAccessExpires(expires);
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
        }
        
        if(player.exists()){
				super.setTitle("Welcome, "+ player.getUsername());
				TextView MineLevel = (TextView)findViewById(R.id.MineLevel);
				MineLevel.setText("Level "+player.getMine());
				TextView MineProduction = (TextView)findViewById(R.id.MineProduction);
				MineProduction.setText(""+Mine.getProduction(player.getMine())+" Gold/Hour");
				
				dialog = new Dialog(this);
				dialog.setTitle("Upgrade Mine");
				dialog.setContentView(R.layout.minedialog);
		        dialog.setCancelable(true);
		        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		        setupUpgradeMine();
        }
    }

	private void setupUpgradeMine() {
		Button button = (Button) findViewById(R.id.MineUpgrade);
        button.setOnClickListener(new OnClickListener() {
        
			@Override
			public void onClick(View v) {
				dialog.show();
				/* Current Level */
		        TextView MineCurrentLevel = (TextView) dialog.findViewById(R.id.MineCurrentLevel);
		        MineCurrentLevel.setText(Html.fromHtml("Level "+player.getMine()+"<br/><small><font color=\"red\">(Current)</font></small>"));
		        /* Current Level Infos */
		        TextView MineCurrentInfos = (TextView) dialog.findViewById(R.id.MineCurrentInfos);
		        MineCurrentInfos.setText(Html.fromHtml("Production: <br /><b>"+Mine.getProduction(player.getMine())+"Gold/Hour</b>"));
		        
		        /* Next Level */
		        TextView MineNextLevel = (TextView) dialog.findViewById(R.id.MineNextLevel);
		        MineNextLevel.setText(Html.fromHtml("Level "+(player.getMine()+1)+"<br/><small><font color=\"red\">(Next)</font></small>"));
		        /* Next Level Infos */
		        TextView MineNextInfos = (TextView) dialog.findViewById(R.id.MineNextInfos);
		        MineNextInfos.setText(Html.fromHtml("Production: <br /><b>"+Mine.getProduction(player.getMine()+1)+"Gold/Hour</b>" +
		        		"<br/> Price: <br /><b>"+Mine.getPrice(player.getMine()+1)+"Gold</b>" +
		        		"<br/> Time: <br /><b>"+SecToString(Mine.getTime(player.getMine()+1))+"</b>"));
		        
		        /* Configure Upgrade Button */
		        TextView MineButtonUpgrade = (TextView) dialog.findViewById(R.id.MineButtonUpgrade);
		        if(player.getGold()<Mine.getPrice(player.getMine()+1)){
		        	MineButtonUpgrade.setVisibility(Button.GONE);
		        }
		        
		        MineButtonUpgrade.setOnClickListener(new OnClickListener(){


					@Override
					public void onClick(View arg0) {
						player.setGold(player.getGold() - Mine.getPrice(player.getMine()+1));
						Mine.setUpgrade(player.getMine()+1);
						timeTicker= new Timer("Ticker");
						tick = new MyTimerTask();
						timeTicker.scheduleAtFixedRate(tick, 0, 100);
						now = (int) (System.currentTimeMillis()/1000);
						mineCount = Mine.getUpgradeTime() - now;
						dialog.dismiss();
						Toast.makeText(DashboardActivity.this, "Mine under construction...", Toast.LENGTH_LONG).show();
					}
		        	
		        });
		        
			}
        	
        });
	}

	private class MyTimerTask extends TimerTask {
		  @Override
		  public void run() {
            myTickTask();
        }
    };
 // Override this in Subclass to get or add specific tick behaviors
    protected void myTickTask() {
        if (timeTickDown == 0) { 
            timerHandler.post(doUpdateTimeout);
        }
        timeTickDown--;

    }

    private Runnable doUpdateTimeout = new Runnable() {
        public void run() {
            updateTimeout();
        }
    };
	

    private void updateTimeout() {
        timeTickDown = 10; // 10* 100ms == once a second
        TextView MineLevel = (TextView)findViewById(R.id.MineLevel);
        Button MineUpgrade = (Button)findViewById(R.id.MineUpgrade);
        if(mineCount-->0){
        	MineLevel.setText(SecToString(mineCount));
        	MineUpgrade.setVisibility(Button.INVISIBLE);
        }
        else {
        	// Delete the upgrading time
        	Mine.finishUpgrade();
        	// Upgrade the mine
        	player.upgradeMine();
        	// Update the Level of the mine
        	MineLevel.setText("Level "+ player.getMine());
        	// Set the button visible
        	MineUpgrade.setVisibility(Button.VISIBLE);
        	// Cancel the runnable
        	tick.cancel();
        	timeTicker.cancel();
        	// Update the Production of the mine
        	TextView MineProduction = (TextView)findViewById(R.id.MineProduction);
        	MineProduction.setText(""+Mine.getProduction(player.getMine())+" Gold/Hour");
        }
    }
    
    private String SecToString(int s){
    	return String.format("%d:%02d:%02d", s/3600, (s%3600)/60, (s%60));
    }
    
}