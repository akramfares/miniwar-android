package com.faresgames.miniwar.compte;

import com.actionbarsherlock.R;
import com.faresgames.miniwar.Player;
import com.faresgames.miniwar.PlayerMW;

import android.os.Bundle;
import android.widget.TextView;

public class CompteActivity extends CompteActionItems {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Player player = new Player(this);
		super.onCreate(savedInstanceState);

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
		username.setText(player.getUsername());
		email.setText(player.getEmail());
		country.setText(player.getCountry());
		gold.setText(player.getGold() + "");
		xp.setText(player.getXp() + "");
		mine.setText(player.getMine() + "");
		soldiers.setText(player.getSoldier() + "");
		knights.setText(player.getKnight() + "");
		archers.setText(player.getArcher() + "");
		heros.setText(player.getHero() + "");
	}
}