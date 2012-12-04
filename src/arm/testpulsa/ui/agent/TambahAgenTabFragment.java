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

public class TambahAgenTabFragment extends BaseSherlockFragment {
	private EditText txtTmbhAgenNum, txtTmbhAgenName, txtTmbhAgenMark,
			txtTmbhAgenSaldo, txtTmbhAgenKec, txtTmbhAgenPin;
	private Button btnTmbhAgen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_agent_tambah_agen, container,
				false);
		// find view
		txtTmbhAgenNum = (EditText) view.findViewById(R.id.text_tmbhAgenNum);
		txtTmbhAgenName = (EditText) view.findViewById(R.id.text_tmbhAgenName);
		txtTmbhAgenMark = (EditText) view.findViewById(R.id.text_tmbhAgenMark);
		txtTmbhAgenSaldo = (EditText) view
				.findViewById(R.id.text_tmbhAgenSaldo);
		txtTmbhAgenKec = (EditText) view.findViewById(R.id.text_tmbhAgenKec);
		txtTmbhAgenPin = (EditText) view.findViewById(R.id.text_tmbhAgenPin);
		btnTmbhAgen = (Button) view.findViewById(R.id.btn_tmbhAgen);

		final String smsMessage = String.format("REG.%s.%s.%s.%s.%s.%s",
				txtTmbhAgenNum.getText().toString(), txtTmbhAgenName.getText()
						.toString(), txtTmbhAgenMark.getText().toString(),
				txtTmbhAgenSaldo.getText().toString(), txtTmbhAgenKec.getText()
						.toString(), txtTmbhAgenPin.getText().toString());

		// Set Listener
		btnTmbhAgen.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;

	}
}
