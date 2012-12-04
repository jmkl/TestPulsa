package arm.testpulsa;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

public class TestPulsaConfiguration implements OnSharedPreferenceChangeListener {
	private static final String TAG = TestPulsaConfiguration.class
			.getSimpleName();

	public String userPin;
	public boolean deletePinOnDestroy;
	public String theme;

	private final SharedPreferences prefs;

	public TestPulsaConfiguration(SharedPreferences _prefs) {
		prefs = _prefs;
		prefs.registerOnSharedPreferenceChangeListener(this);
		loadPrefs(prefs);
	}

	@Override
	protected void finalize() {
		prefs.unregisterOnSharedPreferenceChangeListener(this);
	}

	public void setPIN(String value) {
		userPin = saveStringSetting("pin", value);
	}

	public String saveStringSetting(String key, String value) {
		getEditor().putString(key, value).commit();
		return value;
	}

	public SharedPreferences.Editor getEditor() {
		return prefs.edit();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Log.i(TAG, "onSharedPreferenceChanged() : " + key);
		loadPrefs(prefs);
	}

	private void loadPrefs(SharedPreferences prefs) {
		this.userPin = prefs.getString("pin", "");
		this.deletePinOnDestroy = prefs.getBoolean("deletePinOnDestroy", false);
		this.theme = prefs.getString("theme", "dark");
	}
	
	public int getTheme() {
		if (theme.equals("light")) {
			return com.actionbarsherlock.R.style.Theme_Sherlock_Light;
		} else {
			return com.actionbarsherlock.R.style.Theme_Sherlock;
		}
	}
}
