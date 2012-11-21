package arm.testpulsa.enkrip;
/*
 * Maenkrip String Jadi Baso Planet
 * */
import android.util.Base64;

public class EnkripDekrip {
	
	public String enkode(String stringnankadienkode) {
		String stringterenkrip = Base64.encodeToString(
				stringnankadienkode.getBytes(), Base64.DEFAULT);
		
		return stringterenkrip;
	}

	public String dekode(String stringnankadidekode) {
		String stringterdekrip = new String(Base64.decode(stringnankadidekode,
				Base64.DEFAULT));
		
		return stringterdekrip;
	}

}
