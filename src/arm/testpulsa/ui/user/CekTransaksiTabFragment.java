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
		View view = inflater.inflate(R.layout.cek_transaksi, container, false);
		// find view
		txtCekTrans = (EditText) view.findViewById(R.id.textCekTrans);
		btnCekTrans = (Button) view.findViewById(R.id.btn_cekTrans);

		// setting listener
		txtCekTrans.addTextChangedListener(this);
		btnCekTrans.setEnabled(false);

		final String smsMessage = String.format("TRX.%s", userPin);

		btnCekTrans.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				mConfig.sendToPhoneNumber));
		return view;
	}
}
