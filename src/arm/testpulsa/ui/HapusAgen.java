package arm.testpulsa.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import arm.testpulsa.R;
import arm.testpulsa.receiver.DeliveryReceiver;
import arm.testpulsa.receiver.SentReceiver;
import arm.testpulsa.utils.ArmHelpers;
import arm.testpulsa.utils.ArmPulsaAddressMalformedException;

public class HapusAgen extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	
	EditText txtHapusNum, txtHapusPin;
	Button btnHapusAgen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hapus_agen);

		// find view
		txtHapusNum = (EditText) findViewById(R.id.text_hapusAgenNum);
		txtHapusPin = (EditText) findViewById(R.id.text_hapusAgenPin);
		btnHapusAgen = (Button) findViewById(R.id.btn_hapusAgen);

		// set listener
		txtHapusNum.addTextChangedListener(this);
		txtHapusPin.addTextChangedListener(this);
		btnHapusAgen.setEnabled(true);
		btnHapusAgen.setOnClickListener(new HapusButtonOnClick());

	}

	@Override
	protected void onPause() {
		unregisterReceiver(sentReceiver);
		unregisterReceiver(deliveryReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(sentReceiver, new IntentFilter(SMS_SENT));
		registerReceiver(deliveryReceiver, new IntentFilter(SMS_DELIVERED));
		super.onResume();
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnHapusAgen.setEnabled(true);
			txtHapusNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnHapusAgen.setEnabled(false);
			txtHapusNum.setTextColor(Color.RED);
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// nothing to do here

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// nothing to do here

	}

	private class HapusButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String parNum, parPin;
			parNum = txtHapusNum.getText().toString();
			parPin = txtHapusPin.getText().toString();

			final String smsMessage = String.format("HAPUS.%s.%s",parNum, parPin);

			ArmHelpers.sendSMS(HapusAgen.this, "5556", smsMessage);
		}
	}
}
