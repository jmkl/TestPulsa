package arm.testpulsa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;

public class TextHelper {
	
	public static void verifyPhoneNumber(Editable phoneNumber) throws ArmPulsaAddressMalformedException {
		if (null != phoneNumber && 10 > phoneNumber.length()) {
			Pattern p = Pattern.compile("[0-9\\+]");
			Matcher m = p.matcher(phoneNumber);
			
			if (!m.matches()) {
				throw new ArmPulsaAddressMalformedException("Phone number is incorrect!");
			} else {
				throw new ArmPulsaAddressMalformedException("Phone number wasn't set!");
			}
		}
	}
	
	public static void verifyPinNumber(Editable pinNumber) throws ArmPulsaAddressMalformedException {
		if (null != pinNumber && 4 >= pinNumber.length()) {
			Pattern p = Pattern.compile("[0-9\\+]");
			Matcher m = p.matcher(pinNumber);
			
			if (!m.matches()) {
				throw new ArmPulsaAddressMalformedException("Pin is incorrect!");
			} else {
				throw new ArmPulsaAddressMalformedException("Pin wasn't set!");
			}
		}
	}
}
