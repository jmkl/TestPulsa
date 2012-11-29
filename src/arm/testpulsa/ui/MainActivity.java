package arm.testpulsa.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import arm.testpulsa.R;
import arm.testpulsa.model.CheckProvider;
import arm.testpulsa.model.NominalValue;
import arm.testpulsa.model.OperatorOption;
import arm.testpulsa.receiver.DeliveryReceiver;
import arm.testpulsa.receiver.SentReceiver;
import arm.testpulsa.ui.dialogs.ConfirmationDialog;
import arm.testpulsa.utils.ArmHelpers;
import arm.testpulsa.utils.ArmPulsaAddressMalformedException;

public class MainActivity extends Activity implements TextWatcher {
	private static final String TAG = MainActivity.class.getSimpleName();
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();

	EditText txtPhone, txtNominal, txtUserPin;
	Spinner spnOperator, spnNominal;
	Button btnSendForm;

	private static String userPin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		userPin = prefs.getString("pin", "");

		// Find view
		txtPhone = (EditText) findViewById(R.id.textPhoneNumber);
		spnOperator = (Spinner) findViewById(R.id.spinnerOperator);
		spnNominal = (Spinner) findViewById(R.id.spinnerNominal);
		btnSendForm = (Button) findViewById(R.id.btn_sendForm);

		// set listener
		txtPhone.addTextChangedListener(this);
		btnSendForm.setEnabled(false);
		btnSendForm.setOnClickListener(new SendButtonOnClick());
		setAdapter();
		Log.i(TAG, "onCreate");
	}

	@Override
	protected void onPause() {
		try {
			unregisterReceiver(sentReceiver);
			unregisterReceiver(deliveryReceiver);
		} catch (RuntimeException e) {
			Log.e(TAG, "Receiver not Registered");
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(sentReceiver, new IntentFilter(SMS_SENT));
		registerReceiver(sentReceiver, new IntentFilter(SMS_DELIVERED));
		Log.d(TAG, "onResume");
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnSendForm.setEnabled(true);
			txtPhone.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnSendForm.setEnabled(false);
			txtPhone.setTextColor(Color.RED);
		}
		int spnOpNewIndex = ArmHelpers.phoneNumberMatcher(s);
		spnOperator.setSelection(spnOpNewIndex);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// Nothing to do here!
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// Noting to do here!
	}
	
	private void setAdapter() {
		// spinner nominal adapter
		ArrayAdapter<OperatorOption> spnOperatorOptionAdapter = new ArrayAdapter<OperatorOption>(
				this, android.R.layout.simple_spinner_item,
				new OperatorOption[] { new OperatorOption(1, "Telkomsel", "S"),
						new OperatorOption(2, "Telkomsel Transfer", "P"),
						new OperatorOption(3, "Flexi", "F"),
						new OperatorOption(4, "Flexi Transfer", "FT"),
						new OperatorOption(5, "XL", "X"),
						new OperatorOption(6, "XL Transfer", "XT"),
						new OperatorOption(7, "Indosat", "I"),
						new OperatorOption(8, "Indosat Transfer", "IT"),
						new OperatorOption(9, "Indosat SMS", "IS"),
						new OperatorOption(10, "Indosat Internet", "IG"),
						new OperatorOption(11, "3", "T"),
						new OperatorOption(12, "Axis", "AX"),
						new OperatorOption(13, "Axis Transfer", "AXT"),
						new OperatorOption(14, "Esia", "E"),
						new OperatorOption(15, "Esia Transfer", "ET"),
						new OperatorOption(16, "Smartfren", "V"),
						new OperatorOption(17, "Ceria", "C") });
		spnOperatorOptionAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnOperator.setAdapter(spnOperatorOptionAdapter);

		// spinner nominal adapter
		ArrayAdapter<NominalValue> spnNominalAdapter = new ArrayAdapter<NominalValue>(
				this, android.R.layout.simple_spinner_item, new NominalValue[] {
						new NominalValue(1, "Rp. 5.000", 5),
						new NominalValue(2, "Rp. 10.000", 10),
						new NominalValue(3, "Rp. 15.000", 15),
						new NominalValue(4, "Rp. 20.000", 20),
						new NominalValue(5, "Rp. 25.000", 25),
						new NominalValue(6, "Rp. 50.000", 50),
						new NominalValue(7, "Rp. 100.000", 100) });
		spnNominalAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnNominal.setAdapter(spnNominalAdapter);
	}

	private class SendButtonOnClick implements OnClickListener {

		public void onClick(View v) {
			String telpNumber;
			NominalValue nv = (NominalValue) spnNominal.getSelectedItem();
			telpNumber = txtPhone.getText().toString();
			/* userPin = txtUserPin.getText().toString(); not used */
			OperatorOption op = (OperatorOption) spnOperator.getSelectedItem();

			// FIXME TES CHECK OP
			CheckProvider cek_provider = new CheckProvider();
			int PROVIDER = cek_provider.ayoCheckProvider(telpNumber);
			String mPROV;

			if (PROVIDER == 0)
				mPROV = "TELKOMSEL";
			else if (PROVIDER == 1)
				mPROV = "XL";
			else if (PROVIDER == 2)
				mPROV = "INDOSAT";
			else if (PROVIDER == 3)
				mPROV = "AXIS";
			else if (PROVIDER == 4)
				mPROV = "THREE";
			else
				mPROV = "au ah gelap";
			Toast.makeText(MainActivity.this, mPROV, Toast.LENGTH_SHORT).show();
			// Send SMS
			final String smsMessage = String.format("%s%s.%s.%s", op.kode,
					nv.value, telpNumber, userPin);
			new ConfirmationDialog(MainActivity.this, telpNumber,
					String.valueOf(op.name), String.valueOf(nv.name),
					new ConfirmDialogListener() {

						@Override
						public void onConfirmed() {
							// Toast.makeText(MainActivity.this,serverPhone +
							// smsMessage, 5).show();
							ArmHelpers.sendSMS(getApplicationContext(),
									"5556", smsMessage);

							// reset view to default value
							txtPhone.setText("");

							spnOperator.setSelection(0);
							spnNominal.setSelection(0);

							// request focus to txtPhone
							txtPhone.requestFocus();
						}

						@Override
						public void onCancel() {
							// nothing to do here
						}
					}).show();

			Log.d(TAG, "onSendButton Clicked, Send SMS will be:\n" + smsMessage);
		}
	}

	/**
	 * Callback interface for Confirmation Dialog
	 * 
	 * @author adrianbabame copas from Facebook SDK
	 * @author damnedivan copas from adrianbabame
	 */
	public static interface ConfirmDialogListener {
		/**
		 * Called when ok pressed
		 * 
		 * Executed by thread that executed dialog
		 */
		public void onConfirmed();

		/**
		 * Called when a dialog is canceled by the user.
		 * 
		 * Executed by the thread that initiated the dialog.
		 * 
		 */
		public void onCancel();
	}
}