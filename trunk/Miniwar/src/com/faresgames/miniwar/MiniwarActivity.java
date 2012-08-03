package com.faresgames.miniwar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.faresgames.miniwar.dashboard.DashboardActivity;

public class MiniwarActivity extends ActionItems {
	Facebook facebook = new Facebook("121460807998402");
    private SharedPreferences mPrefs;

	@Override
	public void setContent(TextView view) {
		Player player = new Player(this);
		final Intent dashboard = new Intent(this, DashboardActivity.class);
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
        
        
        if(player.exists()){
        	startActivity(dashboard);
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
                    
                    Bundle tokenBundle = new Bundle();
                    tokenBundle.putString("access_token", facebook.getAccessToken());
                    tokenBundle.putLong("access_expires", facebook.getAccessExpires());
                    dashboard.putExtras(tokenBundle);
					startActivity(dashboard);
                }
    
				@Override
                public void onFacebookError(FacebookError error) {}
    
				@Override
                public void onError(DialogError e) {}
    
                
				@Override
                public void onCancel() {}
            });
            
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

}