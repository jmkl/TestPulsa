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

public class CariKodeKecamatan extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	
	EditText txtCariKodeKec, txtCariKodePin;
	Button btnCariKodeKec;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hapus_agen);

		// find view
		txtCariKodeKec = (EditText) findViewById(R.id.text_cariKodeKec);
		txtCariKodePin = (EditText) findViewById(R.id.text_cariKodePin);
		btnCariKodeKec = (Button) findViewById(R.id.btn_cariKodeKec);

		// set listener
		txtCariKodeKec.addTextChangedListener(this);
		txtCariKodePin.addTextChangedListener(this);
		btnCariKodeKec.setEnabled(true);
		btnCariKodeKec.setOnClickListener(new HapusButtonOnClick());

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
			btnCariKodeKec.setEnabled(true);
			txtCariKodePin.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCariKodeKec.setEnabled(false);
			txtCariKodePin.setTextColor(Color.RED);
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
			String cariKode, cariPin;
			cariKode = txtCariKodeKec.getText().toString();
			cariPin = txtCariKodePin.getText().toString();

			final String smsMessage = String.format("CARI.%s.%s", cariKode, cariPin);

			ArmHelpers.sendSMS(CariKodeKecamatan.this, "5556", smsMessage);
		}
	}
}
