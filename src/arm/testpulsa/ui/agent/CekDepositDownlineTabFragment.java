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

public class CekDepositDownlineTabFragment extends BaseSherlockFragment {
	private EditText txtDownDepositNum, txtDownDepositPin;
	private Button btnDownDeposit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_cek_deposit_downline,
				container, false);

		// read config
		setConfig();
		
		// find view
		txtDownDepositNum = (EditText) view
				.findViewById(R.id.text_downDepositNum);
		txtDownDepositPin = (EditText) view
				.findViewById(R.id.text_downDepositPin);
		btnDownDeposit = (Button) view.findViewById(R.id.btn_sendDownDeposit);

		final String smsMessage = String.format("CEKDEP.%s.%s",
				txtDownDepositNum.getText().toString(), txtDownDepositPin
						.getText().toString());

		// Set Listener
		btnDownDeposit.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;

	}
}
