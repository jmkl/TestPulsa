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
	private EditText txtParalelNumber, txtParalelPin;
	private Button btnSendParalel;
	private String parNum, parPin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_paralel_hp, container, false);

		// find view
		txtParalelNumber = (EditText) view.findViewById(R.id.text_parNumber);
		txtParalelPin = (EditText) view.findViewById(R.id.text_parPin);
		btnSendParalel = (Button) view.findViewById(R.id.btn_sendPar);

		// set listener
		txtParalelNumber.addTextChangedListener(this);
		txtParalelPin.addTextChangedListener(this);
		btnSendParalel.setEnabled(true);

		final String smsMessage = String.format("PAR.%s.%s", parNum, parPin);
		btnSendParalel.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				mConfig.sendToPhoneNumber));
		return view;
	}
}
