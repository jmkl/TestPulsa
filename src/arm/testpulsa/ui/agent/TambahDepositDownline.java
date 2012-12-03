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

public class TambahDepositDownline extends Activity implements TextWatcher {
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();
	
	EditText txtTmbhDpstNum, txtTmbhDpstNom, txtTmbhDpstPin;
	Button btnTmbhDpst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hapus_agen);

		// find view
		txtTmbhDpstNum = (EditText) findViewById(R.id.text_tmbhDpstNum);
		txtTmbhDpstNom = (EditText) findViewById(R.id.text_tmbhDpstDwn);
		txtTmbhDpstPin = (EditText) findViewById(R.id.text_tmbhDpstPin);
		btnTmbhDpst = (Button) findViewById(R.id.btn_tmbhDpstDwn);

		// set listener
		txtTmbhDpstNum.addTextChangedListener(this);
		txtTmbhDpstNom.addTextChangedListener(this);
		txtTmbhDpstPin.addTextChangedListener(this);
		btnTmbhDpst.setEnabled(true);
		btnTmbhDpst.setOnClickListener(new HapusButtonOnClick());

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
			btnTmbhDpst.setEnabled(true);
			txtTmbhDpstNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnTmbhDpst.setEnabled(false);
			txtTmbhDpstNum.setTextColor(Color.RED);
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
			String dpstNum, dpstNom, dpstPin;
			dpstNum = txtTmbhDpstNum.getText().toString();
			dpstNom = txtTmbhDpstNom.getText().toString();
			dpstPin = txtTmbhDpstPin.getText().toString();

			final String smsMessage = String.format("ADDD.%s.%s.%s", dpstNum, dpstNom, dpstPin);

			ArmHelpers.sendSMS(TambahDepositDownline.this, "5556", smsMessage);
		}
	}
}
