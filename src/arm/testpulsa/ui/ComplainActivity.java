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

public class ComplainActivity extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
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

	public class KomplainButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String kompNum, kompText;
			kompNum = txtKomplainNum.getText().toString();
			kompText = txtKomplainText.getText().toString();

			final String smsMessage = String.format("K.%s.%s", kompNum,
					kompText);

			ArmHelpers.sendSMS(ComplainActivity.this, "+6287792021743",
					smsMessage);

		}

	}

}
