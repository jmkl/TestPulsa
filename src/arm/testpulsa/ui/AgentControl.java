package arm.testpulsa.ui;

import android.os.Bundle;
import android.util.Log;
import arm.testpulsa.R;
import arm.testpulsa.ui.agent.CariKodeKecamatanTabFragment;
import arm.testpulsa.ui.agent.CekDepositDownlineTabFragment;
import arm.testpulsa.ui.agent.HapusAgenTabFragment;
import arm.testpulsa.ui.agent.TambahAgenTabFragment;
import arm.testpulsa.ui.agent.TambahDepositDownlineTabFragment;

public class AgentControl extends BaseSherlockFragmentActivity {
	private static final String TAG = UserActivity.class.getSimpleName();
	
	private CariKodeKecamatanTabFragment mCariKodeKecamatanTabFragment = new CariKodeKecamatanTabFragment();
	private TambahAgenTabFragment mTambahAgenTabFragment= new TambahAgenTabFragment();
	private HapusAgenTabFragment mHapusAgenTabFragment= new HapusAgenTabFragment();
	private TambahDepositDownlineTabFragment mTambahDepositDownlineTabFragment = new TambahDepositDownlineTabFragment();
	private CekDepositDownlineTabFragment mCekDepositDownlineTabFragment = new CekDepositDownlineTabFragment();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setTheme(com.actionbarsherlock.R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setTitle("Account Control Panel");
		
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_cariKodeKec))
				.setTabListener(new TabListener(mPager, 0)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_tambahAgen))
				.setTabListener(new TabListener(mPager, 1)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_hapusAgen))
				.setTabListener(new TabListener(mPager, 2)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_tambahDepositDown))
				.setTabListener(new TabListener(mPager, 3)));
		mActionBar.addTab(mActionBar.newTab()
				.setText(getString(R.string.panel_cekDepositDown))
				.setTabListener(new TabListener(mPager, 0)));

		mFragments.add(mCariKodeKecamatanTabFragment);
		mFragments.add(mTambahAgenTabFragment);
		mFragments.add(mHapusAgenTabFragment);
		mFragments.add(mTambahDepositDownlineTabFragment);
		mFragments.add(mCekDepositDownlineTabFragment);
		
		setAdapter();
		
		Log.i(TAG, "onCreated");
	}
}
