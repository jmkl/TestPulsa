package arm.testpulsa.ui;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import arm.testpulsa.utils.ArmHelpers;

public class KirimButtonListener implements OnClickListener {
	private String message, phoneNumber;
	private Context context;

	public KirimButtonListener(Context context, String message, String phoneNumber) {
		this.context = context;
		this.message = message;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public void onClick(View v) {
		ArmHelpers.sendSMS(context,
				phoneNumber, message);
	}
}
