package com.faresgames.miniwar.compte;

import com.actionbarsherlock.R;
import com.faresgames.miniwar.PlayerMW;

import android.os.Bundle;
import android.widget.TextView;

public class CompteActivity extends CompteActionItems {

	PlayerMW Player = new PlayerMW(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Player.Load();
		TextView username, email, country, gold, xp, mine, soldiers, knights, archers, heros;
		username = (TextView) findViewById(R.id.compte_username);
		email = (TextView) findViewById(R.id.compte_email);
		country = (TextView) findViewById(R.id.compte_country);
		gold = (TextView) findViewById(R.id.compte_gold);
		xp = (TextView) findViewById(R.id.compte_xp);
		mine = (TextView) findViewById(R.id.compte_mine);
		soldiers = (TextView) findViewById(R.id.compte_soldiers);
		knights = (TextView) findViewById(R.id.compte_knights);
		archers = (TextView) findViewById(R.id.compte_archers);
		heros = (TextView) findViewById(R.id.compte_heros);
		username.setText(Player.getUsername());
		email.setText(Player.getEmail());
		country.setText(Player.getCountry());
		gold.setText(Player.getGold() + "");
		xp.setText(Player.getXp() + "");
		mine.setText(Player.getMine() + "");
		soldiers.setText(Player.getSoldier() + "");
		knights.setText(Player.getKnight() + "");
		archers.setText(Player.getArcher() + "");
		heros.setText(Player.getHero() + "");
	}
}