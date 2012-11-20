package arm.testpulsa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;

public class TextHelper {
	
	public static void verifyPhoneNumber(Editable phoneNumber) throws ArmPulsaAddressMalformedException {
		if (null != phoneNumber && 5 > phoneNumber.length()) {
			Pattern p = Pattern.compile("[0-9\\+]");
			Matcher m = p.matcher(phoneNumber);
			
			if (!m.matches()) {
				throw new ArmPulsaAddressMalformedException("Phone number is incorrect!");
			} else {
				throw new ArmPulsaAddressMalformedException("Phone number wasn't set!");
			}
		}
	}
}
