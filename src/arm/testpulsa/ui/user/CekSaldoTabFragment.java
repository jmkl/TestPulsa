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

public class CekSaldoTabFragment extends SherlockFragment implements
		TextWatcher {
	EditText txtPinSaldo;
	Button btnCekSaldo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_cek_saldo, container, false);

		// find view
		txtPinSaldo = (EditText) view.findViewById(R.id.textPinSaldo);
		btnCekSaldo = (Button) view.findViewById(R.id.btn_cekSaldo);

		// setting listener
		txtPinSaldo.addTextChangedListener(this);
		btnCekSaldo.setEnabled(false);
		btnCekSaldo.setOnClickListener(new CekButtonOnclick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPinNumber(s);
			btnCekSaldo.setEnabled(true);
			txtPinSaldo.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCekSaldo.setEnabled(false);
			txtPinSaldo.setTextColor(Color.RED);
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

	public class CekButtonOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String pinCekSaldo;
			pinCekSaldo = txtPinSaldo.getText().toString();

			final String smsMessage = String.format("S.%s", pinCekSaldo);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"+6287792021743", smsMessage);
		}

	}
}
