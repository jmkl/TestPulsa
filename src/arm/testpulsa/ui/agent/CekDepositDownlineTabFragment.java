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

public class CekDepositDownlineTabFragment extends SherlockFragment implements TextWatcher {
	private EditText txtDownDepositNum, txtDownDepositPin;
	private Button btnDownDeposit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_cek_deposit_downline, container, false);
		// find view
		txtDownDepositNum= (EditText) view.findViewById(R.id.text_downDepositNum);
		txtDownDepositPin = (EditText) view.findViewById(R.id.text_downDepositPin);
		btnDownDeposit = (Button) view.findViewById(R.id.btn_sendDownDeposit);

		// set listener
		txtDownDepositNum.addTextChangedListener(this);
		txtDownDepositPin.addTextChangedListener(this);
		btnDownDeposit.setEnabled(true);
		btnDownDeposit.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnDownDeposit.setEnabled(true);
			txtDownDepositNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnDownDeposit.setEnabled(false);
			txtDownDepositNum.setTextColor(Color.RED);
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
			String downNum, downPin;
			downNum = txtDownDepositNum.getText().toString();
			downPin = txtDownDepositPin.getText().toString();

			final String smsMessage = String.format("CEKDEP.%s.%s", downNum, downPin);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
