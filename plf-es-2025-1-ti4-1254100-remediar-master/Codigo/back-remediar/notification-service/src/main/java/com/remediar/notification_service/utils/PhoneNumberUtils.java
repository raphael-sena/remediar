package com.remediar.notification_service.utils;

public class PhoneNumberUtils {

    public static String formatToE164(String rawPhone) {
        String digits = rawPhone.replaceAll("\\D+", "");
        while (digits.startsWith("0")) {
            digits = digits.substring(1);
        }

        if (digits.length() == 10 || digits.length() == 11) {
            digits = "55" + digits;
        }

        return "+" + digits;
    }

}
