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

public class CekSaldoTabFragment extends BaseSherlockFragment {
	EditText txtPinSaldo;
	Button btnCekSaldo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_cek_saldo, container, false);

		// find view
		txtPinSaldo = (EditText) view.findViewById(R.id.textPinSaldo);
		btnCekSaldo = (Button) view.findViewById(R.id.btn_cekSaldo);

		// setting listener
		final String smsMessage = String.format("S.%s", userPin);
		btnCekSaldo.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;
	}
}
