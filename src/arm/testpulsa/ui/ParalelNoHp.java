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

public class ParalelNoHp extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	
	EditText txtParNum, txtPinPar;
	Button btnSendPar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pin);

		// find view
		txtParNum = (EditText) findViewById(R.id.textPinOld);
		txtPinPar = (EditText) findViewById(R.id.textPinNew);
		btnSendPar = (Button) findViewById(R.id.btn_sendPar);

		// set listener
		txtParNum.addTextChangedListener(this);
		txtPinPar.addTextChangedListener(this);
		btnSendPar.setEnabled(true);
		btnSendPar.setOnClickListener(new ChangeButtonOnClick());

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
			btnSendPar.setEnabled(true);
			txtPinPar.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnSendPar.setEnabled(false);
			txtPinPar.setTextColor(Color.RED);
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
			String parNum, parPin;
			parNum = txtParNum.getText().toString();
			parPin = txtPinPar.getText().toString();

			final String smsMessage = String.format("PAR.%s.%s",parNum, parPin);

			ArmHelpers.sendSMS(ParalelNoHp.this, "5556", smsMessage);
		}
	}
}
