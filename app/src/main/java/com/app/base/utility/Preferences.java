package com.app.base.utility;

import android.content.Context;
import android.content.SharedPreferences;

import static com.app.base.utility.Config.APP_NAME;

/**
 * Created by j3p0n on 12/30/2016.
 */
public class Preferences {
	
	private static final String PREFS_NAME = APP_NAME;
	Context c;
	
	public Preferences(Context c) {
		this.c = c;
	}
	
	public void savePreferences(String key, int value) {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		settings.edit().putInt(key, value).apply();
	}

	public void savePreferences(String key, String value) {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		settings.edit().putString(key, value).apply();
	}
	
	public void savePreferences(String key, boolean value) {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		settings.edit().putBoolean(key, value).apply();
	}
	
	public int getPreferencesInt(String key) {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		return settings.getInt(key, Integer.MIN_VALUE);
	}

	public String getPreferencesString(String key) {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		return settings.getString(key, null);
	}
	
	public boolean getPreferencesBoolean(String key) {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		return settings.getBoolean(key, false);
	}
	
	public void clearAllPreferences() {
		SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		settings.edit().clear().apply();
	}
	

}