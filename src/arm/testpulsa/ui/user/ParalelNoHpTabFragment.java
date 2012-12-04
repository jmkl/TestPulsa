package arm.testpulsa.ui.user;

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

public class ParalelNoHpTabFragment extends SherlockFragment implements
		TextWatcher {
	private EditText txtParalelNumber, txtParalelPin;
	private Button btnSendParalel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_paralel_hp, container, false);
		
		// find view
		txtParalelNumber= (EditText) view.findViewById(R.id.text_parNumber);
		txtParalelPin= (EditText) view.findViewById(R.id.text_parPin);
		btnSendParalel= (Button) view.findViewById(R.id.btn_sendPar);

		// set listener
		txtParalelNumber.addTextChangedListener(this);
		txtParalelPin.addTextChangedListener(this);
		btnSendParalel.setEnabled(true);
		btnSendParalel.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnSendParalel.setEnabled(true);
			txtParalelNumber.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnSendParalel.setEnabled(false);
			txtParalelNumber.setTextColor(Color.RED);
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
			String parNum, parPin;
			parNum = txtParalelNumber.getText().toString();
			parPin = txtParalelPin.getText().toString();

			final String smsMessage = String.format("PAR.%s.%s", parNum, parPin);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
