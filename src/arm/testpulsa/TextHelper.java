package arm.testpulsa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;

public class TextHelper {

	public static void verifyPhoneNumber(Editable phoneNumber)
			throws ArmPulsaAddressMalformedException {
		if (null != phoneNumber && 10 > phoneNumber.length()) {
			Pattern p = Pattern.compile("[0-9\\+]");
			Matcher m = p.matcher(phoneNumber);

			if (!m.matches()) {
				throw new ArmPulsaAddressMalformedException(
						"Phone number is incorrect!");
			} else {
				throw new ArmPulsaAddressMalformedException(
						"Phone number wasn't set!");
			}
		}
	}

	public static void verifyPinNumber(Editable pinNumber)
			throws ArmPulsaAddressMalformedException {
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

	public static int phoneNumberMatcher(Editable phoneNumber) {
		// Patterns
		Pattern tel = Pattern
				.compile("0811||0812||0813||0852||0853||0821||0822||0823");
		Pattern xl = Pattern
				.compile("0819||0817||0818||0819||0859||0870||0877||0878||088");
		Pattern indosat = Pattern.compile("0814||0815||0855||0856||0857||0858");
		Pattern axis = Pattern.compile("0831||0833||0838");
		Pattern three = Pattern.compile("089");

		// Matchers
		Matcher mTel = tel.matcher(phoneNumber);
		Matcher mXl = xl.matcher(phoneNumber);
		Matcher mIndosat = indosat.matcher(phoneNumber);
		Matcher mAxis = axis.matcher(phoneNumber);
		Matcher mThree = three.matcher(phoneNumber);

		// The logic
		if (mThree.matches()) {
			return 10;
		} else if (mTel.matches()) {
			return 0;
		} else if (mXl.matches()) {
			return 4;
		} else if (mIndosat.matches()) {
			return 6;
		} else if (mAxis.matches()) {
			return 11;
		} else {
			return 0;
		}
	}
}
