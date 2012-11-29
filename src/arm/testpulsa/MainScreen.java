package arm.testpulsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import arm.testpulsa.about.AboutApp;

public class MainScreen extends Activity implements OnClickListener {
	private static final String TAG = MainScreen.class.getSimpleName();
	private Button btnSMS, btnReport, btnSettings, btnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);

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

	private void getToast() {
		Toast.makeText(MainScreen.this, "Alun ado lei ndan!",
				Toast.LENGTH_SHORT).show();
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
			getToast();
			break;
		case R.id.btn_settings:
			Intent settings = new Intent(getApplicationContext(), Preferences.class);
			startActivity(settings);
			break;
		default:
			break;
		}
	}

}
