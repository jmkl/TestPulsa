package arm.testpulsa.ui.agent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import arm.testpulsa.R;
import arm.testpulsa.ui.BaseSherlockFragment;
import arm.testpulsa.ui.KirimButtonListener;

public class HapusAgenTabFragment extends BaseSherlockFragment {
	private EditText txtHapusAgenNum, txtHapusAgenPin;
	private Button btnHapusAgen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_hapus_agen, container,
				false);
		// find view
		txtHapusAgenNum = (EditText) view.findViewById(R.id.text_hapusAgenNum);
		txtHapusAgenPin = (EditText) view.findViewById(R.id.text_hapusAgenPin);
		btnHapusAgen = (Button) view.findViewById(R.id.btn_hapusAgen);

		final String smsMessage = String.format("PAR.%s.%s", txtHapusAgenNum
				.getText().toString(), txtHapusAgenPin.getText().toString());

		// Set Listener
		btnHapusAgen.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;

	}
}
