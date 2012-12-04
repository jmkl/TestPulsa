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

public class TambahDepositDownlineTabFragment extends BaseSherlockFragment {
	private EditText txtTmbhDpstNum, txtTmbhDpstJum, txtTmbhDpstPin;
	private Button btnTmbhDpstDwn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.tab_agent_tambah_deposit_downline, container, false);

		// read config
		setConfig();

		// find view
		txtTmbhDpstNum = (EditText) view.findViewById(R.id.text_tmbhDpstNum);
		txtTmbhDpstJum = (EditText) view.findViewById(R.id.text_tmbhDpstJum);
		txtTmbhDpstPin = (EditText) view.findViewById(R.id.text_tmbhDpstPin);
		btnTmbhDpstDwn = (Button) view.findViewById(R.id.btn_tmbhDpstDwn);

		final String smsMessage = String.format("ADD.%s.%s.%s", txtTmbhDpstNum
				.getText().toString(), txtTmbhDpstJum.getText().toString(),
				txtTmbhDpstPin.getText().toString());

		// Set Listener
		btnTmbhDpstDwn.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;

	}
}
