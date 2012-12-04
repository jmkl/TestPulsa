package arm.testpulsa.ui;

import android.os.Bundle;
import android.util.Log;
import arm.testpulsa.R;
import arm.testpulsa.ui.user.CekHargaTabFragment;
import arm.testpulsa.ui.user.CekSaldoTabFragment;
import arm.testpulsa.ui.user.ChangePinTabFragment;
import arm.testpulsa.ui.user.ComplainTabFragment;

public class UserActivity extends BaseSherlockFragmentActivity {
	private static final String TAG = UserActivity.class.getSimpleName();

	private ChangePinTabFragment mChangePinTabFragment = new ChangePinTabFragment();
	private CekSaldoTabFragment mCekSaldoTabFragment = new CekSaldoTabFragment();
	private CekHargaTabFragment mCekHargaTabFragment = new CekHargaTabFragment();
	private ComplainTabFragment mComplainTabFragment = new ComplainTabFragment();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Account Control Panel");
		
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_cekSaldo))
				.setTabListener(new TabListener(mPager, 0)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_cekHarga))
				.setTabListener(new TabListener(mPager, 1)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_changePin))
				.setTabListener(new TabListener(mPager, 2)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_complain))
				.setTabListener(new TabListener(mPager, 3)));

		mFragments.add(mCekSaldoTabFragment);
		mFragments.add(mCekHargaTabFragment);
		mFragments.add(mChangePinTabFragment);
		mFragments.add(mComplainTabFragment);

		setAdapter();
		Log.i(TAG, "onCreated");
	}
}
