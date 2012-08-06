/*
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.faresgames.miniwar.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.faresgames.miniwar.Player;
import com.faresgames.miniwar.R;
import com.faresgames.miniwar.army.ArmyActivity;
import com.faresgames.miniwar.compte.CompteActivity;

public class DashboardActionItems extends SherlockActivity implements ActionBar.OnNavigationListener{
	Player player;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("Gold("+player.getGold()+")")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add("XP("+player.getXp()+")")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add("Pop("+player.getPop()+")")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		player = new Player(this);
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar); 
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setContent((TextView)findViewById(R.id.MineLevel));

        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
		
	}

	protected void setContent(TextView view) {
		view.setText(R.string.action_items_content);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		Intent i;
		// Handle item selection
		switch (itemPosition) {
			case 4:
				i = new Intent(DashboardActionItems.this, CompteActivity.class);
				startActivity(i);
				return true;
			case 1:
				i = new Intent(DashboardActionItems.this, ArmyActivity.class);
				startActivity(i);
				return true;
			default:
			return false;
		}
	}
}
