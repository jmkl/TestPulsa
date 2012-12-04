package arm.testpulsa.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import arm.testpulsa.R;
import arm.testpulsa.ui.BaseSherlockFragment;
import arm.testpulsa.ui.KirimButtonListener;

public class ComplainTabFragment extends BaseSherlockFragment {

	private EditText txtKomplainNum, txtKomplainText;
	private Button btnKomplain;
	private String kompNum, kompText;

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

		kompNum = txtKomplainNum.getText().toString();
		kompText = txtKomplainText.getText().toString();

		final String smsMessage = String.format("K.%s.%s", kompNum, kompText);

		btnKomplain.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				mConfig.sendToPhoneNumber));
		return view;
	}
}