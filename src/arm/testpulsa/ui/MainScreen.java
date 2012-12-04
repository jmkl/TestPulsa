package arm.testpulsa.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import arm.testpulsa.Preferences;
import arm.testpulsa.R;
import arm.testpulsa.TestPulsaConfiguration;
import arm.testpulsa.ui.dialogs.PinDialog;

public class MainScreen extends Activity implements OnClickListener {
	private static final String TAG = MainScreen.class.getSimpleName();
	private Button btnSMS, btnReport, btnSettings, btnAbout;
	private PinDialog mPinDialog;
	private TestPulsaConfiguration mConfig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);

		mConfig = new TestPulsaConfiguration(
				PreferenceManager.getDefaultSharedPreferences(this));
		mPinDialog = new PinDialog(this, mConfig);
		showPinDialogIfPinNotSet();

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

	/*
	 * Fcuk this....
	 * Prevent activity called onDestroy onOrientationChanged
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		clearUserPinPreference();
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mPinDialog.isShowing())
			mPinDialog.dismiss();
	}

	private void clearUserPinPreference() {
		Log.d(TAG,
				"deletePinOnDestroyConfig: "
						+ String.valueOf(mConfig.deletePinOnDestroy));
		if (mConfig.deletePinOnDestroy) {
			mConfig.setPIN("");
		}
	}

	private void showPinDialogIfPinNotSet() {
		if (mConfig.userPin.length() < 4 && mConfig.userPin.equals("")) {
			mPinDialog.show();
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
			Intent about = new Intent(getApplicationContext(),
					AgentControl.class);
			startActivity(about);
			break;
		case R.id.btn_report:
			Intent user = new Intent(getApplicationContext(),
					UserActivity.class);
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
