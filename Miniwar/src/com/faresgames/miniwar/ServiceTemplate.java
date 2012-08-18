package com.faresgames.miniwar;

import com.faresgames.miniwar.dashboard.Mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceTemplate extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Player player = new Player(context);
		Mine.init(context);
		int production = Mine.getProduction(player.getMine());
		player.setGold(player.getGold()+production );

		Log.e("UPDATE GOLD", "updated : "+ player.getGold());
		Notification notification = new Notification(context);
		notification.addNotification(new Notification("gold_add", "+"+production+" Gold"));
	}
	
}