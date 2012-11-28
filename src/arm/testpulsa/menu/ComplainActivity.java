package arm.testpulsa.menu;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import arm.testpulsa.ArmPulsaAddressMalformedException;
import arm.testpulsa.R;
import arm.testpulsa.TextHelper;

public class ComplainActivity extends Activity implements TextWatcher {

	EditText txtKomplainNum, txtKomplainText;
	Button btnKomplain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complain);

		// find view
		txtKomplainNum = (EditText) findViewById(R.id.text_KomplainNum);
		txtKomplainText = (EditText) findViewById(R.id.text_KomplainText);
		btnKomplain = (Button) findViewById(R.id.btn_komplain);

		// set listener
		txtKomplainNum.addTextChangedListener(this);
		txtKomplainText.addTextChangedListener(this);
		btnKomplain.setEnabled(true);
		btnKomplain.setOnClickListener(new KomplainButtonOnClick());

	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			TextHelper.verifyPhoneNumber(s);
			btnKomplain.setEnabled(true);
			txtKomplainNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnKomplain.setEnabled(false);
			txtKomplainNum.setTextColor(Color.RED);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	public void sendSMS(String phoneNo, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(
				getApplicationContext(), 0, new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "Request Sent",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "Request Sent",
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS tidak terkirim",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
	}

	public class KomplainButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String kompNum, kompText;
			kompNum = txtKomplainNum.getText().toString();
			kompText = txtKomplainText.getText().toString();

			final String smsMessage = String.format("K.%s.%s", kompNum,
					kompText);

			sendSMS("+6287792021743", smsMessage);

		}

	}

}
