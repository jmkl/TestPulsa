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

public class TambahDepositDownlineTabFragment extends SherlockFragment
		implements TextWatcher {
	private EditText txtTmbhDpstNum, txtTmbhDpstJum, txtTmbhDpstPin;
	private Button btnTmbhDpstDwn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.tab_agent_tambah_deposit_downline, container, false);
		// find view
		txtTmbhDpstNum = (EditText) view.findViewById(R.id.text_tmbhDpstNum);
		txtTmbhDpstJum = (EditText) view.findViewById(R.id.text_tmbhDpstJum);
		txtTmbhDpstPin = (EditText) view.findViewById(R.id.text_tmbhDpstPin);
		btnTmbhDpstDwn = (Button) view.findViewById(R.id.btn_tmbhDpstDwn);

		// set listener
		txtTmbhDpstNum.addTextChangedListener(this);
		txtTmbhDpstJum.addTextChangedListener(this);
		txtTmbhDpstPin.addTextChangedListener(this);
		btnTmbhDpstDwn.setEnabled(true);
		btnTmbhDpstDwn.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnTmbhDpstDwn.setEnabled(true);
			txtTmbhDpstNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnTmbhDpstDwn.setEnabled(false);
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

	private class ChangeButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String tmbhNum, tmbhJum, tmbhPin;
			tmbhNum = txtTmbhDpstNum.getText().toString();
			tmbhJum = txtTmbhDpstJum.getText().toString();
			tmbhPin = txtTmbhDpstPin.getText().toString();

			final String smsMessage = String.format("ADD.%s.%s.%s", tmbhNum, tmbhJum, tmbhPin);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
