package arm.testpulsa.ui.user;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import arm.testpulsa.R;
import arm.testpulsa.ui.BaseSherlockFragment;
import arm.testpulsa.ui.KirimButtonListener;
import arm.testpulsa.utils.ArmHelpers;
import arm.testpulsa.utils.ArmPulsaAddressMalformedException;

public class ChangePinTabFragment extends BaseSherlockFragment implements
		TextWatcher {
	private EditText txtPinOld, txtPinNew;
	private Button btnChangePin;
	private String pinOld, pinNew;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_change_pin, container, false);

		// read config
		setConfig();

		// find view
		txtPinOld = (EditText) view.findViewById(R.id.textPinOld);
		txtPinNew = (EditText) view.findViewById(R.id.textPinNew);
		btnChangePin = (Button) view.findViewById(R.id.btn_changePin);

		// set listener
		txtPinOld.addTextChangedListener(this);
		txtPinNew.addTextChangedListener(this);
		btnChangePin.setEnabled(false);

		pinOld = txtPinOld.getText().toString();
		pinNew = txtPinNew.getText().toString();

		final String smsMessage = String.format("S.%s.%s", pinOld, pinNew);

		btnChangePin.setOnClickListener(new KirimButtonListener(
				getSherlockActivity(), smsMessage, sendToPhoneNumber));
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnChangePin.setEnabled(true);
			txtPinOld.setTextColor(Color.BLACK);
			txtPinNew.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnChangePin.setEnabled(false);
			txtPinOld.setTextColor(Color.RED);
			txtPinNew.setTextColor(Color.RED);
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
}
