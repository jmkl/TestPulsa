package arm.testpulsa.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import arm.testpulsa.TestPulsaApp;
import arm.testpulsa.TestPulsaConfiguration;

import com.actionbarsherlock.app.SherlockFragment;

public abstract class BaseSherlockFragment extends SherlockFragment {
	protected TestPulsaConfiguration mConfig;
	protected String userPin, sendToPhoneNumber;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected void setConfig() {
		mConfig = TestPulsaApp.getConfig(getSherlockActivity().getApplicationContext());
		userPin = mConfig.userPin;
		sendToPhoneNumber = mConfig.sendToPhoneNumber;
	}
}
