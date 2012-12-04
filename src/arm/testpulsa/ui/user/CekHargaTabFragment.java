package arm.testpulsa.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import arm.testpulsa.R;
import arm.testpulsa.model.HargaOperator;
import arm.testpulsa.ui.BaseSherlockFragment;
import arm.testpulsa.ui.KirimButtonListener;

public class CekHargaTabFragment extends BaseSherlockFragment {
	private Spinner spnHarga;
	private Button btnCekHarga;
	private String harga;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_cek_harga, container, false);

		// find view by id
		spnHarga = (Spinner) view.findViewById(R.id.spinnerHarga);
		btnCekHarga = (Button) view.findViewById(R.id.btn_cekHarga);
		setAdapter();
		btnCekHarga.setEnabled(false);

		HargaOperator ho = (HargaOperator) spnHarga.getSelectedItem();
		harga = String.valueOf(ho.kode);

		final String smsMessage = String.format("HRG.%s.%s", harga, userPin);
		btnCekHarga.setOnClickListener(new KirimButtonListener(
				getSherlockActivity().getApplicationContext(), smsMessage,
				sendToPhoneNumber));
		return view;
	}

	private void setAdapter() {
		// spinner operator adapter
		ArrayAdapter<HargaOperator> spnHargaOperatorAdapter = new ArrayAdapter<HargaOperator>(
				getSherlockActivity().getApplicationContext(),
				android.R.layout.simple_spinner_item, new HargaOperator[] {
						new HargaOperator(1, "Telkomsel", "Telkomsel"),
						new HargaOperator(2, "XL", "XL"),
						new HargaOperator(3, "Indosat", "Indosat"),
						new HargaOperator(4, "Three", "Three"),
						new HargaOperator(5, "Axis", "Axis"),
						new HargaOperator(6, "Flexi", "Flexi"),
						new HargaOperator(7, "Esia", "Esia"),
						new HargaOperator(8, "Smartfren", "Smartfren"),
						new HargaOperator(9, "Ceria", "Ceria"), });
		spnHargaOperatorAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnHarga.setAdapter(spnHargaOperatorAdapter);

	}
}