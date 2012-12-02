package arm.testpulsa.ui.tab;

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

public class CekTransaksi extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	EditText txtCekTrans;
	Button btnCekTrans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cek_transaksi);

		// find view
		txtCekTrans = (EditText) findViewById(R.id.textCekTrans);
		btnCekTrans = (Button) findViewById(R.id.btn_cekTrans);

		// setting listener
		txtCekTrans.addTextChangedListener(this);
		btnCekTrans.setEnabled(false);
		btnCekTrans.setOnClickListener(new TransButtonOnclick());

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
			btnCekTrans.setEnabled(true);
			txtCekTrans.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCekTrans.setEnabled(false);
			txtCekTrans.setTextColor(Color.RED);
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

	public class TransButtonOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String pinCekTrans;
			pinCekTrans = txtCekTrans.getText().toString();

			final String smsMessage = String.format("TRX.%s", pinCekTrans);

			ArmHelpers.sendSMS(CekTransaksi.this, "+6287792021743", smsMessage);
		}

	}
}
