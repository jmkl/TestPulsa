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

public class ChangePin extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	EditText txtPinOld, txtPinNew;
	Button btnChangePin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pin);

		// find view
		txtPinOld = (EditText) findViewById(R.id.textPinOld);
		txtPinNew = (EditText) findViewById(R.id.textPinNew);
		btnChangePin = (Button) findViewById(R.id.btn_changePin);

		// set listener
		txtPinOld.addTextChangedListener(this);
		txtPinNew.addTextChangedListener(this);
		btnChangePin.setEnabled(true);
		btnChangePin.setOnClickListener(new ChangeButtonOnClick());

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
			ArmHelpers.verifyPinNumber(s);
			btnChangePin.setEnabled(true);
			txtPinNew.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnChangePin.setEnabled(false);
			txtPinNew.setTextColor(Color.RED);
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

	private class ChangeButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String pinOld, pinNew;
			pinOld = txtPinOld.getText().toString();
			pinNew = txtPinNew.getText().toString();

			final String smsMessage = String.format("S.%s.%s", pinOld, pinNew);

			ArmHelpers.sendSMS(ChangePin.this, "5556", smsMessage);
		}
	}
}
