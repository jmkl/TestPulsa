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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import arm.testpulsa.ArmPulsaAddressMalformedException;
import arm.testpulsa.R;
import arm.testpulsa.TextHelper;
import arm.testpulsa.model.HargaOperator;

public class CekHarga extends Activity implements TextWatcher {

	Spinner spnHarga;
	EditText txtPinHarga;
	Button btnCekHarga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cek_harga);

		// find view by id
		spnHarga = (Spinner) findViewById(R.id.spinnerHarga);
		txtPinHarga = (EditText) findViewById(R.id.textPinHarga);
		btnCekHarga = (Button) findViewById(R.id.btn_cekHarga);

		// set listener
		txtPinHarga.addTextChangedListener(this);
		setAdapter();
		btnCekHarga.setEnabled(false);
		btnCekHarga.setOnClickListener(new HargaButtonOnclick());

	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			TextHelper.verifyPinNumber(s);
			btnCekHarga.setEnabled(true);
			txtPinHarga.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCekHarga.setEnabled(false);
			txtPinHarga.setTextColor(Color.RED);
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

	private void setAdapter() {
		// spinner operator adapter
		ArrayAdapter<HargaOperator> spnHargaOperatorAdapter = new ArrayAdapter<HargaOperator>(
				this, android.R.layout.simple_spinner_item,
				new HargaOperator[] {
						new HargaOperator(1, "Telkomsel", "Telkomsel"),
						new HargaOperator(2, "XL", "XL"),
						new HargaOperator(3, "Indosat", "Indosat"),
						new HargaOperator(4, "Three", "Three"),
						new HargaOperator(5, "Axis", "Axis"),
						new HargaOperator(6, "Flexi", "Flexi"),
						new HargaOperator(7, "Esia", "Esia"),
						new HargaOperator(8, "Smartfren", "Smartfren"),
						new HargaOperator(9, "Ceria", "Ceria"), });
		spnHargaOperatorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnHarga.setAdapter(spnHargaOperatorAdapter);

	}

	public class HargaButtonOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String harga = null, pinCekHarga;
			pinCekHarga = txtPinHarga.getText().toString();
			HargaOperator ho = (HargaOperator) spnHarga.getSelectedItem();
			harga = String.valueOf(ho.kode);

			final String smsMessage = String.format("HRG.%s.%s", harga,
					pinCekHarga);

			sendSMS("+6287792021743", smsMessage);

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

}