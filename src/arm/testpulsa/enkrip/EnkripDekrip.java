package arm.testpulsa.enkrip;
/*
 * Maenkrip String Jadi Baso Planet
 * Caro gunoannyo :
 * 
 * String mString = "mypassword";
 * 
 * EnkripDekrip mEnDek = new EnkripDekrip();
 * String EnkripPass = mEnDek.enkode(mString);
 * Log.d("TES ENKRIP PASSWORD JADI BASE64", EnkripPass);
 * 
 * 
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
