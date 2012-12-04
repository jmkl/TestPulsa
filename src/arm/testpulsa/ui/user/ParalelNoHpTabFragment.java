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

public class ParalelNoHpTabFragment extends BaseSherlockFragment {

	private Button btnSendParalel;
	private EditText txtParalelNumber, txtParalelPin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_paralel_hp, container, false);

		// read config
		setConfig();

		// find view
		txtParalelNumber = (EditText) view.findViewById(R.id.text_parNumber);
		txtParalelPin = (EditText) view.findViewById(R.id.text_parPin);
		btnSendParalel = (Button) view.findViewById(R.id.btn_sendPar);

		final String smsMessage = String.format("PAR.%s.%s", txtParalelNumber
				.getText().toString(), txtParalelPin.getText().toString());

		// Set Listener
		btnSendParalel.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;
	}
}
