package com.cloudsolutionltd.cslMobileAccounts;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by csl on 6/25/16.
 */
public class PinProtection {

    public static final String PREF_NAME = "applicationSecurity";
    public static final String APPLICATION_PROTECTED = "applicationProtected";
    public static final String APPLICATION_PIN = "applicationPin";
    public static final String PIN_HINT = "pinHint";

    private boolean isApplicationProtected;
    private String applicationPin;
    private String pinHint;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editSharedPreferences;

    public PinProtection(Context context){

        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editSharedPreferences = sharedPreferences.edit();

        isApplicationProtected = sharedPreferences.getBoolean(APPLICATION_PROTECTED, false);
        applicationPin = sharedPreferences.getString(APPLICATION_PIN, null);
        pinHint = sharedPreferences.getString(PIN_HINT, null);
    }

    public boolean isApplicationProtected() {
        return isApplicationProtected;
    }

    public String getApplicationPin() {
        return applicationPin;
    }

    public String getPinHint() {
        return pinHint;
    }

    public void setApplicationProtected(boolean applicationProtected) {
        isApplicationProtected = applicationProtected;
        editSharedPreferences.putBoolean(APPLICATION_PROTECTED, isApplicationProtected);
    }

    public void setApplicationPin(String applicationPin) {
        this.applicationPin = applicationPin;
        editSharedPreferences.putString(APPLICATION_PIN, this.applicationPin);
    }

    public void setPinHint(String pinHint) {
        this.pinHint = pinHint;
        editSharedPreferences.putString(PIN_HINT, pinHint);
    }

    public void saveSharedPref(){
        editSharedPreferences.commit();
    }
}
