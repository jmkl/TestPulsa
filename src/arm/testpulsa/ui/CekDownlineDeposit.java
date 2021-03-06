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

public class CekDownlineDeposit extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	
	EditText txtDownDepNum, txtDownDepPin;
	Button btnSenDownPin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cek_deposit_downline);

		// find view
		txtDownDepNum = (EditText) findViewById(R.id.text_downDepositNum);
		txtDownDepPin = (EditText) findViewById(R.id.text_downDepositPin);
		btnSenDownPin = (Button) findViewById(R.id.btn_sendDownDeposit);

		// set listener
		txtDownDepNum.addTextChangedListener(this);
		txtDownDepPin.addTextChangedListener(this);
		btnSenDownPin.setEnabled(true);
		btnSenDownPin.setOnClickListener(new DepositButtonOnClick());

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
			btnSenDownPin.setEnabled(true);
			txtDownDepNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnSenDownPin.setEnabled(false);
			txtDownDepNum.setTextColor(Color.RED);
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

	private class DepositButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String downDepNum, downDepPin;
			downDepNum = txtDownDepNum.getText().toString();
			downDepPin = txtDownDepPin.getText().toString();

			final String smsMessage = String.format("CEKDEP.%s.%s", downDepNum, downDepPin);

			ArmHelpers.sendSMS(CekDownlineDeposit.this, "5556", smsMessage);
		}
	}
}
