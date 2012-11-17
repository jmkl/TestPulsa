package arm.testpulsa;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	EditText txtPhone, txtNominal, txtUserPin;
	Spinner spnOperator;
	Spinner spnNominal;
	RadioGroup groupRadio;
	RadioButton rdoPredefined;
	RadioButton rdoManual;
	Button btnSendForm;
	
	private static String userPin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		userPin = prefs.getString("pin", "");
		
		showPinDialogIfPinNotSet(userPin);

		// Find view
		txtPhone = (EditText) findViewById(R.id.textPhoneNumber);
		txtNominal = (EditText) findViewById(R.id.textNominalValue);
		spnOperator = (Spinner) findViewById(R.id.spinnerOperator);
		spnNominal = (Spinner) findViewById(R.id.spinnerNominal);
		groupRadio = (RadioGroup) findViewById(R.id.radiogroupNominal);
		rdoPredefined = (RadioButton) findViewById(R.id.radioSpinner);
		rdoManual = (RadioButton) findViewById(R.id.radioText);
		btnSendForm = (Button) findViewById(R.id.btn_sendForm);

		// set listener
		groupRadio.setOnCheckedChangeListener(new GroupRadioCheckedChange());
		btnSendForm.setOnClickListener(new SendButtonOnClick());

		// spinner nominal adapter
		ArrayAdapter<OperatorOption> spnOperatorOptionAdapter = new ArrayAdapter<OperatorOption>(
				this, android.R.layout.simple_spinner_item, new OperatorOption[] {
						new OperatorOption(1, "Telkomsel", "S"),
						new OperatorOption(2, "Telkomsel Transfer", "P"),
						new OperatorOption(3, "Flexi", "F"),
						new OperatorOption(4, "Flexi Transfer", "FT"),
						new OperatorOption(5, "XL", "X"),
						new OperatorOption(6, "XL Transfer", "XT"),
						new OperatorOption(7, "Indosat", "I"),
						new OperatorOption(8, "Indosat Transfer", "IT"),
						new OperatorOption(9, "Indosat SMS", "IS"),
						new OperatorOption(10, "Indosat Internet", "IG"),
						new OperatorOption(11, "3", "T"),
						new OperatorOption(12, "Axis", "AX"),
						new OperatorOption(13, "Axis Transfer", "AXT"),
						new OperatorOption(14, "Esia", "E"),
						new OperatorOption(15, "Esia Transfer", "ET"),
						new OperatorOption(16, "Smartfren", "V"),
						new OperatorOption(17, "Ceria", "C")});
		spnOperatorOptionAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnOperator.setAdapter(spnOperatorOptionAdapter);

		Log.i(TAG, "onCreate");
		
		// spinner nominal adapter
		ArrayAdapter<NominalValue> spnNominalAdapter = new ArrayAdapter<NominalValue>(
				this, android.R.layout.simple_spinner_item, new NominalValue[] {
						new NominalValue(1, "Rp. 5.000", 5),
						new NominalValue(2, "Rp. 10.000", 10),
						new NominalValue(3, "Rp. 15.000", 15),
						new NominalValue(4, "Rp. 20.000", 20),
						new NominalValue(5, "Rp. 25.000", 25),
						new NominalValue(6, "Rp. 50.000", 50),
						new NominalValue(7, "Rp. 100.000", 100) });
		spnNominalAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnNominal.setAdapter(spnNominalAdapter);

		Log.i(TAG, "onCreate");
	}
	
	private void showPinDialogIfPinNotSet(String pin) {
		if (pin.length() < 3) {
			new PinDialog(this).show();
		}
	}
	
	private class SendButtonOnClick implements OnClickListener {

		public void onClick(View v) {
			String telpNumber, operator = null, value = null;
			NominalValue nv = (NominalValue) spnNominal.getSelectedItem();
			telpNumber = txtPhone.getText().toString();
			/* userPin = txtUserPin.getText().toString(); not used */ 
			OperatorOption op = (OperatorOption) spnOperator.getSelectedItem();
			if (rdoPredefined.isChecked()) {
				value = String.valueOf(nv.value);
				operator = String.valueOf(op.kode);
			} else if (rdoManual.isChecked()) {
				value = txtNominal.getText().toString();
			}

			// Send SMS
			String smsMessage = String.format(
					"%s%s.%s.%s",
					operator, value, telpNumber, userPin);
			sendSMS("5556", smsMessage); // don't use personal phone number
			Log.d(TAG, "onSendButton Clicked, Send SMS will be:\n" + smsMessage);
		}
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
					Toast.makeText(getBaseContext(), "SMS sent",
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
					Toast.makeText(getBaseContext(), "SMS terkirim",
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

	private class GroupRadioCheckedChange implements OnCheckedChangeListener {

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.radioSpinner:
				txtNominal.setVisibility(View.GONE);
				spnNominal.setVisibility(View.VISIBLE);
				break;
			case R.id.radioText:
				txtNominal.setVisibility(View.VISIBLE);
				spnNominal.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}

	}
}