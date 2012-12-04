package arm.testpulsa.ui;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import arm.testpulsa.R;
import arm.testpulsa.receiver.DeliveryReceiver;
import arm.testpulsa.receiver.SentReceiver;
import arm.testpulsa.ui.user.CekHargaTabFragment;
import arm.testpulsa.ui.user.CekSaldoTabFragment;
import arm.testpulsa.ui.user.ChangePinTabFragment;
import arm.testpulsa.ui.user.ComplainTabFragment;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class UserActivity extends SherlockFragmentActivity {
	private static final String TAG = UserActivity.class.getSimpleName();
	public static final String SMS_SENT = "SMS_SENT";
	public static final String SMS_DELIVERED = "SMS_DELIVERED";
	private final BroadcastReceiver sentReceiver = new SentReceiver();
	private final BroadcastReceiver deliveryReceiver = new DeliveryReceiver();

	public class TabListener implements ActionBar.TabListener {
		private ViewPager mPager;
		private int mIndex;

		public TabListener(ViewPager pager, int index) {
			mPager = pager;
			mIndex = index;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mPager.setCurrentItem(mIndex);
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}
	}

	public static class TabAdapter extends FragmentPagerAdapter {
		ActionBar mActionBar;
		ArrayList<SherlockFragment> mFragments;

		public TabAdapter(FragmentManager fm, ActionBar actionBar,
				ArrayList<SherlockFragment> fragments) {
			super(fm);
			mActionBar = actionBar;
			mFragments = fragments;
		}

		@Override
		public int getCount() {
			return mActionBar.getTabCount();
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			if (position >= mFragments.size()) {
				return mFragments.get(mFragments.size() - 1);
			}
			if (position < 0) {
				return mFragments.get(0);
			}

			return mFragments.get(position);
		}
	}

	private ActionBar mActionBar;
	private ViewPager mPager;
	private ChangePinTabFragment mChangePinTabFragment = new ChangePinTabFragment();
	private CekSaldoTabFragment mCekSaldoTabFragment = new CekSaldoTabFragment();
	private CekHargaTabFragment mCekHargaTabFragment = new CekHargaTabFragment();
	private ComplainTabFragment mComplainTabFragment = new ComplainTabFragment();
	private ArrayList<SherlockFragment> mFragments = new ArrayList<SherlockFragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setTheme(com.actionbarsherlock.R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setTitle("Account Control Panel");
		setContentView(R.layout.tab_container);

		mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mPager = (ViewPager) findViewById(R.id.fragment_container);

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

		mPager.setAdapter(new TabAdapter(getSupportFragmentManager(),
				mActionBar, mFragments));

		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageScrollStateChanged(int arg0) {
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageSelected(int index) {
				mActionBar.getTabAt(index).select();
			}
		});
		Log.i(TAG, "onCreated");
	}

	@Override
	protected void onPause() {
		unregisterReceiver(sentReceiver);
		unregisterReceiver(deliveryReceiver);
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		registerReceiver(sentReceiver, new IntentFilter(SMS_SENT));
		registerReceiver(deliveryReceiver, new IntentFilter(SMS_DELIVERED));
		super.onResume();
		Log.d(TAG, "onResume");
	}
}
