package arm.testpulsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// splash screen thread
public class SplashScreen extends Activity {

	private static final int SPLASH_DISPLAY_TIME = 500;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splashscreen);

	    new Handler().postDelayed(new Runnable() {

	        public void run() {

	            Intent mainIntent = new Intent(SplashScreen.this,
	                    MainActivity.class);
	            SplashScreen.this.startActivity(mainIntent);

	            SplashScreen.this.finish();
	            overridePendingTransition(R.anim.mainfadein,
	                    R.anim.splashfadeout);
	        }
	    }, SPLASH_DISPLAY_TIME);
	}

}