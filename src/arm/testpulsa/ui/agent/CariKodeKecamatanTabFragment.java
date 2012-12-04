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

public class CariKodeKecamatanTabFragment extends SherlockFragment implements TextWatcher {
	private EditText txtCariKodeKec, txtCariKodePin;
	private Button btnCariKodeKec;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_cari_kode_kecamatan, container, false);
		// find view
		txtCariKodeKec= (EditText) view.findViewById(R.id.text_cariKodeKec);
		txtCariKodePin = (EditText) view.findViewById(R.id.text_cariKodePin);
		btnCariKodeKec = (Button) view.findViewById(R.id.btn_cariKodeKec);

		// set listener
		txtCariKodeKec.addTextChangedListener(this);
		txtCariKodePin.addTextChangedListener(this);
		btnCariKodeKec.setEnabled(true);
		btnCariKodeKec.setOnClickListener(new ChangeButtonOnClick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPinNumber(s);
			btnCariKodeKec.setEnabled(true);
			txtCariKodePin.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCariKodeKec.setEnabled(false);
			txtCariKodePin.setTextColor(Color.RED);
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
			String cariKode, cariPin;
			cariKode = txtCariKodeKec.getText().toString();
			cariPin = txtCariKodePin.getText().toString();

			final String smsMessage = String.format("CARI.%s.%s", cariKode, cariPin);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(),
					"5556", smsMessage);
		}
	}
}
