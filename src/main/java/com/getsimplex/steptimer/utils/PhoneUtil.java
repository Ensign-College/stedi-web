package com.getsimplex.steptimer.utils;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneUtil {

    public static String getFormattedPhone(String inputPhone) throws Exception{
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(inputPhone, "US");
        String formattedPhone = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        formattedPhone = formattedPhone.replace(" ","");
        return formattedPhone;
    }

}
