package arm.testpulsa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.text.Editable;

public class ArmHelpers {

	public static void sendSMS(Context context, String phoneNo, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
				new Intent(DELIVERED), 0);

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
	}

	/*
	 * Text Helper Starts
	 */
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
				.compile("0811[\\d]{0,10}||0812[\\d]{0,10}||0813[\\d]{0,10}||0852[\\d]{0,10}||0853[\\d]{0,10}||0821[\\d]{0,10}||0822[\\d]{0,10}||0823[\\d]{0,10}");
		Pattern xl = Pattern
				.compile("0819[\\d]{0,10}||0817[\\d]{0,10}||0818[\\d]{0,10}||0819[\\d]{0,10}||0859[\\d]{0,10}||0870[\\d]{0,10}||0877[\\d]{0,10}||0878[\\d]{0,10}||088[\\d]{0,10}");
		Pattern indosat = Pattern
				.compile("0814[\\d]{0,10}||0815[\\d]{0,10}||0855[\\d]{0,10}||0856[\\d]{0,10}||0857[\\d]{0,10}||0858[\\d]{0,10}");
		Pattern axis = Pattern
				.compile("0831[\\d]{0,10}||0833[\\d]{0,10}||0838[\\d]{0,10}");
		Pattern three = Pattern.compile("089[\\d]{0,10}");

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

	/*
	 * Text Helper Ends
	 */
}
