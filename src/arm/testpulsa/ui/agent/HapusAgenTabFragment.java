package arm.testpulsa.ui.agent;

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

public class HapusAgenTabFragment extends SherlockFragment implements
		TextWatcher {
	private EditText txtHapusAgenNum, txtHapusAgenPin;
	private Button btnHapusAgen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_hapus_agen, container, false);
		// find view
		txtHapusAgenNum= (EditText) view.findViewById(R.id.text_hapusAgenNum);
		txtHapusAgenPin= (EditText) view.findViewById(R.id.text_hapusAgenPin);
		btnHapusAgen= (Button) view.findViewById(R.id.btn_hapusAgen);

		// set listener
		txtHapusAgenNum.addTextChangedListener(this);
		txtHapusAgenPin.addTextChangedListener(this);
		btnHapusAgen.setEnabled(true);
		btnHapusAgen.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnHapusAgen.setEnabled(true);
			txtHapusAgenNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnHapusAgen.setEnabled(false);
			txtHapusAgenNum.setTextColor(Color.RED);
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
			String agenNum, agenPin;
			agenNum = txtHapusAgenNum.getText().toString();
			agenPin = txtHapusAgenPin.getText().toString();

			final String smsMessage = String.format("HAPUS.%s.%s", agenNum, agenPin);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
