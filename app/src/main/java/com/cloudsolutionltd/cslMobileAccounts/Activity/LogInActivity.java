package com.cloudsolutionltd.cslMobileAccounts.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudsolutionltd.cslMobileAccounts.R;

public class LogInActivity extends AppCompatActivity {

    private TextView txtForgetPin;
    private TextView txtCreatePin;
    private EditText eTxtPin;
    private CheckBox chkRememberMe;
    private TextInputLayout textInputLayoutPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0091EA")));

        initializeComponent();
    }

    private void initializeComponent() {

        eTxtPin = (EditText) findViewById(R.id.eTxtPin);
        txtCreatePin = (TextView) findViewById(R.id.txtCreatePin);
        txtForgetPin = (TextView) findViewById(R.id.txtForgetPin);
        chkRememberMe = (CheckBox) findViewById(R.id.chkRememberMe);
        textInputLayoutPin = (TextInputLayout) findViewById(R.id.textInputLayoutPin);
    }

    public void txtCreatePinOnClick(View view) {
        AlertDialog.Builder createPinDialog = new AlertDialog.Builder(this);
        createPinDialog.setTitle("Create pin.");

        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        EditText userpin = new EditText(getApplicationContext());
        userpin.setHint("Enter 4 number");
        userpin.setTextColor(Color.parseColor("#000000"));
        userpin.setHintTextColor(Color.parseColor("#00897B"));

        EditText repeatUserPin = new EditText(getApplicationContext());
        repeatUserPin.setHint("Repeat your pin");
        repeatUserPin.setTextColor(Color.parseColor("#000000"));
        repeatUserPin.setHintTextColor(Color.parseColor("#00897B"));

        linearLayout.addView(userpin);
        linearLayout.addView(repeatUserPin);
        createPinDialog.setView(linearLayout);

        createPinDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //m_Text = input.getText().toString();
            }
        });
        createPinDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        createPinDialog.show();
    }
}
