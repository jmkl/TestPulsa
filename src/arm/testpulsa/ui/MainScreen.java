package arm.testpulsa.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import arm.testpulsa.Preferences;
import arm.testpulsa.R;
import arm.testpulsa.ui.dialogs.PinDialog;

public class MainScreen extends Activity implements OnClickListener {
	private static final String TAG = MainScreen.class.getSimpleName();
	private Button btnSMS, btnReport, btnSettings, btnAbout;
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);
		
		prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		
		// find view
		btnSMS = (Button) findViewById(R.id.btn_sendSMS);
		btnReport = (Button) findViewById(R.id.btn_report);
		btnSettings = (Button) findViewById(R.id.btn_settings);
		btnAbout = (Button) findViewById(R.id.btn_about);

		btnSMS.setOnClickListener(this);
		btnReport.setOnClickListener(this);
		btnSettings.setOnClickListener(this);
		btnAbout.setOnClickListener(this);

		Log.i(TAG, "onCreate");
	}

	@Override
	protected void onResume() {
		super.onResume();
		String userPin = prefs.getString("pin", "");
		showPinDialogIfPinNotSet(userPin);
	}

	@Override
	protected void onDestroy() {
		clearUserPinPreference();
		super.onDestroy();
	}

	private void clearUserPinPreference() {
		Editor edit = prefs.edit();
		edit.putString("pin", "");
		edit.commit();
	}

	private void showPinDialogIfPinNotSet(String pin) {
		if (pin.length() < 4) {
			new PinDialog(this).show();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sendSMS:
			Intent sms = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(sms);
			break;
		case R.id.btn_about:
			Intent about = new Intent(getApplicationContext(), AboutApp.class);
			startActivity(about);
			break;
		case R.id.btn_report:
			Intent user = new Intent(getApplicationContext(), UserActivity.class);
			startActivity(user);
			break;
		case R.id.btn_settings:
			Intent settings = new Intent(getApplicationContext(),
					Preferences.class);
			startActivity(settings);
			break;
		default:
			break;
		}
	}

}
