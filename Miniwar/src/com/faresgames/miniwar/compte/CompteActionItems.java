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
package com.faresgames.miniwar.compte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.faresgames.miniwar.R;

public class CompteActionItems extends SherlockActivity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Used to put dark icons on light action bar
		// boolean isLight = true;

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.compteactions, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		// Handle item selection
		switch (item.getItemId()) {

		// case R.id.compte_back:
		// i = new Intent(CompteActionItems.this, CompteActivity.class);
		// startActivity(i);
		// return true;
		
		case R.id.compte_back:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar); // Used for theme
																// switching in
																// samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compte);
	}

	protected void setContent(TextView view) {
		view.setText(R.string.action_items_content);
	}
}
