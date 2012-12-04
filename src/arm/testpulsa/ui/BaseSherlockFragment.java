package arm.testpulsa.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import arm.testpulsa.TestPulsaConfiguration;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class BaseSherlockFragment extends SherlockFragment {
	protected TestPulsaConfiguration mConfig;
	protected String userPin, sendToPhoneNumber;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mConfig = new TestPulsaConfiguration(
				PreferenceManager
						.getDefaultSharedPreferences(getSherlockActivity().getApplicationContext()));
		userPin = mConfig.userPin;
		sendToPhoneNumber = mConfig.sendToPhoneNumber;
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
