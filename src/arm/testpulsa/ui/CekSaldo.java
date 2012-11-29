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

public class CekSaldo extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	EditText txtPinSaldo;
	Button btnCekSaldo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cek_saldo);

		// find view
		txtPinSaldo = (EditText) findViewById(R.id.textPinSaldo);
		btnCekSaldo = (Button) findViewById(R.id.btn_cekSaldo);

		// setting listener
		txtPinSaldo.addTextChangedListener(this);
		btnCekSaldo.setEnabled(false);
		btnCekSaldo.setOnClickListener(new CekButtonOnclick());

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
			btnCekSaldo.setEnabled(true);
			txtPinSaldo.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCekSaldo.setEnabled(false);
			txtPinSaldo.setTextColor(Color.RED);
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

	public class CekButtonOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String pinCekSaldo;
			pinCekSaldo = txtPinSaldo.getText().toString();

			final String smsMessage = String.format("S.%s", pinCekSaldo);

			ArmHelpers.sendSMS(CekSaldo.this, "+6287792021743", smsMessage);
		}

	}
}
