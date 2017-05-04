//SharedPreferences
package br.dc.ufscar.whoami.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {

	public void savePreferences(String key, String value,
			Activity context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String loadSavedPreferences(String key, Activity context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		String name = sharedPreferences.getString(key, "");

		return name;
	}

}
