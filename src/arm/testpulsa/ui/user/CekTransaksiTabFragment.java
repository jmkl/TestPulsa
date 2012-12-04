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

public class CekTransaksiTabFragment extends BaseSherlockFragment {
	EditText txtCekTrans;
	Button btnCekTrans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_cek_transaksi, container, false);
		// find view
		txtCekTrans = (EditText) view.findViewById(R.id.textCekTrans);
		btnCekTrans = (Button) view.findViewById(R.id.btn_cekTrans);

		final String smsMessage = String.format("TRX.%s", userPin);

		// setting listener
		btnCekTrans.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;
	}
}
