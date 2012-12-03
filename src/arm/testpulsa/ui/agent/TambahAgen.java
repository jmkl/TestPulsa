package arm.testpulsa.ui.agent;

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

public class TambahAgen extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();

	EditText txtAgenNum, txtAgenName, txtAgenMark, txtAgenSaldo, txtAgenKec,
			txtAgenPin;
	Button btnTambahAgen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paralel_hp);

		// find view
		txtAgenNum = (EditText) findViewById(R.id.text_tmbhAgenNum);
		txtAgenName = (EditText) findViewById(R.id.text_tmbhAgenName);
		txtAgenMark = (EditText) findViewById(R.id.text_tmbhAgenMark);
		txtAgenSaldo = (EditText) findViewById(R.id.text_tmbhAgenSaldo);
		txtAgenKec = (EditText) findViewById(R.id.text_tmbhAgenKec);
		txtAgenPin = (EditText) findViewById(R.id.text_tmbhAgenPin);
		btnTambahAgen = (Button) findViewById(R.id.btn_tmbhAgen);

		// set listener
		txtAgenNum.addTextChangedListener(this);
		txtAgenName.addTextChangedListener(this);
		txtAgenMark.addTextChangedListener(this);
		txtAgenSaldo.addTextChangedListener(this);
		txtAgenKec.addTextChangedListener(this);
		txtAgenPin.addTextChangedListener(this);
		btnTambahAgen.setEnabled(true);
		btnTambahAgen.setOnClickListener(new ParalelButtonOnClick());

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
			btnTambahAgen.setEnabled(true);
			txtAgenNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnTambahAgen.setEnabled(false);
			txtAgenNum.setTextColor(Color.RED);
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

	private class ParalelButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String agenNum, agenName, agenMark, agenSaldo, agenKec, agenPin;
			agenNum = txtAgenNum.getText().toString();
			agenName = txtAgenName.getText().toString();
			agenMark = txtAgenMark.getText().toString();
			agenSaldo = txtAgenSaldo.getText().toString();
			agenKec = txtAgenKec.getText().toString();
			agenPin = txtAgenPin.getText().toString();

			final String smsMessage = String.format("REG.%s.%s.%s.%s.%s.%s",
					agenNum, agenName, agenMark, agenSaldo, agenKec, agenPin);

			ArmHelpers.sendSMS(TambahAgen.this, "5556", smsMessage);
		}
	}
}
