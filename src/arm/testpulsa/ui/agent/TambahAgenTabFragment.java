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

public class TambahAgenTabFragment extends SherlockFragment implements
		TextWatcher {
	private EditText txtTmbhAgenNum, txtTmbhAgenName, txtTmbhAgenMark,
			txtTmbhAgenSaldo, txtTmbhAgenKec, txtTmbhAgenPin;
	private Button btnTmbhAgen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_tambah_agen, container,
				false);
		// find view
		txtTmbhAgenNum = (EditText) view.findViewById(R.id.text_tmbhAgenNum);
		txtTmbhAgenName = (EditText) view.findViewById(R.id.text_tmbhAgenName);
		txtTmbhAgenMark = (EditText) view.findViewById(R.id.text_tmbhAgenMark);
		txtTmbhAgenSaldo = (EditText) view.findViewById(R.id.text_tmbhAgenSaldo);
		txtTmbhAgenKec = (EditText) view.findViewById(R.id.text_tmbhAgenKec);
		txtTmbhAgenPin = (EditText) view.findViewById(R.id.text_tmbhAgenPin);
		btnTmbhAgen = (Button) view.findViewById(R.id.btn_tmbhAgen);

		// set listener
		txtTmbhAgenNum.addTextChangedListener(this);
		txtTmbhAgenName.addTextChangedListener(this);
		txtTmbhAgenMark.addTextChangedListener(this);
		txtTmbhAgenSaldo.addTextChangedListener(this);
		txtTmbhAgenKec.addTextChangedListener(this);
		txtTmbhAgenPin.addTextChangedListener(this);
		btnTmbhAgen.setEnabled(true);
		btnTmbhAgen.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnTmbhAgen.setEnabled(true);
			txtTmbhAgenNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnTmbhAgen.setEnabled(false);
			txtTmbhAgenNum.setTextColor(Color.RED);
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
			String tmbhNum, tmbhName, tmbhMark, tmbhSaldo, tmbhKec, tmbhPin;
			tmbhNum = txtTmbhAgenNum.getText().toString();
			tmbhName = txtTmbhAgenName.getText().toString();
			tmbhMark = txtTmbhAgenMark.getText().toString();
			tmbhSaldo = txtTmbhAgenSaldo.getText().toString();
			tmbhKec = txtTmbhAgenKec.getText().toString();
			tmbhPin = txtTmbhAgenPin.getText().toString();

			final String smsMessage = String
					.format("REG.%s.%s.%s.%s.%s.%s", tmbhNum, tmbhName, tmbhMark, tmbhSaldo, tmbhKec, tmbhPin);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
