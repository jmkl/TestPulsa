package arm.testpulsa.ui.tab;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import arm.testpulsa.R;
import arm.testpulsa.utils.ArmHelpers;
import arm.testpulsa.utils.ArmPulsaAddressMalformedException;

import com.actionbarsherlock.app.SherlockFragment;

public class ChangePinTabFragment extends SherlockFragment implements
		TextWatcher {
	EditText txtPinOld, txtPinNew;
	Button btnChangePin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_change_pin, container, false);
		// find view
		txtPinOld = (EditText) view.findViewById(R.id.textPinOld);
		txtPinNew = (EditText) view.findViewById(R.id.textPinNew);
		btnChangePin = (Button) view.findViewById(R.id.btn_changePin);

		// set listener
		txtPinOld.addTextChangedListener(this);
		txtPinNew.addTextChangedListener(this);
		btnChangePin.setEnabled(true);
		btnChangePin.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPinNumber(s);
			btnChangePin.setEnabled(true);
			txtPinNew.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnChangePin.setEnabled(false);
			txtPinNew.setTextColor(Color.RED);
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
			String pinOld, pinNew;
			pinOld = txtPinOld.getText().toString();
			pinNew = txtPinNew.getText().toString();

			final String smsMessage = String.format("S.%s.%s", pinOld, pinNew);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
