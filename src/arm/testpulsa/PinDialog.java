package arm.testpulsa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class PinDialog extends AlertDialog implements
		DialogInterface.OnClickListener {

	private MainActivity mainActivity;
	private EditText textPin;

	public PinDialog(MainActivity mainActivity) {
		super(mainActivity);
		this.mainActivity = mainActivity; // 'this' as left hand qualifier
		setTitle(R.string.title_userPin);

		LayoutInflater inflater = (LayoutInflater) mainActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View group = inflater.inflate(R.layout.pin_dialog, null, false);
		setView(group);
		
		setButton(BUTTON_POSITIVE, mainActivity.getString(android.R.string.ok), this);
		setButton(BUTTON_NEGATIVE, mainActivity.getString(android.R.string.cancel), this);
		
		textPin = (EditText) group.findViewById(R.id.text_pinDialog);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case BUTTON_POSITIVE:
			// FIXME must not save pin as human readable text
			String userPin = textPin.getText().toString();
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);
			Editor editor = sharedPreferences.edit();
			
			editor.putString("pin", userPin);
			editor.commit();
			Intent restartIntent = new Intent(mainActivity, MainActivity.class);
			restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mainActivity.startActivity(restartIntent);
			mainActivity.finish();
			break;
		case BUTTON_NEGATIVE:
			mainActivity.finish();
			break;
			
		}
	}
}
