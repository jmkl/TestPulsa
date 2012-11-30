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

public class ComplainTabFragment extends SherlockFragment implements TextWatcher {
	
	EditText txtKomplainNum, txtKomplainText;
	Button btnKomplain;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_complain, container, false);

		// find view
		txtKomplainNum = (EditText) view.findViewById(R.id.text_KomplainNum);
		txtKomplainText = (EditText) view.findViewById(R.id.text_KomplainText);
		btnKomplain = (Button) view.findViewById(R.id.btn_komplain);

		// set listener
		txtKomplainNum.addTextChangedListener(this);
		txtKomplainText.addTextChangedListener(this);
		btnKomplain.setEnabled(true);
		btnKomplain.setOnClickListener(new KomplainButtonOnClick());
		return view;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPhoneNumber(s);
			btnKomplain.setEnabled(true);
			txtKomplainNum.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnKomplain.setEnabled(false);
			txtKomplainNum.setTextColor(Color.RED);
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

	public class KomplainButtonOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String kompNum, kompText;
			kompNum = txtKomplainNum.getText().toString();
			kompText = txtKomplainText.getText().toString();

			final String smsMessage = String.format("K.%s.%s", kompNum,
					kompText);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(), "+6287792021743",
					smsMessage);
		}
	}
}