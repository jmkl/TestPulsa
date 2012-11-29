package arm.testpulsa.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import arm.testpulsa.R;
import arm.testpulsa.adapter.MenuListAdapter;

public class MenuListView extends ListActivity {

	static final String[] MENU = new String[] { "Isi Pulsa", "Cek Saldo",
			"Komplain", "Cek Harga", "Cek Transaksi Hari Ini",
			"Ganti PIN", "Paralel No HP", "Cek Deposit Downline",
			"Mendaftarkan Agen", "Hapus Agen", "Menambah Deposit Downline" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setListAdapter(new ArrayAdapter<String>(this, R.layout.list_mobile,
		// R.id.label, MOBILE_OS));

		setListAdapter(new MenuListAdapter(this, MENU));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		{
			if (position == 0) {
				startActivity(new Intent(this, MainActivity.class));
			}
			if (position == 1) {
				startActivity(new Intent(this, CekSaldo.class));
			}
			if (position == 2) {
				startActivity(new Intent(this, ComplainActivity.class));
			}
			if (position == 3) {
				startActivity(new Intent(this, CekHarga.class));
			}
			if (position == 4) {
				startActivity(new Intent(this, CekTransaksi.class));
			}
			if (position == 5) {
				startActivity(new Intent(this, ChangePin.class));
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_about:
			startActivity(new Intent(this, AboutApp.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}