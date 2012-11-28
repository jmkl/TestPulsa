package arm.testpulsa.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import arm.testpulsa.R;

public class MenuListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MenuListAdapter(Context context, String[] values) {
		super(context, R.layout.list_view, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_view, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);

		// Change icon based on name
		String s = values[position];

		System.out.println(s);

		if (s.equals("Isi Pulsa")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Cek Saldo")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Komplain")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Cek Deposit")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Deposit")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Cek Harga")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Cek Transaksi Hari Ini")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Ganiti PIN")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Paralel No HP")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Cek Deposit Downline")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Mendaftarkan Agen")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Hapus Agen")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else if (s.equals("Menambah Deposit Downline")) {
			imageView.setImageResource(R.drawable.logoarm);
		} else {
			imageView.setImageResource(R.drawable.logoarm);
		}

		return rowView;
	}

}
