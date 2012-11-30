package arm.testpulsa.ui.tab;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import arm.testpulsa.R;
import arm.testpulsa.model.HargaOperator;
import arm.testpulsa.utils.ArmHelpers;
import arm.testpulsa.utils.ArmPulsaAddressMalformedException;

import com.actionbarsherlock.app.SherlockFragment;

public class CekHargaTabFragment extends SherlockFragment implements TextWatcher {
	private Spinner spnHarga;
	private EditText txtPinHarga;
	private Button btnCekHarga;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_cek_harga, container, false);

		// find view by id
		spnHarga = (Spinner) view.findViewById(R.id.spinnerHarga);
		txtPinHarga = (EditText) view.findViewById(R.id.textPinHarga);
		btnCekHarga = (Button) view.findViewById(R.id.btn_cekHarga);

		// set listener
		txtPinHarga.addTextChangedListener(this);
		setAdapter();
		btnCekHarga.setEnabled(false);
		btnCekHarga.setOnClickListener(new HargaButtonOnclick());
		return view;
	}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			ArmHelpers.verifyPinNumber(s);
			btnCekHarga.setEnabled(true);
			txtPinHarga.setTextColor(Color.BLACK);
		} catch (ArmPulsaAddressMalformedException e) {
			btnCekHarga.setEnabled(false);
			txtPinHarga.setTextColor(Color.RED);
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	private void setAdapter() {
		// spinner operator adapter
		ArrayAdapter<HargaOperator> spnHargaOperatorAdapter = new ArrayAdapter<HargaOperator>(
				getSherlockActivity().getApplicationContext(), android.R.layout.simple_spinner_item,
				new HargaOperator[] {
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

	public class HargaButtonOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			String harga = null, pinCekHarga;
			pinCekHarga = txtPinHarga.getText().toString();
			HargaOperator ho = (HargaOperator) spnHarga.getSelectedItem();
			harga = String.valueOf(ho.kode);

			final String smsMessage = String.format("HRG.%s.%s", harga,
					pinCekHarga);

			ArmHelpers.sendSMS(getSherlockActivity().getApplicationContext(), "+6287792021743", smsMessage);
		}

	}
}