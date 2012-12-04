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

public class ChangePinTabFragment extends BaseSherlockFragment {
	private EditText txtPinOld, txtPinNew;
	private Button btnChangePin;
	private String pinOld, pinNew;

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
		
		pinOld = txtPinOld.getText().toString();
		pinNew = txtPinNew.getText().toString();

		final String smsMessage = String.format("S.%s.%s", pinOld, pinNew);
		
		btnChangePin.setOnClickListener(new KirimButtonListener(getSherlockActivity(), smsMessage, "5556"));
		return view;
	}
}
