package arm.testpulsa.model;

import android.util.Log;

public class CheckProvider {
	private String TAG = getClass().getSimpleName();
	private String[] TSEL = { "0811", "0812", "0813", "0852", "0853", "0821",
			"0822", "0823" };
	private String[] XL = { "0817", "0818", "0819", "0859", "0870", "0877",
			"0878", "088" };
	private String[] INDOSAT = { "0814", "0815", "0855", "0856", "0857", "0858" };
	private String[] AXIS = { "0831", "0833", "0838" };
	private String[] THREE = { "089" };
	private int PROV_TSEL = 0, PROV_XL = 1, PROV_ISAT = 2, PROV_AXIS = 3,
			PROV_THREE = 4;
	// default value kok ndak sobok nan nyo nio .. integer 0 tu samo jo null???
	private int PROVIDER = 666;
	private String num_toCompare;

	public int ayoCheckProvider(String number) {
		num_toCompare = number.substring(0, 2);
		// FIXME pisahan lu nan 3 digit.. kareh bana waawkwawa tabiaso hardcode
		if (num_toCompare.equals("088") || num_toCompare.equals("089")) {
			num_toCompare = number.substring(0, 3);
		} else {
			num_toCompare = number.substring(0, 4);
		}
		// FIXME ko lai ndak baa mode iko ko??
		boolean cekTSEL = KomperString(num_toCompare, TSEL);
		boolean cekXL = KomperString(num_toCompare, XL);
		boolean cekISAT = KomperString(num_toCompare, INDOSAT);
		boolean cekAXIS = KomperString(num_toCompare, AXIS);
		boolean cekTRI = KomperString(num_toCompare, THREE);
		if (cekTSEL) {
			setProv(PROV_TSEL);
		}
		if (cekXL) {
			setProv(PROV_XL);
		}
		if (cekISAT) {
			setProv(PROV_ISAT);
		}
		if (cekAXIS) {
			setProv(PROV_AXIS);
		}
		if (cekTRI) {
			setProv(PROV_THREE);
		}

		return PROVIDER;

	}

	void setProv(int myProvider) {
		PROVIDER = myProvider;
		Log.d(TAG, Integer.toString(PROVIDER));
	}

	public boolean KomperString(String num_Input, String[] num_Array) {
		for (String numarray : num_Array) {
			if (num_Input.equals(numarray)) {
				return true;
			}
		}
		return false;
	}

}
