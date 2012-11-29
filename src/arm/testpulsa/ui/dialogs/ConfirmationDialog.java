package arm.testpulsa.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import arm.testpulsa.ui.MainActivity.ConfirmDialogListener;
import arm.testpulsa.R;

public class ConfirmationDialog extends Dialog implements OnClickListener {
	private static final String TAG = ConfirmationDialog.class.getSimpleName();
	
	private String phoneNumber, operator, nominal;
	private ConfirmDialogListener mListener;
	
	public ConfirmationDialog(Context context, String phoneNumber, String operator, String nominal, ConfirmDialogListener listener) {
		super(context);
		// this as left hand qualifier
		this.phoneNumber = phoneNumber;
		this.operator = operator;
		this.nominal = nominal;
		mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirmation_dialog);
		setTitle(R.string.confirmation_info);
		// find view
		TextView txtPhone = (TextView) findViewById(R.id.conf_textPhoneNumber);
		TextView txtOperator = (TextView) findViewById(R.id.conf_textOperator);
		TextView txtNominal = (TextView) findViewById(R.id.conf_textNominal);
		ImageButton btnConfirm = (ImageButton) findViewById(R.id.conf_buttonConfirm);
		ImageButton btnSend = (ImageButton) findViewById(R.id.conf_buttonEdit);
		
		// set text
		txtPhone.setText(": " + phoneNumber);
		txtOperator.setText(": " + operator);
		txtNominal.setText(": " + nominal);
		
		// set listener
		btnConfirm.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		Log.i(TAG, "onCreate");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.conf_buttonConfirm:
			mListener.onConfirmed();
			ConfirmationDialog.this.dismiss();
			break;
		case R.id.conf_buttonEdit:
			mListener.onCancel();
			ConfirmationDialog.this.dismiss();
			break;
		default:
			mListener.onCancel();
			ConfirmationDialog.this.dismiss();
		}
	}
}
