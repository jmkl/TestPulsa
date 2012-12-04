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

public class CariKodeKecamatanTabFragment extends BaseSherlockFragment {
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
		
		final String smsMessage = String.format("CARI.%s.%s", txtCariKodeKec
				.getText().toString(), txtCariKodePin.getText().toString());

		// Set Listener
		btnCariKodeKec.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;

	}
}
