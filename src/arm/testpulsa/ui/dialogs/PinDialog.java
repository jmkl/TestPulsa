package arm.testpulsa.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import arm.testpulsa.R;
import arm.testpulsa.ui.MainScreen;

public class PinDialog extends AlertDialog implements
		DialogInterface.OnClickListener {

	private MainScreen mainScreen;
	private EditText textPin;

	public PinDialog(MainScreen mainScreen) {
		super(mainScreen);
		this.mainScreen = mainScreen; // 'this' as left hand qualifier
		setTitle(R.string.title_userPin);

		LayoutInflater inflater = (LayoutInflater) mainScreen
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View group = inflater.inflate(R.layout.pin_dialog, null, false);
		setView(group);
		
		setButton(BUTTON_POSITIVE, mainScreen.getString(android.R.string.ok), this);
		setButton(BUTTON_NEGATIVE, mainScreen.getString(android.R.string.cancel), this);
		
		textPin = (EditText) group.findViewById(R.id.text_pinDialog);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case BUTTON_POSITIVE:
			// FIXME must not save pin as human readable text
			String userPin = textPin.getText().toString();
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainScreen);
			Editor editor = sharedPreferences.edit();
			
			editor.putString("pin", userPin);
			editor.commit();
			break;
		case BUTTON_NEGATIVE:
			mainScreen.finish();
			break;
			
		}
	}
}
