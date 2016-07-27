package com.cloudsolutionltd.cslMobileAccounts;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kazi Abdullah Al Mamun on 6/25/16.
 *
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


    /**
     * Check that application is protected or not.
     * @return true if application is protected.
     */
    public boolean isApplicationProtected() {
        return isApplicationProtected;
    }


    /**
     * Get pin from SharedPreferences
     * which will use to log in purpose.
     * @return 4 digit log in pin.
     */
    public String getApplicationPin() {
        return applicationPin;
    }


    /**
     * Fetch pin hint from SharedPreferences
     * and return to user.
     * @return the pin hint.
     */
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
