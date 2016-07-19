package com.cloudsolutionltd.cslMobileAccounts;

import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by csl on 7/14/16.
 */
public class EditTextPin extends EditText {
    public EditTextPin(Context context) {
        super(context);
        setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }
}
