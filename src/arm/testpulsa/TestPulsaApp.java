package arm.testpulsa;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

public class TestPulsaApp extends Application {
	private static final String TAG = TestPulsaApp.class.getSimpleName();
	
	private TestPulsaConfiguration mConfig;
	
	public TestPulsaApp() {
		super();
	}
	
	@Override
	public void onCreate() {
		mConfig = new TestPulsaConfiguration(PreferenceManager
				.getDefaultSharedPreferences(this));
		Log.i(TAG, "onCreate");
	}

	public static TestPulsaApp getApp(Context ctx) {
		return (TestPulsaApp)ctx.getApplicationContext();
	}

	public static TestPulsaConfiguration getConfig(Context ctx) {
		return getApp(ctx).mConfig;
	}

}
